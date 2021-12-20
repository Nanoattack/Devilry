package com.nano.devilry;

import com.nano.devilry.block.ModBlocks;
import com.nano.devilry.blockentity.ModBlockEntities;
import com.nano.devilry.container.ModContainers;
import com.nano.devilry.data.recipes.ModRecipeTypes;
import com.nano.devilry.entity.ModEntityTypes;
import com.nano.devilry.events.ModSoundEvents;
import com.nano.devilry.item.ModItems;
import com.nano.devilry.screen.MortarScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import static net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer;

// The value here should match an entry in the META-INF/mods.toml file
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

        ModRecipeTypes.register(eventbus);
        ModSoundEvents.register(eventbus);

        ModEntityTypes.register(eventbus);

        GeckoLib.initialize();

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
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        setRenderLayer(ModBlocks.BRONZE_BARS.get(), RenderType.cutout());
        setRenderLayer(ModBlocks.BRONZE_CHAIN.get(), RenderType.cutout());
        setRenderLayer(ModBlocks.BRONZE_LANTERN.get(), RenderType.cutout());
        setRenderLayer(ModBlocks.MORTAR.get(), RenderType.cutout());

        setRenderLayer(ModBlocks.SALTPETRE_CLUSTER.get(), RenderType.cutout());
        setRenderLayer(ModBlocks.LARGE_SALTPETRE_BUD.get(), RenderType.cutout());
        setRenderLayer(ModBlocks.MEDIUM_SALTPETRE_BUD.get(), RenderType.cutout());
        setRenderLayer(ModBlocks.SMALL_SALPETRE_BUD.get(), RenderType.cutout());

        MenuScreens.register(ModContainers.MORTAR_CONTAINER.get(), MortarScreen::new);

    }
}
