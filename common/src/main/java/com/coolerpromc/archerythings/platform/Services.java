package com.coolerpromc.archerythings.platform;

import com.coolerpromc.archerythings.Constants;
import com.coolerpromc.archerythings.platform.services.*;

import java.util.ServiceLoader;

public class Services {
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);
    public static final IMenuHelper MENU = load(IMenuHelper.class);
    public static final INetworkHelper NETWORK = load(INetworkHelper.class);
    public static final IQuiverHelper QUIVER = load(IQuiverHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz, Services.class.getClassLoader()).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}