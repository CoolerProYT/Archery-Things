package com.coolerpromc.archerythings.screen.quiver;

import com.coolerpromc.archerythings.Constants;
import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.network.packet.ServerBoundSelectQuiverSlotPacket;
import com.coolerpromc.archerythings.platform.Services;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.Optional;

public class QuiverScreen extends AbstractContainerScreen<QuiverMenu> {
    public static final Identifier TEXTURE = Constants.id("textures/gui/quiver.png");

    public QuiverScreen(QuiverMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 133);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Slot slot = this.menu.slots.get(36 + this.menu.selected());
        int x = this.leftPos + slot.x - 1;
        int y = this.topPos + slot.y - 1;
        guiGraphics.fill(x, y, x + 18, y + 1, ARGB.color(0xFF, 16755200));
        guiGraphics.fill(x, y + 17, x + 18, y + 18, ARGB.color(0xFF, 16755200));
        guiGraphics.fill(x, y, x + 1, y + 18, ARGB.color(0xFF, 16755200));
        guiGraphics.fill(x + 17, y, x + 18, y + 18, ARGB.color(0xFF, 16755200));

        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.key() >= 49 && event.key() <= 57){
            int key = event.key() - 49;
            this.menu.quiver.set(ModDataComponents.SELECTED.get(), key);
            this.menu.broadcastChanges();
            Services.NETWORK.sendToServer(new ServerBoundSelectQuiverSlotPacket(key, this.menu.hand == null ? Optional.empty() : Optional.of(this.menu.hand)));
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY == -1){
            int selected = this.menu.selected() + 1 > 8 ? 0 : this.menu.selected() + 1;
            this.menu.quiver.set(ModDataComponents.SELECTED.get(), selected);
            this.menu.broadcastChanges();
            Services.NETWORK.sendToServer(new ServerBoundSelectQuiverSlotPacket(selected, Optional.ofNullable(this.menu.hand)));
        }
        if (scrollY == 1){
            int selected = this.menu.selected() - 1 < 0 ? 8 : this.menu.selected() - 1;
            this.menu.quiver.set(ModDataComponents.SELECTED.get(), selected);
            this.menu.broadcastChanges();
            Services.NETWORK.sendToServer(new ServerBoundSelectQuiverSlotPacket(selected, Optional.ofNullable(this.menu.hand)));
        }
        return true;
    }
}
