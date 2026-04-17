package com.coolerpromc.archerythings.network.packet;

import com.coolerpromc.archerythings.Constants;
import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.item.ModItems;
import com.coolerpromc.archerythings.platform.Services;
import com.coolerpromc.archerythings.platform.util.PayloadContext;
import com.coolerpromc.archerythings.screen.quiver.QuiverMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;

public record ServerBoundQuiverMenuPacket() implements CustomPacketPayload {
    public static final Type<ServerBoundQuiverMenuPacket> TYPE = new Type<>(Constants.id("quiver_menu_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerBoundQuiverMenuPacket> STREAM_CODEC = StreamCodec.unit(new ServerBoundQuiverMenuPacket());

    public void handle(PayloadContext context){
        context.execute(() -> {
            if (context.player() instanceof ServerPlayer player){
                if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.QUIVER.get()) || player.getItemBySlot(EquipmentSlot.CHEST).has(ModDataComponents.STORED_QUIVER.get()) || player.getItemBySlot(EquipmentSlot.LEGS).has(ModDataComponents.STORED_QUIVER.get())){
                    Services.MENU.openMenu(player, new SimpleMenuProvider(QuiverMenu::new, Component.translatable("item.archerythings.quiver")), null);
                }
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
