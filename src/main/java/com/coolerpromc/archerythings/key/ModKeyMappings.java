package com.coolerpromc.archerythings.key;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.network.packet.ServerBoundQuiverMenuPacket;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = ArcheryThings.MODID, value = Dist.CLIENT)
public class ModKeyMappings {
    public static final KeyMapping.Category ARCHERY_THINGS_CATEGORY = new KeyMapping.Category(ArcheryThings.id("category"));
    public static final Lazy<KeyMapping> OPEN_QUIVER_MENU = Lazy.of(() -> new KeyMapping("key.archerythings.open_quiver_menu", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KeyMapping.Category.GAMEPLAY));

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.registerCategory(ARCHERY_THINGS_CATEGORY);
        event.register(OPEN_QUIVER_MENU.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (OPEN_QUIVER_MENU.get().consumeClick()) {
            ClientPacketDistributor.sendToServer(new ServerBoundQuiverMenuPacket());
        }
    }
}
