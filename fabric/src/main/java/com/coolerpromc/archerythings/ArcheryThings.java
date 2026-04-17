package com.coolerpromc.archerythings;

import com.coolerpromc.archerythings.network.packet.ServerBoundQuiverMenuPacket;
import com.coolerpromc.archerythings.network.packet.ServerBoundSelectQuiverSlotPacket;
import com.coolerpromc.archerythings.platform.util.FabricPayloadContext;
import com.coolerpromc.archerythings.util.UseItemHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ArcheryThings implements ModInitializer {
    
    @Override
    public void onInitialize() {
        CommonClass.init();

        PayloadTypeRegistry.serverboundPlay().register(ServerBoundQuiverMenuPacket.TYPE, ServerBoundQuiverMenuPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ServerBoundQuiverMenuPacket.TYPE, (packet, context) -> packet.handle(new FabricPayloadContext(context)));

        PayloadTypeRegistry.serverboundPlay().register(ServerBoundSelectQuiverSlotPacket.TYPE, ServerBoundSelectQuiverSlotPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ServerBoundSelectQuiverSlotPacket.TYPE, (packet, context) -> packet.handle(new FabricPayloadContext(context)));

        UseItemCallback.EVENT.register((player, _, interactionHand) -> UseItemHandler.onUseItem(player.getItemInHand(interactionHand), player, interactionHand));
    }
}
