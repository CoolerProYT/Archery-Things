package com.coolerpromc.archerythings.component.data;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record StoredQuiver(ItemStack stack) {
    public static final Codec<StoredQuiver> CODEC = ItemStack.CODEC.xmap(StoredQuiver::new, StoredQuiver::stack);
    
    public static final StreamCodec<RegistryFriendlyByteBuf, StoredQuiver> STREAM_CODEC = ItemStack.STREAM_CODEC.map(StoredQuiver::new, StoredQuiver::stack);

    public StoredQuiver(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack stack() {
        return stack;
    }
}