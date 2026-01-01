package com.coolerpromc.archerythings.screen;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.network.packet.ServerBoundSelectQuiverSlotPacket;
import com.coolerpromc.archerythings.screen.quiver.QuiverMenu;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
    public static final MenuType<QuiverMenu> QUIVER_MENU = Registry.register(BuiltInRegistries.MENU, ArcheryThings.id("quiver_menu"), new ExtendedScreenHandlerType<>((i, inventory, hand) ->
            new QuiverMenu(i, inventory, inventory.player, hand.orElse(null)), ByteBufCodecs.optional(ServerBoundSelectQuiverSlotPacket.INTERACTION_HAND_STREAM_CODEC)));

    public static void register(){

    }
}
