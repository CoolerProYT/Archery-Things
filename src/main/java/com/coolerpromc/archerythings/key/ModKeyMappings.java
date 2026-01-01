package com.coolerpromc.archerythings.key;

import com.coolerpromc.archerythings.network.packet.ServerBoundQuiverMenuPacket;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeyMappings {
    public static final KeyMapping OPEN_QUIVER_MENU = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.archerythings.open_quiver_menu", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KeyMapping.Category.GAMEPLAY));

    public static void register(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_QUIVER_MENU.consumeClick()) {
                ClientPlayNetworking.send(new ServerBoundQuiverMenuPacket());
            }
        });
    }
}
