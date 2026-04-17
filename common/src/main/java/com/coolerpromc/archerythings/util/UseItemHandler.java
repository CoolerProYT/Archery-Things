package com.coolerpromc.archerythings.util;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.item.custom.ModQuiverItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class UseItemHandler {
    public static InteractionResult onUseItem(ItemStack stack, Player player, InteractionHand interactionHand){
        if (stack.has(ModDataComponents.STORED_QUIVER.get())){
            ItemStack itemStack = stack.get(ModDataComponents.STORED_QUIVER.get()).stack();
            if(itemStack.getItem() instanceof ModQuiverItem item){
                return item.use(player.level(), player, interactionHand);
            }
        }
        return InteractionResult.PASS;
    }
}
