package com.coolerpromc.archerythings.item;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.QuiverData;
import com.coolerpromc.archerythings.item.custom.ModQuiverItem;
import com.coolerpromc.archerythings.platform.Services;
import com.coolerpromc.archerythings.platform.util.RegistryHandler;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {
    public static final RegistryHandler<ModQuiverItem> QUIVER = registerItem("quiver", properties -> new ModQuiverItem(properties.stacksTo(1).component(ModDataComponents.QUIVER_DATA.get(), QuiverData.EMPTY).component(ModDataComponents.SELECTED.get(), 0)));

    private static <T extends Item> RegistryHandler<T> registerItem(String name, Function<Item.Properties, T> item){
        return Services.REGISTRY.registerItem(name, item);
    }

    public static void load() {
    }
}
