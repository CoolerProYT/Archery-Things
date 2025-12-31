package com.coolerpromc.archerythings.item;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.QuiverData;
import com.coolerpromc.archerythings.item.custom.ModQuiverItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ArcheryThings.MODID);

    public static final DeferredItem<ModQuiverItem> QUIVER = registerItem("quiver", properties -> new ModQuiverItem(properties.component(ModDataComponents.QUIVER_DATA.get(), QuiverData.EMPTY).component(ModDataComponents.SELECTED.get(), 0)));

    private static <T extends Item> DeferredItem<T> registerItem(String name, Function<Item.Properties, ? extends T> item){
        return ITEMS.registerItem(name, item);
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
