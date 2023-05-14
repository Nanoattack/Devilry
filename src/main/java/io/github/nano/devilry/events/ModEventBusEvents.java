package io.github.nano.devilry.events;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.block.ModBlocks;
import io.github.nano.devilry.entity.ModEntityTypes;
import io.github.nano.devilry.entity.OwlEntity;
import io.github.nano.devilry.client.entity.model.OwlModel;
import io.github.nano.devilry.client.entity.render.OwlRenderer;
import io.github.nano.devilry.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

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

    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(ModMain.MOD_ID, "devilry_materials"),
                builder -> builder.icon(() -> new ItemStack(ModItems.ALCHEMICAL_ESSENCE.get()))
                        .title(Component.translatableWithFallback("devilry.tab.devilry_materials", "Devilry Materials"))
                        .displayItems((feature, output) -> ModItems.DEVILRY_MATERIALS.stream()
                                .map(RegistryObject::get).forEach(output::accept)).build());

        event.registerCreativeModeTab(new ResourceLocation(ModMain.MOD_ID, "devilry_blocks"),
                builder -> builder.icon(() -> new ItemStack(ModBlocks.POLISHED_LIMESTONE.get()))
                        .title(Component.translatableWithFallback("devilry.tab.devilry_blocks", "Devilry Blocks"))
                        .displayItems((params, output) -> ModItems.DEVILRY_BLOCKS.stream()
                                .map(RegistryObject::get).forEach(output::accept)).build());

        event.registerCreativeModeTab(new ResourceLocation(ModMain.MOD_ID, "devilry_misc"),
                builder -> builder.icon(() -> new ItemStack(ModItems.PESTLE.get()))
                        .title(Component.translatableWithFallback("devilry.tab.devilry_misc", "Devilry Misc"))
                        .displayItems((params, output) -> ModItems.DEVILRY_MISC.stream()
                                .map(RegistryObject::get).forEach(output::accept)).build());
    }
}
