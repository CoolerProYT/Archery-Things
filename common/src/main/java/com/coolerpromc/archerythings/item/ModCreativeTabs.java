package com.coolerpromc.archerythings.item;

import com.coolerpromc.archerythings.platform.Services;
import com.coolerpromc.archerythings.platform.util.RegistryHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {
    public static final RegistryHandler<CreativeModeTab> ARCHERY_THINGS = Services.REGISTRY.registerCreativeTab("archery_things", () -> new ItemStack(ModItems.QUIVER.get()), Component.translatable("creativetab.archerythings"),
            (_) -> new ItemStack[]{ModItems.QUIVER.toStack()}
    );

    public static void load() {
    }
}
