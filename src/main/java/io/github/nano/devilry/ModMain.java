package io.github.nano.devilry;

import io.github.nano.devilry.block.ModBlocks;
import io.github.nano.devilry.blockentity.ModBlockEntities;
import io.github.nano.devilry.container.ModContainers;
import io.github.nano.devilry.data.recipes.ModRecipeTypes;
import io.github.nano.devilry.entity.ModEntityTypes;
import io.github.nano.devilry.events.ModSoundEvents;
import io.github.nano.devilry.item.ModItems;
import io.github.nano.devilry.networking.ModMessages;
import io.github.nano.devilry.particles.ModParticles;
import io.github.nano.devilry.screen.CarvingScreen;
import io.github.nano.devilry.screen.DemonicAltarScreen;
import io.github.nano.devilry.screen.MortarScreen;
import io.github.nano.devilry.util.ModPOI;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
//fixme //todo


@Mod(ModMain.MOD_ID)
public class ModMain
{
    public static final String MOD_ID = "devilry";

    public ModMain()
    {
        IEventBus eventbus = FMLJavaModLoadingContext.get().getModEventBus();

        CreativeModeTabRegistry.CREATIVE_MODE_TABS.register(eventbus);
        ModBlocks.register(eventbus);

        ModItems.register(eventbus);
        ModBlockEntities.register(eventbus);
        ModContainers.register(eventbus);

        ModRecipeTypes.register(eventbus);
        ModSoundEvents.register(eventbus);
        ModParticles.PARTICLE_TYPES.register(eventbus);


        ModEntityTypes.register(eventbus);
        ModPOI.POI.register(eventbus);


        eventbus.addListener(this::setup);
        eventbus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        MenuScreens.register(ModContainers.MORTAR_CONTAINER.get(), MortarScreen::new);
        MenuScreens.register(ModContainers.DEMONIC_ALTAR_CONTAINER.get(), DemonicAltarScreen::new);
        MenuScreens.register(ModContainers.CARVING_CONTAINER.get(), CarvingScreen::new);
        ModMessages.register();
    }
}
