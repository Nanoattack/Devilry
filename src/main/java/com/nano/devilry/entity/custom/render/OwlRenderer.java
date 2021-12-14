package com.nano.devilry.entity.custom.render;

import com.nano.devilry.ModMain;
import com.nano.devilry.entity.custom.OwlEntity;
import com.nano.devilry.entity.custom.model.OwlModel;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OwlRenderer extends MobRenderer<OwlEntity, OwlModel<OwlEntity>> {
    protected static final ResourceLocation TEXTURE =
            new ResourceLocation(ModMain.MOD_ID, "textures/entity/owl.png");

    public OwlRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn, new OwlModel<>(), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(OwlEntity p_114482_) {
        return TEXTURE;
    }
}
