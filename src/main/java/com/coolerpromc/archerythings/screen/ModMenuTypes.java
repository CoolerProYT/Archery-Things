package com.coolerpromc.archerythings.screen;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.screen.quiver.QuiverMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, ArcheryThings.MODID);

    public static final Supplier<MenuType<QuiverMenu>> QUIVER_MENU = MENU_TYPES.register("quiver_menu", () -> IMenuTypeExtension.create((i, inventory, buf) ->
            new QuiverMenu(i, inventory, inventory.player, buf.readBoolean() ? buf.readEnum(InteractionHand.class) : null)));

    public static void register(IEventBus eventBus){
        MENU_TYPES.register(eventBus);
    }
}
