package com.coolerpromc.archerythings.mixin;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.StoredQuiver;
import com.coolerpromc.archerythings.item.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    @Shadow
    private @Nullable String itemName;

    @Shadow
    @Final
    private DataSlot cost;

    @Shadow
    public int repairItemCountCost;

    public AnvilMenuMixin(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess, ItemCombinerMenuSlotDefinition itemCombinerMenuSlotDefinition) {
        super(menuType, i, inventory, containerLevelAccess, itemCombinerMenuSlotDefinition);
    }

    @Inject(method = "createResult", at = @At("RETURN"))
    public void createResult(CallbackInfo ci){
        ItemStack leftInput = this.inputSlots.getItem(0);
        ItemStack rightInput = this.inputSlots.getItem(1);
        onAnvilUpdate(leftInput, rightInput, resultSlots, itemName, this.player);
    }

    @Unique
    public void onAnvilUpdate(ItemStack left, ItemStack right, Container resultSlot, @Nullable String name, Player player) {
        if (!left.isEmpty()) {
            if (left.get(DataComponents.EQUIPPABLE) != null && left.get(DataComponents.EQUIPPABLE).slot().equals(EquipmentSlot.CHEST) && right.getItem() == ModItems.QUIVER && !left.has(ModDataComponents.STORED_QUIVER)){
                ItemStack output = left.copy();
                output.set(ModDataComponents.STORED_QUIVER, new StoredQuiver(right));
                resultSlot.setItem(0, output);
                this.cost.set(1);
                this.repairItemCountCost = 1;
            }
        }
    }
}
