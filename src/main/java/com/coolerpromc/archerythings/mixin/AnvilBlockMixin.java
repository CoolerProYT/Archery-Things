package com.coolerpromc.archerythings.mixin;

import com.coolerpromc.archerythings.util.AnvilHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilBlock.class)
public abstract class AnvilBlockMixin {
    @Inject(method = "onLand", at = @At("RETURN"))
    private void onLand(Level level, BlockPos pos, BlockState state, BlockState replacedBlock, FallingBlockEntity entity, CallbackInfo ci){
        AnvilHandler.onLand(level, pos, state, replacedBlock, entity);
    }
}
