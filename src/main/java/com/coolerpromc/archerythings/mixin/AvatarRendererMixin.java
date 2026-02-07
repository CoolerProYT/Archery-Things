package com.coolerpromc.archerythings.mixin;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.compat.compat.AccessoriesHelper;
import com.coolerpromc.archerythings.util.LivingEntityRenderStateAccessor;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin<AvatarlikeEntity extends Avatar & ClientAvatarEntity> {
    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    private void onExtractRenderState(AvatarlikeEntity avatar, AvatarRenderState renderState, float f, CallbackInfo ci){
        if (avatar instanceof Player player && ArcheryThings.isQuiverEquipped(player)){
            ((LivingEntityRenderStateAccessor) renderState).archerythings$setQuiverStack(AccessoriesHelper.getQuiver(player));
        }
        else{
            ((LivingEntityRenderStateAccessor) renderState).archerythings$setQuiverStack(null);
        }
    }
}
