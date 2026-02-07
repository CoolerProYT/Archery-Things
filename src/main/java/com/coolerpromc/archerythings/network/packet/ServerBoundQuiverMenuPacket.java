package com.coolerpromc.archerythings.network.packet;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.compat.AccessoriesHelper;
import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.item.ModItems;
import com.coolerpromc.archerythings.screen.quiver.QuiverMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ServerBoundQuiverMenuPacket() implements CustomPacketPayload {
    public static final Type<ServerBoundQuiverMenuPacket> TYPE = new Type<>(ArcheryThings.id("quiver_menu_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerBoundQuiverMenuPacket> STREAM_CODEC = StreamCodec.unit(new ServerBoundQuiverMenuPacket());

    public static void handle(ServerBoundQuiverMenuPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player){
                if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.QUIVER.get()) || player.getItemBySlot(EquipmentSlot.CHEST).has(ModDataComponents.STORED_QUIVER.get()) || player.getItemBySlot(EquipmentSlot.LEGS).has(ModDataComponents.STORED_QUIVER.get()) || ArcheryThings.isQuiverEquipped(player)){
                    player.openMenu(new SimpleMenuProvider((id, inv, playerEntity) -> new QuiverMenu(id, inv, player, null), Component.translatable("item.archerythings.quiver")), buf -> buf.writeBoolean(false));
                }
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
