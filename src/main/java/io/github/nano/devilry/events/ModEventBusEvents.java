package io.github.nano.devilry.events;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.client.entity.model.OwlModel;
import io.github.nano.devilry.client.entity.render.OwlRenderer;
import io.github.nano.devilry.entity.ModEntityTypes;
import io.github.nano.devilry.entity.OwlEntity;
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
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(OwlModel.LAYER_LOCATION, OwlModel::createBodyLayer);
    }
}
