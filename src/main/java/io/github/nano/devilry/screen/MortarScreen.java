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
        blitWithColor(guiGraphics.pose(), GUI,i + 44, i + 44 + 88, j + 26, j + 26 + 51, 0, 44, 132, 192, 243, menu.getColor().x, menu.getColor().y, menu.getColor().z, 1);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int x, int y, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, x, y, partialTicks);
        this.renderTooltip(guiGraphics, x, y);
        guiGraphics.renderComponentHoverEffect(this.font, null, x, y);
    }

    void blitWithColor(PoseStack pose, ResourceLocation textureLocation, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2, float red, float green, float blue, float alpha) {
        RenderSystem.setShaderTexture(0, textureLocation);
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.enableBlend();
        Matrix4f matrix4f = pose.last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        bufferbuilder.vertex(matrix4f, (float)x1, (float)y1, (float)z).color(red, green, blue, alpha).uv(u1, v1).endVertex();
        bufferbuilder.vertex(matrix4f, (float)x1, (float)y2, (float)z).color(red, green, blue, alpha).uv(u1, v2).endVertex();
        bufferbuilder.vertex(matrix4f, (float)x2, (float)y2, (float)z).color(red, green, blue, alpha).uv(u2, v2).endVertex();
        bufferbuilder.vertex(matrix4f, (float)x2, (float)y1, (float)z).color(red, green, blue, alpha).uv(u2, v1).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.disableBlend();
    }
}
