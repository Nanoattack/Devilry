package io.github.nano.devilry.screen;

import io.github.nano.devilry.container.CarvingMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
//todo

public class CarvingScreen extends AbstractContainerScreen<CarvingMenu>
{
    private final ResourceLocation gui;


    public CarvingScreen(CarvingMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        gui = pMenu.material.gui;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float x, int y, int partialTicks) {
        final int i = (this.width - imageWidth) / 2;
        final int j = (this.height - imageHeight) / 2;
        guiGraphics.blit(gui,  i, j, 0, 0, imageWidth, imageHeight, 256, 256);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int x, int y, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, x, y, partialTicks);
        this.renderTooltip(guiGraphics, x, y);
        guiGraphics.renderComponentHoverEffect(this.font, null, x, y);
    }
}
