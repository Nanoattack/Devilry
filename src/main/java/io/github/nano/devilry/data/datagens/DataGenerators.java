package io.github.nano.devilry.devilry.data.datagens;

import io.github.nano.devilry.devilry.data.datagens.client.ModBlockStateProvider;
import io.github.nano.devilry.devilry.data.datagens.client.ModItemModelProvider;
import io.github.nano.devilry.devilry.ModMain;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
        private DataGenerators() {}

            @SubscribeEvent
            public static void gatherData(GatherDataEvent event) {
                DataGenerator gen = event.getGenerator();
                ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

                gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
                gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
                gen.addProvider(new ModLootTableProvider(gen));
                gen.addProvider(new ModRecipeProvider(gen));

                }
            }
