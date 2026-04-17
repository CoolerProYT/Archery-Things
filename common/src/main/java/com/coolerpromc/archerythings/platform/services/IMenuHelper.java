package com.coolerpromc.archerythings.platform.services;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;

public interface IMenuHelper {
    void openMenu(ServerPlayer player, MenuProvider provider, InteractionHand data);
}
