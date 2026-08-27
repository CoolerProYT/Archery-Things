package com.coolerpromc.archerythings.compat;

import com.coolerpromc.archerythings.item.ModItems;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class TrinketsHelper {
    public static boolean isQuiverEquipped(Player player) {
        return findQuiver(player).isPresent();
    }

    public static ItemStack getQuiver(Player player) {
        return findQuiver(player).map(TrinketSlotAccess::get).orElse(ItemStack.EMPTY);
    }

    private static Optional<TrinketSlotAccess> findQuiver(Player player) {
        return TrinketsApi.getAttachment(player).findFirst(stack -> stack.is(ModItems.QUIVER.get()));
    }
}
