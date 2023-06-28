package io.github.nano.devilry.screen;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.container.MortarMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
//todo

public class MortarScreen extends AbstractContainerScreen<MortarMenu>
{
    private final ResourceLocation GUI = new ResourceLocation(ModMain.MOD_ID,
            "textures/gui/mortar_gui.png");


    public MortarScreen(MortarMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float x, int y, int partialTicks) {
        final int i = (this.width - imageWidth) / 2;
        final int j = (this.height - imageHeight) / 2;
        guiGraphics.blit(GUI,  i, j, 0, 0, imageWidth, imageHeight, 256, 256);
        guiGraphics.blit(GUI, i + 81, j + 28, 178, 0, 14, this.menu.getProgress());
        guiGraphics.blit(GUI, i + 44, j + 26, 44, 192, 88, 51, 1, 0, 0);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int x, int y, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, x, y, partialTicks);
        this.renderTooltip(guiGraphics, x, y);
        guiGraphics.renderComponentHoverEffect(this.font, null, x, y);
    }
}
