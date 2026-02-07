package com.coolerpromc.archerythings.item;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.QuiverData;
import com.coolerpromc.archerythings.item.custom.ModQuiverItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {
    public static final ModQuiverItem QUIVER = registerItem("quiver", properties -> new ModQuiverItem(properties.component(ModDataComponents.QUIVER_DATA, QuiverData.EMPTY).stacksTo(1).component(ModDataComponents.SELECTED, 0)));

    private static <T extends Item> T registerItem(String name, Function<Item.Properties, ? extends T> item){
        return Registry.register(BuiltInRegistries.ITEM, ArcheryThings.id(name), item.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ArcheryThings.id(name)))));
    }

    public static void register() {
    }
}
