package com.coolerpromc.archerythings.item;

import com.coolerpromc.archerythings.ArcheryThings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {
    public static final CreativeModeTab ARCHERY_THINGS = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ArcheryThings.id("archery_things"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.QUIVER))
                    .title(Component.translatable("creativetab.archerythings"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.QUIVER);
                    })
                    .build()
    );

    public static void register() {

    }
}
