package com.coolerpromc.archerythings.platform.services;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IQuiverHelper {
    boolean isQuiverEquipped(Player player);
    ItemStack getQuiver(Player player);
}
