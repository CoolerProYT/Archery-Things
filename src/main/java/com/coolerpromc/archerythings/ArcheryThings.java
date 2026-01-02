package com.coolerpromc.archerythings;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.QuiverData;
import com.coolerpromc.archerythings.component.data.StoredQuiver;
import com.coolerpromc.archerythings.item.ModCreativeTabs;
import com.coolerpromc.archerythings.item.ModItems;
import com.coolerpromc.archerythings.item.custom.ModQuiverItem;
import com.coolerpromc.archerythings.screen.ModMenuTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingGetProjectileEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.slf4j.Logger;

@Mod(ArcheryThings.MODID)
public class ArcheryThings {
    public static final String MODID = "archerythings";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ArcheryThings(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingGetProjectile(LivingGetProjectileEvent event) {
        if (event.getEntity() instanceof Player player){
            ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
            if (stack.has(ModDataComponents.STORED_QUIVER.get())){
                stack = stack.get(ModDataComponents.STORED_QUIVER.get()).stack();
            }
            if (!stack.isEmpty() && (stack.getItem() == ModItems.QUIVER.get() || stack.has(ModDataComponents.STORED_QUIVER.get()))){
                QuiverData contents = stack.getOrDefault(ModDataComponents.QUIVER_DATA, QuiverData.EMPTY);
                int selected = stack.getOrDefault(ModDataComponents.SELECTED.get(), 0);

                if (selected < contents.getSlots()){
                    ItemStack selectedStack = contents.getStackInSlot(selected);
                    if (!selectedStack.isEmpty()){
                        event.setProjectileItemStack(selectedStack);
                    }
                    else{
                        player.displayClientMessage(Component.translatable("message.archerythings.no_quiver_slot"), true);
                    }
                }
                else{
                    player.displayClientMessage(Component.translatable("message.archerythings.no_quiver_slot"), true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingEntityUseItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().has(ModDataComponents.STORED_QUIVER)){
            ItemStack itemStack = event.getItemStack().get(ModDataComponents.STORED_QUIVER).stack();
            if(itemStack.getItem() instanceof ModQuiverItem item && event.getEntity() instanceof Player player){
                item.use(event.getEntity().level(), player, event.getHand());
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onArrowLoose(ArrowLooseEvent event) {
        Player player = event.getEntity();
        ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);

        if (stack.has(ModDataComponents.STORED_QUIVER.get())){
            stack = stack.get(ModDataComponents.STORED_QUIVER.get()).stack();
        }

        if (!stack.isEmpty()) {
            QuiverData contents = stack.get(ModDataComponents.QUIVER_DATA.get());;
            if (contents != null && contents != QuiverData.EMPTY) {
                int selected = stack.getOrDefault(ModDataComponents.SELECTED.get(), 0);

                if (selected >= 0 && selected < contents.getSlots()) {
                    ItemStack checkArrow = contents.getStackInSlot(selected);
                    if (checkArrow.isEmpty()) {
                        return;
                    }

                    NonNullList<ItemStack> updatedItems = NonNullList.withSize(contents.getSlots(), ItemStack.EMPTY);
                    contents.copyInto(updatedItems);

                    ItemStack arrow = updatedItems.get(selected);
                    if (!arrow.isEmpty() && event.hasAmmo() && event.getBow().getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).getLevel(event.getLevel().holderOrThrow(Enchantments.INFINITY)) != 1) {
                        arrow.shrink(1);
                        if (arrow.isEmpty()) {
                            updatedItems.set(selected, ItemStack.EMPTY);
                        }

                        stack.set(ModDataComponents.QUIVER_DATA.get(), QuiverData.fromItems(updatedItems));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.get(DataComponents.EQUIPPABLE) != null && left.get(DataComponents.EQUIPPABLE).slot().equals(EquipmentSlot.CHEST) && right.getItem() == ModItems.QUIVER.get() && !left.has(ModDataComponents.STORED_QUIVER.get())){
            ItemStack output = event.getLeft().copy();
            output.set(ModDataComponents.STORED_QUIVER.get(), new StoredQuiver(right));
            event.setOutput(output);
            event.setMaterialCost(1);
            event.setXpCost(1);
        }
    }


    public static ResourceLocation id(String path){
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
