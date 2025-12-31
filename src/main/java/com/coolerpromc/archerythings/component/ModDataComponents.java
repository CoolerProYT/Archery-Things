package com.coolerpromc.archerythings.component;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.component.data.QuiverData;
import com.coolerpromc.archerythings.component.data.StoredQuiver;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ArcheryThings.MODID);

    public static final Supplier<DataComponentType<QuiverData>> QUIVER_DATA = DATA_COMPONENTS.registerComponentType("quiver_data", (p_341846_) -> p_341846_.persistent(QuiverData.CODEC).networkSynchronized(QuiverData.STREAM_CODEC));
    public static final Supplier<DataComponentType<Integer>> SELECTED = DATA_COMPONENTS.registerComponentType("selected", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).cacheEncoding());
    public static final Supplier<DataComponentType<StoredQuiver>> STORED_QUIVER = DATA_COMPONENTS.registerComponentType("stored_quiver", builder -> builder.persistent(StoredQuiver.CODEC).networkSynchronized(StoredQuiver.STREAM_CODEC));

    public static void register(IEventBus eventBus){
        DATA_COMPONENTS.register(eventBus);
    }
}
