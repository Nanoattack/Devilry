package io.github.nano.devilry.client.entity.render;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.entity.OwlEntity;
import io.github.nano.devilry.client.entity.model.OwlModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
//todo

public class OwlRenderer extends LivingEntityRenderer<OwlEntity, OwlModel>
{
    //todo variants
    public static final ResourceLocation BARN_OWL = new ResourceLocation(ModMain.MOD_ID, "textures/entity/owl.png");

    public OwlRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new OwlModel(pContext.bakeLayer(OwlModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull OwlEntity pEntity) {
        return BARN_OWL;
    }
}