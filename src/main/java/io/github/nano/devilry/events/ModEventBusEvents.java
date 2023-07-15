package io.github.nano.devilry.events;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.blockentity.ModBlockEntities;
import io.github.nano.devilry.client.entity.model.OwlModel;
import io.github.nano.devilry.client.entity.model.PestleModel;
import io.github.nano.devilry.client.entity.render.MortarBlockEntityRenderer;
import io.github.nano.devilry.client.entity.render.OwlRenderer;
import io.github.nano.devilry.entity.ModEntityTypes;
import io.github.nano.devilry.entity.OwlEntity;
import io.github.nano.devilry.particles.ModParticles;
import io.github.nano.devilry.particles.UndeadSoulParticle;
import io.github.nano.devilry.util.ModPOI;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.UNDEAD_SOUL.get(), UndeadSoulParticle.Provider::new);
    }
}
