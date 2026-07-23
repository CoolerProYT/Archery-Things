package com.coolerpromc.archerythings.util;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.StoredQuiver;
import com.coolerpromc.archerythings.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
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

    public static AnvilUpdateResult onUpdate(ItemStack left, ItemStack right, String name){
        if (!left.isEmpty()) {
            if (left.get(DataComponents.EQUIPPABLE) != null && (left.get(DataComponents.EQUIPPABLE).slot().equals(EquipmentSlot.CHEST) || left.get(DataComponents.EQUIPPABLE).slot().equals(EquipmentSlot.LEGS)) && right.getItem() == ModItems.QUIVER.get() && !left.has(ModDataComponents.STORED_QUIVER.get())){
                ItemStack output = left.copy();
                output.set(ModDataComponents.STORED_QUIVER.get(), new StoredQuiver(right));

                int xpCost = 1;
                if (name != null) {
                    if (name.isEmpty()) {
                        if (left.has(DataComponents.CUSTOM_NAME)) {
                            output.remove(DataComponents.CUSTOM_NAME);
                            xpCost += 1;
                        }
                    } else if (!name.equals(left.getHoverName().getString())) {
                        output.set(DataComponents.CUSTOM_NAME, Component.literal(name));
                        xpCost += 1;
                    }
                }

                return new AnvilUpdateResult(output, xpCost);
            }
        }
        return AnvilUpdateResult.EMPTY;
    }

    public record AnvilUpdateResult(ItemStack output, int xpCost){
        public static final AnvilUpdateResult EMPTY = new AnvilUpdateResult(ItemStack.EMPTY, 0);
    }
}
