package io.github.nano.devilry;

import io.github.nano.devilry.block.ModBlocks;
import io.github.nano.devilry.blockentity.ModBlockEntities;
import io.github.nano.devilry.container.ModContainers;
import io.github.nano.devilry.data.recipes.ModRecipeTypes;
import io.github.nano.devilry.devilry.block.ModBlocks;
import io.github.nano.devilry.devilry.blockentity.ModBlockEntities;
import io.github.nano.devilry.devilry.container.ModContainers;
import io.github.nano.devilry.devilry.data.recipes.ModRecipeTypes;
import io.github.nano.devilry.devilry.entity.ModEntityTypes;
import io.github.nano.devilry.devilry.events.LootInjector;
import io.github.nano.devilry.devilry.events.ModSoundEvents;
import io.github.nano.devilry.devilry.item.ModItems;
import io.github.nano.devilry.devilry.screen.MortarScreen;
import io.github.nano.devilry.devilry.screen.WittlingScreen;
import io.github.nano.devilry.entity.ModEntityTypes;
import io.github.nano.devilry.entity.ai.MemoryRegistry;
import io.github.nano.devilry.events.ModSoundEvents;
import io.github.nano.devilry.item.ModItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
//fixme //todo


@Mod(ModMain.MOD_ID)
public class ModMain
{
    public static final String MOD_ID = "devilry";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public ModMain()
    {
        // Register the setup method for modloading
        IEventBus eventbus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventbus);
        ModBlocks.register(eventbus);
        ModBlockEntities.register(eventbus);
        ModContainers.register(eventbus);
        MemoryRegistry.register(eventbus);

        ModRecipeTypes.register(eventbus);
        ModSoundEvents.register(eventbus);

        ModEntityTypes.register(eventbus);


        MinecraftForge.EVENT_BUS.register(new LootInjector());

        eventbus.addListener(this::setup);
        eventbus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        event.enqueueWork(()->{
            SpawnPlacements.register(ModEntityTypes.OWL.get(), SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        setRenderLayer(ModBlocks.BRONZE_BARS.get(), RenderType.cutout());
        setRenderLayer(ModBlocks.BRONZE_CHAIN.get(), RenderType.cutout());
        setRenderLayer(ModBlocks.BRONZE_LANTERN.get(), RenderType.cutout());
        setRenderLayer(ModBlocks.MORTAR.get(), RenderType.cutout());
        setRenderLayer(ModBlocks.STOLAS_EFFIGY.get(), RenderType.translucent());

        setRenderLayer(ModBlocks.SALTPETRE_CLUSTER.get(), RenderType.cutout());
        setRenderLayer(ModBlocks.LARGE_SALTPETRE_BUD.get(), RenderType.cutout());
        setRenderLayer(ModBlocks.MEDIUM_SALTPETRE_BUD.get(), RenderType.cutout());
        setRenderLayer(ModBlocks.SMALL_SALPETRE_BUD.get(), RenderType.cutout());

        MenuScreens.register(ModContainers.MORTAR_CONTAINER.get(), MortarScreen::new);
        MenuScreens.register(ModContainers.WITTLING_CONTAINER.get(), WittlingScreen::new);

    }
}
