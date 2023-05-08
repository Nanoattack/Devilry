package io.github.nano.devilry.devilry.entity.custom.render;

import io.github.nano.devilry.devilry.entity.custom.OwlEntity;
import io.github.nano.devilry.devilry.entity.custom.model.OwlModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class OwlRenderer extends GeoEntityRenderer<OwlEntity>
{
    public OwlRenderer(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new OwlModel());
        this.shadowRadius = 0.4F; //change to the desired shadow size.
    }
}