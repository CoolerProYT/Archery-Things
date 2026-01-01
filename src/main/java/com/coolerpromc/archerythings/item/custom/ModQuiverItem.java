package com.coolerpromc.archerythings.item.custom;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.screen.quiver.QuiverMenu;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public class ModQuiverItem extends Item {
    public ModQuiverItem(Properties properties) {
        super(properties.component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.CHEST).setAsset(ResourceKey.create(EquipmentAssets.ROOT_ID, ArcheryThings.id("quiver"))).build()));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!player.isCrouching()){
            if (player instanceof ServerPlayer serverPlayer){
                serverPlayer.openMenu(new ExtendedScreenHandlerFactory<Optional<InteractionHand>>() {
                    @Override
                    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                        return new QuiverMenu(i, inventory, player, hand);
                    }

                    @Override
                    public Component getDisplayName() {
                        return Component.translatable("item.archerythings.quiver");
                    }

                    @Override
                    public Optional<InteractionHand> getScreenOpeningData(ServerPlayer player) {
                        return Optional.of(hand);
                    }
                });
            }
            return InteractionResult.SUCCESS;
        }
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        if (stack.has(ModDataComponents.QUIVER_DATA) && stack.get(ModDataComponents.QUIVER_DATA) instanceof TooltipProvider provider){
            provider.addToTooltip(context, tooltipAdder, flag, stack);
        }
    }
}
