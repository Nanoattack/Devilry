package io.github.nano.devilry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.nano.devilry.devilry.ModMain;
import io.github.nano.devilry.devilry.container.WittlingContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
//fixme
//todo

public class WittlingScreen extends AbstractContainerScreen<WittlingContainer>
{
    private final ResourceLocation GUI = new ResourceLocation(ModMain.MOD_ID,
            "textures/gui/wittling_table_gui.png");

    public WittlingScreen(WittlingContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void render(PoseStack stack, int x, int y, float partialTicks)
    {
        this.renderBackground(stack);
        super.render(stack, x, y, partialTicks);
        this.renderTooltip(stack, x, y);
        this.renderComponentHoverEffect(stack, null, x, y);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY)
    {
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, GUI);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(pPoseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if(menu.isCrafting()) {
            int k = this.menu.getScaledProgress();
            this.blit(pPoseStack, i + 108, j + 35, 176, 14, k + 1, 16);
        //    this.font.draw(pPoseStack, " " + menu.getScaledProgress(), 25f, 25f, 500000);
        }
    }
}
