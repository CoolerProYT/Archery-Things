package com.coolerpromc.archerythings.mixin;

import com.coolerpromc.archerythings.util.ArrowHandler;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(Player.class)
public abstract class PlayerMixin extends Avatar {
    @Shadow
    @Final
    private Inventory inventory;

    @Shadow
    @Final
    private Abilities abilities;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "getProjectile", at = @At("HEAD"), cancellable = true)
    public void getProjectile(ItemStack shootable, CallbackInfoReturnable<ItemStack> cir) {
        if (!(shootable.getItem() instanceof ProjectileWeaponItem)) {
            cir.setReturnValue(ItemStack.EMPTY);
        } else {
            Predicate<ItemStack> predicate = ((ProjectileWeaponItem)shootable.getItem()).getSupportedHeldProjectiles();
            ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(this, predicate);
            if (!itemstack.isEmpty()) {
                cir.setReturnValue(ArrowHandler.getProjectileFromQuiver(this, itemstack));
                return;
            } else {
                predicate = ((ProjectileWeaponItem)shootable.getItem()).getAllSupportedProjectiles();

                for (int i = 0; i < this.inventory.getContainerSize(); i++) {
                    ItemStack itemstack1 = this.inventory.getItem(i);
                    if (predicate.test(itemstack1)) {
                        cir.setReturnValue(ArrowHandler.getProjectileFromQuiver(this, itemstack1));
                        return;
                    }
                }

                cir.setReturnValue(ArrowHandler.getProjectileFromQuiver(this, this.abilities.instabuild ? new ItemStack(Items.ARROW) : ItemStack.EMPTY));
                return;
            }
        }
        cir.cancel();
    }
}
