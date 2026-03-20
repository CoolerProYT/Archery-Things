package com.coolerpromc.archerythings.util;

import com.coolerpromc.archerythings.component.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class AnvilHandler {
    public static void onLand(Level level, BlockPos pos, BlockState state, BlockState replacedBlock, FallingBlockEntity entity){
        for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, new AABB(pos))){
            if (itemEntity.getItem().has(ModDataComponents.STORED_QUIVER)){
                ItemStack quiver = itemEntity.getItem().remove(ModDataComponents.STORED_QUIVER).stack();
                level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), quiver));
            }
        }
    }
}
