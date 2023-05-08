package io.github.nano.devilry.devilry.events;

import io.github.nano.devilry.devilry.entity.ModEntityTypes;
import io.github.nano.devilry.devilry.entity.custom.OwlEntity;
import io.github.nano.devilry.devilry.ModMain;
import io.github.nano.devilry.devilry.entity.custom.render.OwlRenderer;
import io.github.nano.devilry.devilry.item.custom.SupplierSpawnEggItem;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = ModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.OWL.get(), OwlEntity.setCustomAttributes().build());
    }

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityType<?>> event) {
        SupplierSpawnEggItem.updateEggMap();
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntityTypes.OWL.get(), OwlRenderer::new);
    }
}
