package com.coolerpromc.archerythings.network.packet;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.compat.AccessoriesHelper;
import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.item.ModItems;
import com.coolerpromc.archerythings.screen.quiver.QuiverMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;

public record ServerBoundSelectQuiverSlotPacket(int slot, Optional<InteractionHand> hand) implements CustomPacketPayload {
    public static final Type<ServerBoundSelectQuiverSlotPacket> TYPE = new Type<>(ArcheryThings.id("select_quiver_slot"));

    public static final StreamCodec<RegistryFriendlyByteBuf, InteractionHand> INTERACTION_HAND_STREAM_CODEC = StreamCodec.of(
            FriendlyByteBuf::writeEnum,
            buf -> buf.readEnum(InteractionHand.class)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerBoundSelectQuiverSlotPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ServerBoundSelectQuiverSlotPacket::slot,
            ByteBufCodecs.optional(INTERACTION_HAND_STREAM_CODEC),
            ServerBoundSelectQuiverSlotPacket::hand,
            ServerBoundSelectQuiverSlotPacket::new
    );

    public static void handle(ServerBoundSelectQuiverSlotPacket packet, IPayloadContext context){
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player && player.containerMenu instanceof QuiverMenu menu){
                ItemStack quiver = ItemStack.EMPTY;

                if (packet.hand.isPresent() && player.getItemInHand(packet.hand.get()).is(ModItems.QUIVER.get())){
                    quiver = player.getItemInHand(packet.hand.get());
                }
                else if(player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.QUIVER.get())){
                    quiver = player.getItemBySlot(EquipmentSlot.CHEST);
                }
                else if (packet.hand.isPresent() && player.getItemInHand(packet.hand.get()).has(ModDataComponents.STORED_QUIVER.get())){
                    quiver = player.getItemInHand(packet.hand.get()).get(ModDataComponents.STORED_QUIVER.get()).stack();
                }
                else if(player.getItemBySlot(EquipmentSlot.CHEST).has(ModDataComponents.STORED_QUIVER.get())){
                    quiver = player.getItemBySlot(EquipmentSlot.CHEST).get(ModDataComponents.STORED_QUIVER.get()).stack();
                }
                else if(player.getItemBySlot(EquipmentSlot.LEGS).has(ModDataComponents.STORED_QUIVER.get())){
                    quiver = player.getItemBySlot(EquipmentSlot.LEGS).get(ModDataComponents.STORED_QUIVER.get()).stack();
                }
                else if (ArcheryThings.isQuiverEquipped(player)){
                    quiver = AccessoriesHelper.getQuiver(player);
                }

                quiver.set(ModDataComponents.SELECTED.get(), packet.slot);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
