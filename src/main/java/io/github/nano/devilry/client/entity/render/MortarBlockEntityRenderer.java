package io.github.nano.devilry.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.blockentity.MortarBlockEntity;
import io.github.nano.devilry.client.entity.model.PestleModel;
import io.github.nano.devilry.item.ModItems;
import io.github.nano.devilry.item.custom.Pestle;
import io.github.nano.devilry.networking.ModMessages;
import io.github.nano.devilry.networking.packets.UpdateTurningPacket;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MortarBlockEntityRenderer implements BlockEntityRenderer<MortarBlockEntity> {
    private static final RenderType RENDER_TYPE = RenderType.entityCutout(new ResourceLocation(ModMain.MOD_ID, "textures/entity/netherite_pestle_block.png"));
    private static final RenderType NETHERITE_RENDER_TYPE = RenderType.entityCutout(new ResourceLocation(ModMain.MOD_ID, "textures/entity/pestle_block.png"));
    private final PestleModel pestleModel;

    public MortarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        pestleModel = new PestleModel(context.bakeLayer(PestleModel.LAYER_LOCATION));
    }

    @Override
    public void render(MortarBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemStack pestle = pBlockEntity.itemHandler.getStackInSlot(0);

        if (pestle.getItem() instanceof Pestle) {
            VertexConsumer consumer = pBufferSource.getBuffer(getRenderType(pestle));
            pPoseStack.pushPose();
            pPoseStack.mulPoseMatrix(new Matrix4f().rotate(((float) Math.toRadians(180)), new Vector3f(1, 0, 0)));
            pPoseStack.translate(0.5,-1.15,-0.5);
            pPoseStack.scale(0.75f, 0.75f, 0.75f);
            pestleModel.pestle.yRot = pBlockEntity.time * 0.3f;
            pestleModel.pestle2.xRot = ((float) Math.cos(pBlockEntity.fixedTime * 0.1f))*0.3f;
            if (pBlockEntity.isTurning && pestleModel.pestle.yRot > Math.PI) {
                pBlockEntity.shouldStop = true;
            }
            if (pBlockEntity.shouldStop && pestleModel.pestle.yRot < Math.PI || pestleModel.pestle.yRot > (2 * Math.PI)) {
                pBlockEntity.isTurning = false;
                pBlockEntity.shouldStop = false;
                ModMessages.sendToServer(new UpdateTurningPacket(pBlockEntity.getBlockPos()));
            }
            pestleModel.renderToBuffer(pPoseStack, consumer, pPackedLight, pPackedOverlay, 1, 1, 1, 1);
            pPoseStack.popPose();
        }
    }

    public @NotNull RenderType getRenderType(@NotNull ItemStack pestle) {
        return pestle.is(ModItems.NETHERITE_PESTLE.get()) ? RENDER_TYPE : NETHERITE_RENDER_TYPE;
    }
}
