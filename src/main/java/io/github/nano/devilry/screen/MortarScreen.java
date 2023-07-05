package io.github.nano.devilry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.container.MortarMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
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
        if (this.menu.hasRecipe()) {
            this.blit(guiGraphics, GUI, i + 44, j + 26, 44, 192, 88, 51);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int x, int y, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, x, y, partialTicks);
        this.renderTooltip(guiGraphics, x, y);
        guiGraphics.renderComponentHoverEffect(this.font, null, x, y);
    }

    void innerBlit(GuiGraphics guiGraphics, ResourceLocation p_283461_, int p_281399_, int p_283222_, int p_283615_, int p_283430_, float p_283247_, float p_282598_, float p_282883_, float p_283017_) {
        RenderSystem.setShaderTexture(0, p_283461_);
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.enableBlend();
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        bufferbuilder.vertex(matrix4f, (float)p_281399_, (float)p_283615_, (float) 1).color(menu.getColor().x(), menu.getColor().z(), menu.getColor().y(), 1).uv(p_283247_, p_282883_).endVertex();
        bufferbuilder.vertex(matrix4f, (float)p_281399_, (float)p_283430_, (float) 1).color(menu.getColor().x(), menu.getColor().z(), menu.getColor().y(), 1).uv(p_283247_, p_283017_).endVertex();
        bufferbuilder.vertex(matrix4f, (float)p_283222_, (float)p_283430_, (float) 1).color(menu.getColor().x(), menu.getColor().z(), menu.getColor().y(), 1).uv(p_282598_, p_283017_).endVertex();
        bufferbuilder.vertex(matrix4f, (float)p_283222_, (float)p_283615_, (float) 1).color(menu.getColor().x(), menu.getColor().z(), menu.getColor().y(), 1).uv(p_282598_, p_282883_).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.disableBlend();
    }

    void blit(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, float u, float v, int width, int height) {
        this.innerBlit(guiGraphics, texture, x, x + width, y, y + height, (u + 0.0F) / (float) 256, (u + (float)width) / (float) 256, (v + 0.0F) / (float) 256, (v + (float)height) / (float) 256);
    }
}
