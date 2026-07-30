package com.coolerpromc.archerythings.network.packet;

import com.coolerpromc.archerythings.Constants;
import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.StoredQuiver;
import com.coolerpromc.archerythings.item.ModItems;
import com.coolerpromc.archerythings.platform.Services;
import com.coolerpromc.archerythings.platform.util.PayloadContext;
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

import java.util.Optional;

public record ServerBoundToggleQuiverPickupPacket(boolean collect, Optional<InteractionHand> hand) implements CustomPacketPayload {
    public static final Type<ServerBoundToggleQuiverPickupPacket> TYPE = new Type<>(Constants.id("toggle_quiver_pickup"));

    public static final StreamCodec<RegistryFriendlyByteBuf, InteractionHand> INTERACTION_HAND_STREAM_CODEC = StreamCodec.of(
            FriendlyByteBuf::writeEnum,
            buf -> buf.readEnum(InteractionHand.class)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerBoundToggleQuiverPickupPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ServerBoundToggleQuiverPickupPacket::collect,
            ByteBufCodecs.optional(INTERACTION_HAND_STREAM_CODEC),
            ServerBoundToggleQuiverPickupPacket::hand,
            ServerBoundToggleQuiverPickupPacket::new
    );

    public void handle(PayloadContext context) {
        context.execute(() -> {
            if (!(context.player() instanceof ServerPlayer player) || !(player.containerMenu instanceof QuiverMenu)) {
                return;
            }

            boolean applied = hand.isPresent() && applyToggle(player.getItemInHand(hand.get()), this.collect);

            if (!applied) {
                applied = applyToggle(player.getItemBySlot(EquipmentSlot.CHEST), this.collect);
            }
            if (!applied) {
                applied = applyToggle(player.getItemBySlot(EquipmentSlot.LEGS), this.collect);
            }
            if (!applied && Services.QUIVER.isQuiverEquipped(player)) {
                Services.QUIVER.getQuiver(player).set(ModDataComponents.COLLECT_TO_QUIVER.get(), this.collect);
            }
        });
    }

    /**
     * Attempts to apply the collect toggle to an ItemStack, whether it is the
     * quiver itself or an item carrying a stored quiver (STORED_QUIVER).
     *
     * @return true if the toggle was applied somewhere, false otherwise.
     */
    private static boolean applyToggle(ItemStack stack, boolean collect) {
        if (stack.is(ModItems.QUIVER.get())) {
            stack.set(ModDataComponents.COLLECT_TO_QUIVER.get(), collect);
            return true;
        }

        if (stack.has(ModDataComponents.STORED_QUIVER.get())) {
            ItemStack quiverStack = stack.get(ModDataComponents.STORED_QUIVER.get()).stack();
            quiverStack.set(ModDataComponents.COLLECT_TO_QUIVER.get(), collect);
            stack.set(ModDataComponents.STORED_QUIVER.get(), new StoredQuiver(quiverStack));
            return true;
        }

        return false;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}