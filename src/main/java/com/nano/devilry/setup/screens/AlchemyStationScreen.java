package com.nano.devilry.setup.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.nano.devilry.ModMain;
import com.nano.devilry.setup.container.AlchemyStationContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AlchemyStationScreen extends ContainerScreen<AlchemyStationContainer>
{

    private final ResourceLocation GUI = new ResourceLocation(ModMain.MOD_ID,
            "textures/gui/alchemy_station_gui.png");

    public AlchemyStationScreen(AlchemyStationContainer container, PlayerInventory inv, ITextComponent name)
    {
        super (container, inv, name);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y)
    {
        drawString(matrixStack, Minecraft.getInstance().font, "Dark Energy: 0",
                        28, 10, 0xffffff );
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int p_230450_4_)
    {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F );
        this.minecraft.getTextureManager().bind(GUI);
        int i = this.getGuiLeft();
        int j = this.getGuiTop();
        this.blit(matrixStack, i, j, 0 , 0,this.getXSize(), this.getYSize());

    }
}
