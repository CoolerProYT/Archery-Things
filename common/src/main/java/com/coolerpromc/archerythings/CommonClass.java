package com.coolerpromc.archerythings;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.item.ModCreativeTabs;
import com.coolerpromc.archerythings.item.ModItems;
import com.coolerpromc.archerythings.screen.ModMenuTypes;

public class CommonClass {
    public static void init() {
        ModDataComponents.load();
        ModMenuTypes.load();
        ModItems.load();
        ModCreativeTabs.load();
    }
}