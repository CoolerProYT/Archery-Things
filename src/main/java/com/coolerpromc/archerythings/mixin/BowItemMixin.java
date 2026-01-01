package com.coolerpromc.archerythings.mixin;

import com.coolerpromc.archerythings.ArcheryThings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static net.minecraft.world.item.BowItem.getPowerForTime;

@Mixin(BowItem.class)
public abstract class BowItemMixin extends ProjectileWeaponItem {
    public BowItemMixin(Properties properties) {
        super(properties);
    }

    @Shadow
    public abstract int getUseDuration(ItemStack itemStack, LivingEntity livingEntity);

    @Inject(method = "releaseUsing", at = @At("HEAD"), cancellable = true)
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i, CallbackInfoReturnable<Boolean> cir) {
        if (!(livingEntity instanceof Player player)) {
            cir.setReturnValue(false);
            return;
        } else {
            ItemStack itemStack2 = player.getProjectile(itemStack);
            if (itemStack2.isEmpty()) {
                cir.setReturnValue(false);
                return;
            } else {
                int j = this.getUseDuration(itemStack, livingEntity) - i;
                ArcheryThings.onArrowLoose(player, itemStack, level, !itemStack2.isEmpty());
                float f = getPowerForTime(j);
                if ((double)f < 0.1) {
                    cir.setReturnValue(false);
                    return;
                } else {
                    List<ItemStack> list = draw(itemStack, itemStack2, player);
                    if (level instanceof ServerLevel serverLevel) {
                        if (!list.isEmpty()) {
                            this.shoot(serverLevel, player, player.getUsedItemHand(), itemStack, list, f * 3.0F, 1.0F, f == 1.0F, null);
                        }
                    }

                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    player.awardStat(Stats.ITEM_USED.get(this));
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }
}
