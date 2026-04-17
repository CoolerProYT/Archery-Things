package com.coolerpromc.archerythings;

import com.coolerpromc.archerythings.key.ModKeyMappings;
import com.coolerpromc.archerythings.model.ModModelLayers;
import com.coolerpromc.archerythings.model.quiver.QuiverLayer;
import com.coolerpromc.archerythings.model.quiver.QuiverLegModel;
import com.coolerpromc.archerythings.model.quiver.QuiverModel;
import com.coolerpromc.archerythings.network.packet.ServerBoundQuiverMenuPacket;
import com.coolerpromc.archerythings.platform.Services;
import com.coolerpromc.archerythings.screen.ModMenuTypes;
import com.coolerpromc.archerythings.screen.quiver.QuiverScreen;
import com.coolerpromc.archerythings.util.ClientHandler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.PlayerModelType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@Mod(value = Constants.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Constants.MODID, value = Dist.CLIENT)
public class ArcheryThingsClient {
    public ArcheryThingsClient(ModContainer container) {

    }

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.registerCategory(ModKeyMappings.ARCHERY_THINGS_CATEGORY);
        event.register(ModKeyMappings.OPEN_QUIVER_MENU.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (ModKeyMappings.OPEN_QUIVER_MENU.get().consumeClick()) {
            Services.NETWORK.sendToServer(new ServerBoundQuiverMenuPacket());
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ClientHandler.onItemTooltip(event.getItemStack(), event.getContext(), event.getFlags(), event.getToolTip());
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
        BuiltInRegistries.ITEM.stream().forEach(item -> event.register(item, ClientHandler::onRegisterItemDecoration));
    }
}
