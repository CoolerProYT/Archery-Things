package com.coolerpromc.archerythings.network;

import com.coolerpromc.archerythings.network.packet.ServerBoundQuiverMenuPacket;
import com.coolerpromc.archerythings.network.packet.ServerBoundSelectQuiverSlotPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class NetworkRegistries {
    public static void register() {
        PayloadTypeRegistry.playC2S().register(ServerBoundQuiverMenuPacket.TYPE, ServerBoundQuiverMenuPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ServerBoundQuiverMenuPacket.TYPE, ServerBoundQuiverMenuPacket::handle);

        PayloadTypeRegistry.playC2S().register(ServerBoundSelectQuiverSlotPacket.TYPE, ServerBoundSelectQuiverSlotPacket.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ServerBoundSelectQuiverSlotPacket.TYPE, ServerBoundSelectQuiverSlotPacket::handle);
    }
}
