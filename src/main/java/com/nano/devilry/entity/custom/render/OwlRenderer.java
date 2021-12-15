package com.nano.devilry.entity.custom.render;

import com.nano.devilry.entity.custom.OwlEntity;
import com.nano.devilry.entity.custom.model.OwlModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.example.client.model.entity.ExampleEntityModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class OwlRenderer extends GeoEntityRenderer<OwlEntity>
{
    public OwlRenderer(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new OwlModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }
}