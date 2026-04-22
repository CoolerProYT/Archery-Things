package com.coolerpromc.archerythings.compat;

import com.coolerpromc.archerythings.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;

public class CuriosHelper {
    public static boolean isQuiverEquipped(Player player){
        Optional<ICuriosItemHandler> maybeCuriosInventory = CuriosApi.getCuriosInventory(player);
        if (maybeCuriosInventory.isPresent()){
            ICurioStacksHandler slotInventory = maybeCuriosInventory.get().getStacksHandler("quiver").orElse(null);
            return slotInventory != null && slotInventory.getStacks().getStackInSlot(0).is(ModItems.QUIVER.get());
        }
        return false;
    }

    public static ItemStack getQuiver(Player player){
        Optional<ICuriosItemHandler> maybeCuriosInventory = CuriosApi.getCuriosInventory(player);
        if (maybeCuriosInventory.isPresent()){
            ICurioStacksHandler slotInventory = maybeCuriosInventory.get().getStacksHandler("quiver").orElse(null);
            if (slotInventory != null){
                return slotInventory.getStacks().getStackInSlot(0);
            }
        }
        return ItemStack.EMPTY;
    }
}
