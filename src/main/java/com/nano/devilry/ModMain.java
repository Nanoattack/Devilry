package com.nano.devilry;

import com.nano.devilry.setup.*;
import com.nano.devilry.setup.container.ModContainers;
import com.nano.devilry.setup.screens.AlchemyStationScreen;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ModMain.MOD_ID)
public class ModMain
{
    public static final String MOD_ID = "devilry";

    public static final ItemGroup DEVILRY_TAB = new ItemGroup("devilrytab") {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModItems.ELDRITCH_IDOL.get());
        }
    };

    private static final Logger LOGGER = LogManager.getLogger();

    public ModMain()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);

        Registration.register();

        MinecraftForge.EVENT_BUS.register(this);

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {

            RenderTypeLookup.setRenderLayer(ModBlocks.ALCHEMY_STATION.get(), RenderType.translucent());

            RenderTypeLookup.setRenderLayer(ModBlocks.DEVILS_SNARE_CROP.get(), RenderType.cutout());

            ScreenManager.register(ModContainers.ALCHEMY_STATION_CONTAINER.get(), AlchemyStationScreen::new);


        });
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {

            LOGGER.info("HELLO from Register Block");
        }
    }
}
