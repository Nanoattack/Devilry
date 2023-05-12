package io.github.nano.devilry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.container.MortarMenu;
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
    public void render(@NotNull PoseStack stack, int x, int y, float partialTicks)
    {
        this.renderBackground(stack);
        super.render(stack, x, y, partialTicks);
        this.renderTooltip(stack, x, y);
        this.renderComponentHoverEffect(stack, null, x, y);
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTicks, int pMouseX, int pMouseY)
    {
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, GUI);
        int i = this.leftPos;
        int j = this.topPos;
        //todo
    }
}
