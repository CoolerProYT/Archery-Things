package com.coolerpromc.archerythings.screen.quiver;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.item.ModItems;
import com.coolerpromc.archerythings.screen.ModMenuTypes;
import com.coolerpromc.archerythings.screen.container.QuiverContainer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class QuiverMenu extends AbstractContainerMenu {
    public final ItemStack quiver;
    private final QuiverContainer container;
    public final Player player;
    public final InteractionHand hand;

    public QuiverMenu(int id, Inventory inventory, Player player, @Nullable InteractionHand hand) {
        super(ModMenuTypes.QUIVER_MENU.get(), id);
        if (hand != null && player.getItemInHand(hand).is(ModItems.QUIVER.get())){
            quiver = player.getItemInHand(hand);
        }
        else if (hand != null && player.getItemInHand(hand).has(ModDataComponents.STORED_QUIVER.get())){
            quiver = player.getItemInHand(hand).get(ModDataComponents.STORED_QUIVER.get()).stack();
        }
        else if(player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.QUIVER.get())){
            quiver = player.getItemBySlot(EquipmentSlot.CHEST);
        }
        else if(player.getItemBySlot(EquipmentSlot.CHEST).has(ModDataComponents.STORED_QUIVER.get())){
            quiver = player.getItemBySlot(EquipmentSlot.CHEST).get(ModDataComponents.STORED_QUIVER.get()).stack();
        }
        else if(player.getItemBySlot(EquipmentSlot.LEGS).has(ModDataComponents.STORED_QUIVER.get())){
            quiver = player.getItemBySlot(EquipmentSlot.LEGS).get(ModDataComponents.STORED_QUIVER.get()).stack();
        }
        else {
            quiver = ItemStack.EMPTY;
        }

        this.container = new QuiverContainer(quiver);
        this.player = player;
        this.hand = hand;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        for (int i = 0; i < 9; i++){
            this.addSlot(new Slot(container, i, 8 + i * 18, 17){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof ArrowItem;
                }
            });
        }
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 9;

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(pPlayer, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return !quiver.isEmpty() && player.getInventory().contains(quiver) || player.getItemInHand(InteractionHand.MAIN_HAND).has(ModDataComponents.STORED_QUIVER.get()) || player.getItemBySlot(EquipmentSlot.CHEST).has(ModDataComponents.STORED_QUIVER.get()) || player.getItemBySlot(EquipmentSlot.LEGS).has(ModDataComponents.STORED_QUIVER.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 51 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 109));
        }
    }

    public int selected(){
        return quiver.getOrDefault(ModDataComponents.SELECTED.get(), 0);
    }
}
