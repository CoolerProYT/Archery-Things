package com.coolerpromc.archerythings.platform;

import com.coolerpromc.archerythings.compat.CuriosHelper;
import com.coolerpromc.archerythings.platform.services.IQuiverHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class NeoForgeQuiverHelper implements IQuiverHelper {
    @Override
    public boolean isQuiverEquipped(Player player) {
        return (Services.PLATFORM.isModLoaded("curios") && CuriosHelper.isQuiverEquipped(player)) || isQuiverEquippedCommon(player);
    }

    @Override
    public ItemStack getQuiver(Player player) {
        if (!isQuiverEquipped(player)){
            return ItemStack.EMPTY;
        }
        if (isQuiverEquippedCommon(player)) return getQuiverCommon(player);
        return CuriosHelper.getQuiver(player);
    }
}
