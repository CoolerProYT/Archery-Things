package com.coolerpromc.archerythings.util;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.StoredQuiver;
import com.coolerpromc.archerythings.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class AnvilHandler {
    public static void onLand(Level level, BlockPos pos, BlockState state, BlockState replacedBlock, FallingBlockEntity entity){
        for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, new AABB(pos))){
            if (itemEntity.getItem().has(ModDataComponents.STORED_QUIVER.get())){
                ItemStack quiver = itemEntity.getItem().remove(ModDataComponents.STORED_QUIVER.get()).stack();
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), quiver));
            }
        }
    }

    public static ItemStack onUpdate(ItemStack left, ItemStack right){
        if (!left.isEmpty()) {
            if (left.get(DataComponents.EQUIPPABLE) != null && (left.get(DataComponents.EQUIPPABLE).slot().equals(EquipmentSlot.CHEST) || left.get(DataComponents.EQUIPPABLE).slot().equals(EquipmentSlot.LEGS)) && right.getItem() == ModItems.QUIVER.get() && !left.has(ModDataComponents.STORED_QUIVER.get())){
                ItemStack output = left.copy();
                output.set(ModDataComponents.STORED_QUIVER.get(), new StoredQuiver(right));
                return output;
            }
        }
        return ItemStack.EMPTY;
    }
}
