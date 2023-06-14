package io.github.nano.devilry.events;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.blockentity.ModBlockEntities;
import io.github.nano.devilry.client.entity.model.OwlModel;
import io.github.nano.devilry.client.entity.model.PestleModel;
import io.github.nano.devilry.client.entity.render.MortarBlockEntityRenderer;
import io.github.nano.devilry.client.entity.render.OwlRenderer;
import io.github.nano.devilry.container.entity.ModEntityTypes;
import io.github.nano.devilry.container.entity.OwlEntity;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//todo

@Mod.EventBusSubscriber(modid = ModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.OWL.get(), OwlEntity.setCustomAttributes().build());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntityTypes.OWL.get(), OwlRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MORTAR_ENTITY.get(), MortarBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(OwlModel.LAYER_LOCATION, OwlModel::createBodyLayer);
        event.registerLayerDefinition(PestleModel.LAYER_LOCATION, PestleModel::createBodyLayer);
    }
}
