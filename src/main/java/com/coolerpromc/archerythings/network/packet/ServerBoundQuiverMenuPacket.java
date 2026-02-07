package com.coolerpromc.archerythings.network.packet;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.item.ModItems;
import com.coolerpromc.archerythings.screen.quiver.QuiverMenu;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record ServerBoundQuiverMenuPacket() implements CustomPacketPayload {
    public static final Type<ServerBoundQuiverMenuPacket> TYPE = new Type<>(ArcheryThings.id("quiver_menu_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerBoundQuiverMenuPacket> STREAM_CODEC = StreamCodec.unit(new ServerBoundQuiverMenuPacket());

    public static void handle(ServerBoundQuiverMenuPacket packet, ServerPlayNetworking.Context context){
        context.server().execute(() -> {
            if (context.player() instanceof ServerPlayer player){
                if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.QUIVER) || player.getItemBySlot(EquipmentSlot.CHEST).has(ModDataComponents.STORED_QUIVER) || player.getItemBySlot(EquipmentSlot.LEGS).has(ModDataComponents.STORED_QUIVER) || ArcheryThings.isQuiverEquipped(player)){
                    player.openMenu(new ExtendedScreenHandlerFactory<Optional<InteractionHand>>() {
                        @Override
                        public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                            return new QuiverMenu(i, inventory, player, null);
                        }

                        @Override
                        public Component getDisplayName() {
                            return Component.translatable("item.archerythings.quiver");
                        }

                        @Override
                        public Optional<InteractionHand> getScreenOpeningData(ServerPlayer player) {
                            return Optional.empty();
                        }
                    });
                }
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
