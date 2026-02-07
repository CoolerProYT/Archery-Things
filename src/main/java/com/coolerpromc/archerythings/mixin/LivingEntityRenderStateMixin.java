package com.coolerpromc.archerythings.mixin;

import com.coolerpromc.archerythings.util.LivingEntityRenderStateAccessor;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements LivingEntityRenderStateAccessor {
    @Unique
    private ItemStack archerythings$quiverStack;

    @Unique
    public void archerythings$setQuiverStack(ItemStack quiverStack){
        this.archerythings$quiverStack = quiverStack;
    }

    @Unique
    public ItemStack archerythings$getQuiverStack() {
        return archerythings$quiverStack;
    }
}
