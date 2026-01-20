package com.coolerpromc.archerythings;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.QuiverData;
import com.coolerpromc.archerythings.item.ModCreativeTabs;
import com.coolerpromc.archerythings.item.ModItems;
import com.coolerpromc.archerythings.item.custom.ModQuiverItem;
import com.coolerpromc.archerythings.network.NetworkRegistries;
import com.coolerpromc.archerythings.screen.ModMenuTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArcheryThings implements ModInitializer {
	public static final String MODID = "archerythings";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
        ModItems.register();
        ModCreativeTabs.register();
        ModDataComponents.register();
        ModMenuTypes.register();

        NetworkRegistries.register();

        UseItemCallback.EVENT.register((player, level, interactionHand) -> {
            ItemStack stack = player.getItemInHand(interactionHand);

            if (stack.has(ModDataComponents.STORED_QUIVER)){
                ItemStack itemStack = stack.get(ModDataComponents.STORED_QUIVER).stack();
                if(itemStack.getItem() instanceof ModQuiverItem item){
                    return item.use(player.level(), player, interactionHand);
                }
            }
            return InteractionResult.PASS;
        });
	}

    public static void onArrowLoose(Player player, ItemStack bow, Level level, boolean hasAmmo) {
        ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
        if(stack.isEmpty()){
            stack = player.getItemBySlot(EquipmentSlot.LEGS);
        }

        if (stack.has(ModDataComponents.STORED_QUIVER)){
            stack = stack.get(ModDataComponents.STORED_QUIVER).stack();
        }

        if (!stack.isEmpty()) {
            QuiverData contents = stack.get(ModDataComponents.QUIVER_DATA);;
            if (contents != null && contents != QuiverData.EMPTY) {
                int selected = stack.getOrDefault(ModDataComponents.SELECTED, 0);

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

                        stack.set(ModDataComponents.QUIVER_DATA, QuiverData.fromItems(updatedItems));
                    }
                }
            }
        }
    }

    public static ResourceLocation id(String path){
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}