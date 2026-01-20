package com.coolerpromc.archerythings;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.item.custom.ModQuiverItem;
import com.coolerpromc.archerythings.model.ModModelLayers;
import com.coolerpromc.archerythings.model.quiver.QuiverLayer;
import com.coolerpromc.archerythings.model.quiver.QuiverLegModel;
import com.coolerpromc.archerythings.model.quiver.QuiverModel;
import com.coolerpromc.archerythings.screen.ModMenuTypes;
import com.coolerpromc.archerythings.screen.quiver.QuiverScreen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.IItemDecorator;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.LinkedList;

@Mod(value = ArcheryThings.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ArcheryThings.MODID, value = Dist.CLIENT)
public class ArcheryThingsClient {
    public ArcheryThingsClient(ModContainer container) {

    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().has(ModDataComponents.STORED_QUIVER)) {
            ItemStack quiver = event.getItemStack().get(ModDataComponents.STORED_QUIVER).stack();
            TooltipDisplay tooltipdisplay = event.getItemStack().getOrDefault(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);
            if (quiver.getItem() instanceof ModQuiverItem item) {
                LinkedList<Component> tooltip = new LinkedList<>(event.getToolTip());
                LinkedList<Component> quiverTooltip = new LinkedList<>();
                item.appendHoverText(quiver, event.getContext(), tooltipdisplay, quiverTooltip::add, event.getFlags());
                tooltip.set(0, tooltip.getFirst().copy().append(Component.translatable("tooltip.archerythings.with_quiver")));
                tooltip.addAll(1, quiverTooltip);
                event.getToolTip().clear();
                event.getToolTip().addAll(tooltip);
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.QUIVER_MENU.get(), QuiverScreen::new);
    }

    @SubscribeEvent
    public static void onEntityRenderersRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.QUIVER, QuiverModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.QUIVER_LEG, QuiverLegModel::createLegLayer);
    }

    @SubscribeEvent
    public static void onEntityRenderersAddLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerModelType skinType : event.getSkins()) {
            var renderer = event.getPlayerRenderer(skinType);
            if (renderer != null) {
                renderer.addLayer(new QuiverLayer<>(renderer, event.getEntityModels()));
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterItemDecorations(RegisterItemDecorationsEvent event) {
        IItemDecorator decorator = (guiGraphics, font, itemStack, i, i1) -> {
            if (itemStack.has(ModDataComponents.STORED_QUIVER.get())){
                guiGraphics.pose().pushMatrix();
                guiGraphics.pose().scale(0.5f);
                guiGraphics.renderFakeItem(itemStack.get(ModDataComponents.STORED_QUIVER.get()).stack(), i * 2, i1 * 2);
                guiGraphics.pose().popMatrix();
            }
            return true;
        };

        BuiltInRegistries.ITEM.stream().forEach(item -> {
            if (!(item instanceof ModQuiverItem) && item.components().has(DataComponents.EQUIPPABLE) && (item.components().get(DataComponents.EQUIPPABLE).slot().equals(EquipmentSlot.CHEST) || item.components().get(DataComponents.EQUIPPABLE).slot().equals(EquipmentSlot.LEGS))){
                event.register(item, decorator);
            }
        });
    }
}
