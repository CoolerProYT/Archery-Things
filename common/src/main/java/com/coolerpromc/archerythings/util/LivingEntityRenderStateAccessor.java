package com.coolerpromc.archerythings.util;

import net.minecraft.world.item.ItemStack;

public interface LivingEntityRenderStateAccessor {
    void archerythings$setQuiverStack(ItemStack quiverStack);
    ItemStack archerythings$getQuiverStack();
}