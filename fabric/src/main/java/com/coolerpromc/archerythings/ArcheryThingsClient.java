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
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.ExtractItemDecorationsCallback;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;

public class ArcheryThingsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyMappingHelper.registerKeyMapping(ModKeyMappings.OPEN_QUIVER_MENU.get());

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ModKeyMappings.OPEN_QUIVER_MENU.get().consumeClick()) {
                Services.NETWORK.sendToServer(new ServerBoundQuiverMenuPacket());
            }
        });

        ItemTooltipCallback.EVENT.register(ClientHandler::onItemTooltip);

        MenuScreens.register(ModMenuTypes.QUIVER_MENU.get(), QuiverScreen::new);

        ModelLayerRegistry.registerModelLayer(ModModelLayers.QUIVER, QuiverModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(ModModelLayers.QUIVER_LEG, QuiverLegModel::createLegLayer);

        LivingEntityRenderLayerRegistrationCallback.EVENT.register((_, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof AvatarRenderer<?> renderer){
                registrationHelper.register(new QuiverLayer<>(renderer, context.getModelSet()));
            }
        });

        ExtractItemDecorationsCallback.EVENT.register(ClientHandler::onRegisterItemDecoration);
    }
}