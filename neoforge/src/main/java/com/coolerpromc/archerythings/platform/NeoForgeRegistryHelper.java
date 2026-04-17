package com.coolerpromc.archerythings.platform;

import com.coolerpromc.archerythings.Constants;
import com.coolerpromc.archerythings.platform.services.IRegistryHelper;
import com.coolerpromc.archerythings.platform.util.MenuFactory;
import com.coolerpromc.archerythings.platform.util.RegistryHandler;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class NeoForgeRegistryHelper implements IRegistryHelper {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Constants.MODID);
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Constants.MODID);

    @Override
    public <T extends Item> RegistryHandler<T> registerItem(String name, Function<Item.Properties, T> func) {
        DeferredItem<T> item = ITEMS.registerItem(name, func);

        return new RegistryHandler<T>() {
            @Override
            public Identifier id() {
                return item.getId();
            }

            @Override
            public Holder<T> holder() {
                return (Holder<T>) item.getDelegate();
            }

            @Override
            public T get() {
                return item.get();
            }
        };
    }

    @Override
    public RegistryHandler<CreativeModeTab> registerCreativeTab(String name, Supplier<ItemStack> icon, Component title, Function<CreativeModeTab.ItemDisplayParameters, ItemStack[]> func) {
        DeferredHolder<CreativeModeTab, CreativeModeTab> tab = CREATIVE_TABS.register(name, () -> CreativeModeTab.builder().icon(icon).title(title).displayItems(((param, output) -> Arrays.stream(func.apply(param)).forEach(output::accept))).build());

        return new RegistryHandler<>() {
            @Override
            public Identifier id() {
                return tab.getId();
            }

            @Override
            public Holder<CreativeModeTab> holder() {
                return tab.getDelegate();
            }

            @Override
            public CreativeModeTab get() {
                return tab.value();
            }
        };
    }

    @Override
    public <T extends AbstractContainerMenu, D> RegistryHandler<MenuType<T>> registerMenu(String name, MenuFactory<T, D> factory, StreamCodec<? super RegistryFriendlyByteBuf, D> data) {
        DeferredHolder<MenuType<?>, MenuType<T>> menu = MENUS.register(name, () -> IMenuTypeExtension.create((id, inv, buf) -> factory.create(id, inv, data.decode(buf))));

        return new RegistryHandler<>() {
            @Override
            public Identifier id() {
                return menu.getId();
            }

            @Override
            public Holder<MenuType<T>> holder() {
                return (Holder<MenuType<T>>) (Holder<?>) menu.getDelegate();
            }

            @Override
            public MenuType<T> get() {
                return menu.get();
            }
        };
    }

    @Override
    public <T> RegistryHandler<DataComponentType<T>> registerDataComponent(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        DeferredHolder<DataComponentType<?>, DataComponentType<T>> component = DATA_COMPONENTS.register(name, () -> builder.apply(DataComponentType.builder()).build());

        return new RegistryHandler<>() {
            @Override
            public Identifier id() {
                return component.getId();
            }

            @Override
            public Holder<DataComponentType<T>> holder() {
                return (Holder<DataComponentType<T>>) (Holder<?>) component.getDelegate();
            }

            @Override
            public DataComponentType<T> get() {
                return component.get();
            }
        };
    }

    @Override
    public Supplier<KeyMapping> registerKeyMapping(String name, InputConstants.Type type, int value, KeyMapping.Category category) {
        return Lazy.of(() -> new KeyMapping(name, KeyConflictContext.IN_GAME, type, value, category));
    }

    @Override
    public KeyMapping.Category registerKeyCategory(Identifier identifier) {
        return new KeyMapping.Category(identifier);
    }

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
        CREATIVE_TABS.register(eventBus);
        MENUS.register(eventBus);
        DATA_COMPONENTS.register(eventBus);
    }
}
