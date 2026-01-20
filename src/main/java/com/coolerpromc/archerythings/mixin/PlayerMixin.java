package com.coolerpromc.archerythings.mixin;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.QuiverData;
import com.coolerpromc.archerythings.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
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
import org.spongepowered.asm.mixin.Unique;
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
                cir.setReturnValue(getProjectileFromQuiver(this, shootable, itemstack));
                return;
            } else {
                predicate = ((ProjectileWeaponItem)shootable.getItem()).getAllSupportedProjectiles();

                for (int i = 0; i < this.inventory.getContainerSize(); i++) {
                    ItemStack itemstack1 = this.inventory.getItem(i);
                    if (predicate.test(itemstack1)) {
                        cir.setReturnValue(getProjectileFromQuiver(this, shootable, itemstack1));
                        return;
                    }
                }

                cir.setReturnValue(getProjectileFromQuiver(this, shootable, this.abilities.instabuild ? new ItemStack(Items.ARROW) : ItemStack.EMPTY));
                return;
            }
        }
        cir.cancel();
    }

    @Unique
    public ItemStack getProjectileFromQuiver(LivingEntity livingEntity, ItemStack projectileWeaponItemStack, ItemStack ammo) {
        if (livingEntity instanceof Player player){
            ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
            if(stack.isEmpty()){
                stack = player.getItemBySlot(EquipmentSlot.LEGS);
            }
            if (stack.has(ModDataComponents.STORED_QUIVER)){
                stack = stack.get(ModDataComponents.STORED_QUIVER).stack();
            }
            if (!stack.isEmpty() && (stack.getItem() == ModItems.QUIVER || stack.has(ModDataComponents.STORED_QUIVER))){
                QuiverData contents = stack.getOrDefault(ModDataComponents.QUIVER_DATA, QuiverData.EMPTY);
                int selected = stack.getOrDefault(ModDataComponents.SELECTED, 0);

                if (selected < contents.getSlots()){
                    ItemStack selectedStack = contents.getStackInSlot(selected);
                    if (!selectedStack.isEmpty()){
                        return selectedStack;
                    }
                    else{
                        player.displayClientMessage(Component.translatable("message.archerythings.no_quiver_slot"), true);
                    }
                }
                else{
                    player.displayClientMessage(Component.translatable("message.archerythings.no_quiver_slot"), true);
                }
            }
        }
        return ammo;
    }
}
