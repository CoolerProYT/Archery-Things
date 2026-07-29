package com.coolerpromc.archerythings.component;

import com.coolerpromc.archerythings.component.data.QuiverData;
import com.coolerpromc.archerythings.component.data.StoredQuiver;
import com.coolerpromc.archerythings.platform.Services;
import com.coolerpromc.archerythings.platform.util.RegistryHandler;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;

public class ModDataComponents {
    public static final RegistryHandler<DataComponentType<QuiverData>> QUIVER_DATA = Services.REGISTRY.registerDataComponent("quiver_data", (p_341846_) -> p_341846_.persistent(QuiverData.CODEC).networkSynchronized(QuiverData.STREAM_CODEC));
    public static final RegistryHandler<DataComponentType<Integer>> SELECTED = Services.REGISTRY.registerDataComponent("selected", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).cacheEncoding());
    public static final RegistryHandler<DataComponentType<StoredQuiver>> STORED_QUIVER = Services.REGISTRY.registerDataComponent("stored_quiver", builder -> builder.persistent(StoredQuiver.CODEC).networkSynchronized(StoredQuiver.STREAM_CODEC));
    public static final RegistryHandler<DataComponentType<Boolean>> COLLECT_TO_QUIVER = Services.REGISTRY.registerDataComponent("collect_to_quiver", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).cacheEncoding());

    public static void load(){
    }
}
