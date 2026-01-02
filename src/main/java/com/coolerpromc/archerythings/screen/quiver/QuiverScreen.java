package com.coolerpromc.archerythings.screen.quiver;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.network.packet.ServerBoundSelectQuiverSlotPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.Optional;

public class QuiverScreen extends AbstractContainerScreen<QuiverMenu> {
    public static final ResourceLocation TEXTURE = ArcheryThings.id("textures/gui/quiver.png");

    public QuiverScreen(QuiverMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageHeight = 133;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        Slot slot = this.menu.slots.get(36 + this.menu.selected());
        int x = this.leftPos + slot.x - 1;
        int y = this.topPos + slot.y - 1;
        guiGraphics.fill(x, y, x + 18, y + 1, ARGB.color(0xFF, 16755200));
        guiGraphics.fill(x, y + 17, x + 18, y + 18, ARGB.color(0xFF, 16755200));
        guiGraphics.fill(x, y, x + 1, y + 18, ARGB.color(0xFF, 16755200));
        guiGraphics.fill(x + 17, y, x + 18, y + 18, ARGB.color(0xFF, 16755200));

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.key() >= 49 && event.key() <= 57){
            int key = event.key() - 49;
            this.menu.quiver.set(ModDataComponents.SELECTED, key);
            this.menu.broadcastChanges();
            ClientPlayNetworking.send(new ServerBoundSelectQuiverSlotPacket(key, this.menu.hand == null ? Optional.empty() : Optional.of(this.menu.hand)));
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY == -1){
            int selected = this.menu.selected() + 1 > 8 ? 0 : this.menu.selected() + 1;
            this.menu.quiver.set(ModDataComponents.SELECTED, selected);
            this.menu.broadcastChanges();
            ClientPlayNetworking.send(new ServerBoundSelectQuiverSlotPacket(selected, Optional.ofNullable(this.menu.hand)));
        }
        if (scrollY == 1){
            int selected = this.menu.selected() - 1 < 0 ? 8 : this.menu.selected() - 1;
            this.menu.quiver.set(ModDataComponents.SELECTED, selected);
            this.menu.broadcastChanges();
            ClientPlayNetworking.send(new ServerBoundSelectQuiverSlotPacket(selected, Optional.ofNullable(this.menu.hand)));
        }
        return true;
    }
}
