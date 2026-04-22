package com.coolerpromc.archerythings.platform;

import com.coolerpromc.archerythings.platform.services.IQuiverHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

// TODO: Update checking when accessories released
public class FabricQuiverHelper implements IQuiverHelper {
    @Override
    public boolean isQuiverEquipped(Player player) {
        return Services.PLATFORM.isModLoaded("accessories");
    }

    @Override
    public ItemStack getQuiver(Player player) {
        return ItemStack.EMPTY;
    }
}
