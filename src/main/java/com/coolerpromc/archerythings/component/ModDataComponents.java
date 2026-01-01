package com.coolerpromc.archerythings.component;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.component.data.QuiverData;
import com.coolerpromc.archerythings.component.data.StoredQuiver;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DataComponentType<QuiverData> QUIVER_DATA = register("quiver_data", (p_341846_) -> p_341846_.persistent(QuiverData.CODEC).networkSynchronized(QuiverData.STREAM_CODEC));
    public static final DataComponentType<Integer> SELECTED = register("selected", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).cacheEncoding());
    public static final DataComponentType<StoredQuiver> STORED_QUIVER = register("stored_quiver", builder -> builder.persistent(StoredQuiver.CODEC).networkSynchronized(StoredQuiver.STREAM_CODEC));

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> func){
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ArcheryThings.id(name), func.apply(DataComponentType.builder()).build());
    }

    public static void register(){

    }
}
