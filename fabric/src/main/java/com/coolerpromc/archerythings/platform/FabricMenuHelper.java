package com.coolerpromc.archerythings.platform;

import com.coolerpromc.archerythings.platform.services.IMenuHelper;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class FabricMenuHelper implements IMenuHelper {
    @Override
    public void openMenu(ServerPlayer player, MenuProvider provider, InteractionHand data) {
        player.openMenu(new ExtendedMenuProvider<Optional<InteractionHand>>() {
            @Override
            public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
                return provider.createMenu(containerId, inventory, player);
            }

            @Override
            public Component getDisplayName() {
                return provider.getDisplayName();
            }

            @Override
            public Optional<InteractionHand> getScreenOpeningData(ServerPlayer serverPlayer) {
                return Optional.ofNullable(data);
            }
        });
    }
}
