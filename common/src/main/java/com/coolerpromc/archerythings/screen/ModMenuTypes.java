package com.coolerpromc.archerythings.screen;

import com.coolerpromc.archerythings.platform.Services;
import com.coolerpromc.archerythings.screen.quiver.QuiverMenu;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final Supplier<MenuType<QuiverMenu>> QUIVER_MENU = Services.REGISTRY.registerMenu("quiver_menu", (i, inventory, hand) -> new QuiverMenu(i, inventory, inventory.player, hand.orElse(null)), ByteBufCodecs.optional(InteractionHand.STREAM_CODEC));

    public static void load(){
    }
}
