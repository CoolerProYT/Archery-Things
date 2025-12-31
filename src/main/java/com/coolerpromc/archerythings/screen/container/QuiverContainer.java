package com.coolerpromc.archerythings.screen.container;

import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.QuiverData;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public class QuiverContainer extends SimpleContainer {
    private final ItemStack quiver;

    public QuiverContainer(ItemStack quiver) {
        super(9);
        this.quiver = quiver;
        QuiverData contents = quiver.getOrDefault(ModDataComponents.QUIVER_DATA, QuiverData.EMPTY);
        contents.copyInto(this.getItems());
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.quiver.set(ModDataComponents.QUIVER_DATA, QuiverData.fromItems(this.getItems()));
    }
}
