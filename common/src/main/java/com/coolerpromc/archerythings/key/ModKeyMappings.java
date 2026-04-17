package com.coolerpromc.archerythings.key;

import com.coolerpromc.archerythings.Constants;
import com.coolerpromc.archerythings.platform.Services;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

public class ModKeyMappings {
    public static final KeyMapping.Category ARCHERY_THINGS_CATEGORY = Services.REGISTRY.registerKeyCategory(Constants.id("category"));
    public static final Supplier<KeyMapping> OPEN_QUIVER_MENU = Services.REGISTRY.registerKeyMapping("key.archerythings.open_quiver_menu", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, ARCHERY_THINGS_CATEGORY);
}
