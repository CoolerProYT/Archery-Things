package com.coolerpromc.archerythings.component.data;

import com.google.common.collect.Iterables;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class QuiverData implements TooltipProvider {
    private static final int NO_SLOT = -1;
    private static final int MAX_SIZE = 256;
    public static final QuiverData EMPTY = new QuiverData(NonNullList.create());
    public static final Codec<QuiverData> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, QuiverData> STREAM_CODEC;
    private final NonNullList<ItemStack> items;
    private final int hashCode;

    private QuiverData(NonNullList<ItemStack> items) {
        if (items.size() > 256) {
            throw new IllegalArgumentException("Got " + items.size() + " items, but maximum is 256");
        } else {
            this.items = items;
            this.hashCode = ItemStack.hashStackList(items);
        }
    }

    private QuiverData(int size) {
        this(NonNullList.withSize(size, ItemStack.EMPTY));
    }

    private QuiverData(List<ItemStack> items) {
        this(items.size());

        for(int i = 0; i < items.size(); ++i) {
            this.items.set(i, items.get(i));
        }

    }

    private static QuiverData fromSlots(List<QuiverData.Slot> slots) {
        OptionalInt optionalint = slots.stream().mapToInt(QuiverData.Slot::index).max();
        if (optionalint.isEmpty()) {
            return EMPTY;
        } else {
            QuiverData QuiverData = new QuiverData(optionalint.getAsInt() + 1);

            for(QuiverData.Slot QuiverData$slot : slots) {
                QuiverData.items.set(QuiverData$slot.index(), QuiverData$slot.item());
            }

            return QuiverData;
        }
    }

    public static QuiverData fromItems(List<ItemStack> items) {
        int i = findLastNonEmptySlot(items);
        if (i == -1) {
            return EMPTY;
        } else {
            QuiverData QuiverData = new QuiverData(i + 1);

            for(int j = 0; j <= i; ++j) {
                QuiverData.items.set(j, items.get(j).copy());
            }

            return QuiverData;
        }
    }

    private static int findLastNonEmptySlot(List<ItemStack> items) {
        for(int i = items.size() - 1; i >= 0; --i) {
            if (!((ItemStack)items.get(i)).isEmpty()) {
                return i;
            }
        }

        return -1;
    }

    private List<QuiverData.Slot> asSlots() {
        List<QuiverData.Slot> list = new ArrayList<>();

        for(int i = 0; i < this.items.size(); ++i) {
            ItemStack itemstack = this.items.get(i);
            if (!itemstack.isEmpty()) {
                list.add(new QuiverData.Slot(i, itemstack));
            }
        }

        return list;
    }

    public void copyInto(NonNullList<ItemStack> list) {
        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = i < this.items.size() ? (ItemStack)this.items.get(i) : ItemStack.EMPTY;
            list.set(i, itemstack.copy());
        }

    }

    public Iterable<ItemStack> nonEmptyItems() {
        return Iterables.filter(this.items, (p_331420_) -> !p_331420_.isEmpty());
    }

    public boolean equals(Object other) {
        boolean var10000;
        if (this == other) {
            var10000 = true;
        } else {
            if (other instanceof QuiverData quiverData) {
                if (ItemStack.listMatches(this.items, quiverData.items)) {
                    var10000 = true;
                    return var10000;
                }
            }

            var10000 = false;
        }

        return var10000;
    }

    public int hashCode() {
        return this.hashCode;
    }

    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        int i = 0;
        int j = 0;

        consumer.accept(Component.translatable("tooltip.archerythings.quiver_stored").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BLUE));

        for(ItemStack itemstack : this.nonEmptyItems()) {
            ++j;
            if (i <= 4) {
                ++i;
                consumer.accept(Component.translatable("item.container.item_count", itemstack.getHoverName(), itemstack.getCount()).withStyle(ChatFormatting.GRAY));
            }
        }

        if (j - i > 0) {
            consumer.accept(Component.translatable("item.container.more_items", j - i).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        }
    }

    public int getSlots() {
        return this.items.size();
    }

    public ItemStack getStackInSlot(int slot) {
        this.validateSlotIndex(slot);
        return this.items.get(slot).copy();
    }

    private void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= this.getSlots()) {
            throw new UnsupportedOperationException("Slot " + slot + " not in valid range - [0," + this.getSlots() + ")");
        }
    }

    static {
        CODEC = QuiverData.Slot.CODEC.sizeLimitedListOf(256).xmap(QuiverData::fromSlots, QuiverData::asSlots);
        STREAM_CODEC = ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list(256)).map(QuiverData::new, (p_331691_) -> p_331691_.items);
    }

    record Slot(int index, ItemStack item) {
        public static final Codec<QuiverData.Slot> CODEC = RecordCodecBuilder.create((p_331695_) -> p_331695_.group(Codec.intRange(0, 255).fieldOf("slot").forGetter(QuiverData.Slot::index), ItemStack.CODEC.fieldOf("item").forGetter(QuiverData.Slot::item)).apply(p_331695_, QuiverData.Slot::new));
    }
}
