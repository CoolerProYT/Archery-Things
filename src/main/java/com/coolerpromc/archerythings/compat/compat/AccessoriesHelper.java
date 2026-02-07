package com.coolerpromc.archerythings.compat.compat;

import com.coolerpromc.archerythings.item.ModItems;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesStorage;
import io.wispforest.accessories.api.slot.SlotPath;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AccessoriesHelper {
    public static boolean isQuiverEquipped(Player player){
        AccessoriesCapability capability = AccessoriesCapability.get(player);
        if (capability != null){
            AccessoriesStorage storage = capability.getContainer(SlotPath.fromString("quiver/0"));
            return storage != null && storage.getAccessories().getItem(0).is(ModItems.QUIVER);
        }
        return false;
    }

    public static ItemStack getQuiver(Player player){
        AccessoriesCapability capability = AccessoriesCapability.get(player);
        if (capability != null){
            AccessoriesStorage storage = capability.getContainer(SlotPath.fromString("quiver/0"));
            if (storage != null){
                return storage.getAccessories().getItem(0);
            }
        }
        return ItemStack.EMPTY;
    }
}
