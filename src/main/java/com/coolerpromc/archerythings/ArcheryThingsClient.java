package com.coolerpromc.archerythings;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.item.custom.ModQuiverItem;
import com.coolerpromc.archerythings.key.ModKeyMappings;
import com.coolerpromc.archerythings.model.ModModelLayers;
import com.coolerpromc.archerythings.model.quiver.QuiverLayer;
import com.coolerpromc.archerythings.model.quiver.QuiverLegModel;
import com.coolerpromc.archerythings.model.quiver.QuiverModel;
import com.coolerpromc.archerythings.screen.ModMenuTypes;
import com.coolerpromc.archerythings.screen.quiver.QuiverScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.DrawItemStackOverlayCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.LinkedList;

public class ArcheryThingsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipFlag, list) -> {
            if (itemStack.has(ModDataComponents.STORED_QUIVER)) {
                ItemStack quiver = itemStack.get(ModDataComponents.STORED_QUIVER).stack();
                TooltipDisplay tooltipdisplay = itemStack.getOrDefault(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);
                if (quiver.getItem() instanceof ModQuiverItem item) {
                    LinkedList<Component> tooltip = new LinkedList<>(list);
                    LinkedList<Component> quiverTooltip = new LinkedList<>();
                    item.appendHoverText(quiver, tooltipContext, tooltipdisplay, quiverTooltip::add, tooltipFlag);
                    tooltip.set(0, tooltip.getFirst().copy().append(Component.translatable("tooltip.archerythings.with_quiver")));
                    tooltip.addAll(1, quiverTooltip);
                    list.clear();
                    list.addAll(tooltip);
                }
            }
        });

        MenuScreens.register(ModMenuTypes.QUIVER_MENU, QuiverScreen::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.QUIVER, QuiverModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.QUIVER_LEG, QuiverLegModel::createLegLayer);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof AvatarRenderer<?> renderer){
                registrationHelper.register(new QuiverLayer<>(renderer, context.getModelSet()));
            }
        });

        DrawItemStackOverlayCallback.EVENT.register((guiGraphics, textRenderer, item, x, y) -> {
            if (!(item.getItem() instanceof ModQuiverItem) && item.has(DataComponents.EQUIPPABLE) && (item.get(DataComponents.EQUIPPABLE).slot().equals(EquipmentSlot.CHEST) || item.get(DataComponents.EQUIPPABLE).slot().equals(EquipmentSlot.LEGS))){
                if(item.has(ModDataComponents.STORED_QUIVER)){
                    guiGraphics.pose().pushMatrix();
                    guiGraphics.pose().scale(0.5f);
                    guiGraphics.renderFakeItem(item.get(ModDataComponents.STORED_QUIVER).stack(), x * 2, y * 2);
                    guiGraphics.pose().popMatrix();
                }
            }
        });

        ModKeyMappings.register();
    }
}