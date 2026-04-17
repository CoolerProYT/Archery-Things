package com.coolerpromc.archerythings.platform;

import com.coolerpromc.archerythings.Constants;
import com.coolerpromc.archerythings.platform.services.IRegistryHelper;
import com.coolerpromc.archerythings.platform.util.MenuFactory;
import com.coolerpromc.archerythings.platform.util.RegistryHandler;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public <T extends Item> RegistryHandler<T> registerItem(String name, Function<Item.Properties, T> func) {
        ResourceKey<Item> key = IRegistryHelper.itemKey(name);
        Identifier id = key.identifier();
        Holder<T> holder = Registry.registerForHolder(BuiltInRegistries.ITEM, id, func.apply(new Item.Properties().setId(key)));

        return new RegistryHandler<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public Holder<T> holder() {
                return holder;
            }

            @Override
            public T get() {
                return holder.value();
            }
        };
    }

    @Override
    public RegistryHandler<CreativeModeTab> registerCreativeTab(String name, Supplier<ItemStack> icon, Component title, Function<CreativeModeTab.ItemDisplayParameters, ItemStack[]> func) {
        Identifier id = Constants.id(name);
        Holder<CreativeModeTab> holder = Registry.registerForHolder(BuiltInRegistries.CREATIVE_MODE_TAB, id, FabricCreativeModeTab.builder().icon(icon).title(title).displayItems((parameters, output) -> Arrays.stream(func.apply(parameters)).forEach(output::accept)).build());

        return new RegistryHandler<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public Holder<CreativeModeTab> holder() {
                return holder;
            }

            @Override
            public CreativeModeTab get() {
                return holder.value();
            }
        };
    }

    @Override
    public <T extends AbstractContainerMenu, D> RegistryHandler<MenuType<T>> registerMenu(String name, MenuFactory<T, D> factory, StreamCodec<? super RegistryFriendlyByteBuf, D> data) {
        Identifier id = Constants.id(name);
        Holder<MenuType<T>> holder = Registry.registerForHolder(BuiltInRegistries.MENU, id, new ExtendedMenuType<>(factory::create, data));

        return new RegistryHandler<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public Holder<MenuType<T>> holder() {
                return holder;
            }

            @Override
            public MenuType<T> get() {
                return holder.value();
            }
        };
    }

    @Override
    public <T> RegistryHandler<DataComponentType<T>> registerDataComponent(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        Identifier id = Constants.id(name);
        Holder<DataComponentType<T>> holder = Registry.registerForHolder(BuiltInRegistries.DATA_COMPONENT_TYPE, id, builder.apply(DataComponentType.builder()).build());

        return new RegistryHandler<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public Holder<DataComponentType<T>> holder() {
                return holder;
            }

            @Override
            public DataComponentType<T> get() {
                return holder.value();
            }
        };
    }

    @Override
    public Supplier<KeyMapping> registerKeyMapping(String name, InputConstants.Type type, int value, KeyMapping.Category category) {
        KeyMapping mapping = new KeyMapping(name, type, value, category);
        return () -> mapping;
    }

    @Override
    public KeyMapping.Category registerKeyCategory(Identifier identifier) {
        return KeyMapping.Category.register(identifier);
    }
}
