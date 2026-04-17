package com.coolerpromc.archerythings.util;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.item.custom.ModQuiverItem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.LinkedList;
import java.util.List;

public class ClientHandler {
    public static void onItemTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipFlag tooltipFlag, List<Component> list){
        if (itemStack.has(ModDataComponents.STORED_QUIVER.get())) {
            ItemStack quiver = itemStack.get(ModDataComponents.STORED_QUIVER.get()).stack();
            TooltipDisplay tooltipdisplay = itemStack.getOrDefault(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT);
            if (quiver.getItem() instanceof ModQuiverItem item) {
                LinkedList<Component> tooltip = new LinkedList<>(list);
                LinkedList<Component> quiverTooltip = new LinkedList<>();
                item.appendHoverText(quiver, tooltipContext, tooltipdisplay, quiverTooltip::add, tooltipFlag);
                tooltip.set(0, tooltip.getFirst().copy().append(Component.translatable("tooltip.archerythings.with_quiver")));
                tooltip.addAll(1, quiverTooltip);
                list.clear();
                list.addAll(tooltip);
            }
        }
    }

    public static boolean onRegisterItemDecoration(GuiGraphicsExtractor guiGraphics, Font font, ItemStack item, int x, int y){
        if (!(item.getItem() instanceof ModQuiverItem) && item.has(DataComponents.EQUIPPABLE) && (item.get(DataComponents.EQUIPPABLE).slot().equals(EquipmentSlot.CHEST) || item.get(DataComponents.EQUIPPABLE).slot().equals(EquipmentSlot.LEGS))){
            if(item.has(ModDataComponents.STORED_QUIVER.get())){
                guiGraphics.pose().pushMatrix();
                guiGraphics.pose().scale(0.5f);
                guiGraphics.fakeItem(item.get(ModDataComponents.STORED_QUIVER.get()).stack(), x * 2, y * 2);
                guiGraphics.pose().popMatrix();
            }
        }
        return false;
    }
}
