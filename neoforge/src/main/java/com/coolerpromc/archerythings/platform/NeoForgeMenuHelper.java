package com.coolerpromc.archerythings.platform;

import com.coolerpromc.archerythings.platform.services.IMenuHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;

public class NeoForgeMenuHelper implements IMenuHelper {
    @Override
    public void openMenu(ServerPlayer player, MenuProvider provider, InteractionHand data) {
        player.openMenu(provider, buf -> buf.writeNullable(data, InteractionHand.STREAM_CODEC));
    }
}
