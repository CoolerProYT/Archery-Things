package com.coolerpromc.archerythings.util;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.QuiverData;
import com.coolerpromc.archerythings.item.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

public class ArrowHandler {
    public static void onArrowLoose(Player player, ItemStack bow, Level level, boolean hasAmmo) {
        ItemStack chestEquipment = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legEquipment = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack quiverLike = ItemStack.EMPTY;

        if (chestEquipment.is(ModItems.QUIVER.get()) && hasArrow(chestEquipment)){
            quiverLike = chestEquipment;
        }
        else if (chestEquipment.has(ModDataComponents.STORED_QUIVER.get()) && hasArrow(chestEquipment.get(ModDataComponents.STORED_QUIVER.get()).stack())){
            quiverLike = chestEquipment.get(ModDataComponents.STORED_QUIVER.get()).stack();
        }
        else if (legEquipment.has(ModDataComponents.STORED_QUIVER.get())){
            quiverLike = legEquipment.get(ModDataComponents.STORED_QUIVER.get()).stack();
        }

        if (!quiverLike.isEmpty()) {
            QuiverData contents = quiverLike.get(ModDataComponents.QUIVER_DATA.get());
            if (contents != null && contents != QuiverData.EMPTY) {
                int selected = quiverLike.getOrDefault(ModDataComponents.SELECTED.get(), 0);

                if (selected >= 0 && selected < contents.getSlots()) {
                    ItemStack checkArrow = contents.getStackInSlot(selected);
                    if (checkArrow.isEmpty()) {
                        return;
                    }

                    NonNullList<ItemStack> updatedItems = NonNullList.withSize(contents.getSlots(), ItemStack.EMPTY);
                    contents.copyInto(updatedItems);

                    ItemStack arrow = updatedItems.get(selected);
                    if (!arrow.isEmpty() && hasAmmo && bow.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).getLevel(level.holderLookup(Registries.ENCHANTMENT).getOrThrow(Enchantments.INFINITY)) != 1) {
                        arrow.shrink(1);
                        if (arrow.isEmpty()) {
                            updatedItems.set(selected, ItemStack.EMPTY);
                        }

                        quiverLike.set(ModDataComponents.QUIVER_DATA.get(), QuiverData.fromItems(updatedItems));
                    }
                }
            }
        }
    }

    public static ItemStack getProjectileFromQuiver(LivingEntity livingEntity, ItemStack ammo) {
        if (livingEntity instanceof Player player){
            ItemStack chestEquipment = player.getItemBySlot(EquipmentSlot.CHEST);
            ItemStack legEquipment = player.getItemBySlot(EquipmentSlot.LEGS);
            ItemStack quiverLike = ItemStack.EMPTY;

            if (chestEquipment.is(ModItems.QUIVER.get()) && hasArrow(chestEquipment)){
                quiverLike = chestEquipment;
            }
            else if (chestEquipment.has(ModDataComponents.STORED_QUIVER.get()) && hasArrow(chestEquipment.get(ModDataComponents.STORED_QUIVER.get()).stack())){
                quiverLike = chestEquipment.get(ModDataComponents.STORED_QUIVER.get()).stack();
            }
            else if (legEquipment.has(ModDataComponents.STORED_QUIVER.get())){
                quiverLike = legEquipment.get(ModDataComponents.STORED_QUIVER.get()).stack();
            }

            if (!quiverLike.isEmpty() && (quiverLike.getItem() == ModItems.QUIVER.get() || quiverLike.has(ModDataComponents.STORED_QUIVER.get()))){
                QuiverData contents = quiverLike.getOrDefault(ModDataComponents.QUIVER_DATA.get(), QuiverData.EMPTY);
                int selected = quiverLike.getOrDefault(ModDataComponents.SELECTED.get(), 0);

                if (selected < contents.getSlots()){
                    ItemStack selectedStack = contents.getStackInSlot(selected);
                    if (!selectedStack.isEmpty()){
                        return selectedStack;
                    }
                    else{
                        player.sendOverlayMessage(Component.translatable("message.archerythings.no_quiver_slot"));
                    }
                }
                else{
                    player.sendOverlayMessage(Component.translatable("message.archerythings.no_quiver_slot"));
                }
            }
            else if (!quiverLike.isEmpty()){
                player.sendOverlayMessage(Component.translatable("message.archerythings.no_quiver_slot"));
            }
        }
        return ammo;
    }

    private static boolean hasArrow(ItemStack quiverLike){
        if (!quiverLike.isEmpty() && (quiverLike.getItem() == ModItems.QUIVER.get() || quiverLike.has(ModDataComponents.STORED_QUIVER.get()))){
            QuiverData contents = quiverLike.getOrDefault(ModDataComponents.QUIVER_DATA.get(), QuiverData.EMPTY);
            int selected = quiverLike.getOrDefault(ModDataComponents.SELECTED.get(), 0);

            if (selected < contents.getSlots()){
                ItemStack selectedStack = contents.getStackInSlot(selected);
                return !selectedStack.isEmpty();
            }
        }
        return false;
    }
}
