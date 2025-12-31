package com.coolerpromc.archerythings.item;

import com.coolerpromc.archerythings.ArcheryThings;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArcheryThings.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ARCHERY_THINGS = CREATIVE_MOD_TABS.register("archery_things",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.QUIVER.get()))
                    .title(Component.translatable("creativetab.archerythings"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.QUIVER);
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MOD_TABS.register(eventBus);
    }
}
