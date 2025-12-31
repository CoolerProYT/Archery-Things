package com.coolerpromc.archerythings.network;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.network.packet.ServerBoundQuiverMenuPacket;
import com.coolerpromc.archerythings.network.packet.ServerBoundSelectQuiverSlotPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ArcheryThings.MODID)
public class NetworkRegistries {
    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(ServerBoundQuiverMenuPacket.TYPE, ServerBoundQuiverMenuPacket.STREAM_CODEC, ServerBoundQuiverMenuPacket::handle);
        registrar.playToServer(ServerBoundSelectQuiverSlotPacket.TYPE, ServerBoundSelectQuiverSlotPacket.STREAM_CODEC, ServerBoundSelectQuiverSlotPacket::handle);
    }
}
