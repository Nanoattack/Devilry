package io.github.nano.devilry.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.nano.devilry.container.CarvingMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;
//todo

public class CarvingScreen extends AbstractContainerScreen<CarvingMenu>
{
    private final ResourceLocation gui;


    public CarvingScreen(CarvingMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        gui = pMenu.material.gui;
        this.imageWidth = 176;
        this.imageHeight = 102;
        this.titleLabelX -= 0.5;
        this.titleLabelY += 10;
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

    @Override
    protected void init() {
        super.init();
        addButtons();
    }

    public void addButtons(){
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        Vector2i topLeft = new Vector2i(centerX-5 - (menu.x / 2 * 13 + 1), centerY-5 - (menu.y / 2 * 13 + 1));
        for (int x = 0; x < menu.x; x++) {
            for (int y = 0; y < menu.y; y++) {
                int finalX = x;
                int finalY = y;
                addRenderableWidget(new ImageButton(x * 13 + topLeft.x(), y * 13 + topLeft.y(), 12, 12, 0, 112, 12, gui, 256, 256, button -> {
                    button.active = false;
                    button.visible = false;
                    getMenu().chip(finalX, finalY, Minecraft.getInstance().level);
                }));
            }
        }
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int p_282681_, int p_283686_) {
        graphics.pose().pushPose();
        graphics.pose().scale(0.8f, 0.8f, 0.8f);
        graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        graphics.pose().popPose();
    }
}
