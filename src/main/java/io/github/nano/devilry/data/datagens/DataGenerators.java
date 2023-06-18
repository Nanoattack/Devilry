package io.github.nano.devilry.data.datagens;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.data.datagens.client.ModBlockStateProvider;
import io.github.nano.devilry.data.datagens.client.ModItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
//todo

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = ModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {
    private DataGenerators() {

    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(true, new ModBlockStateProvider(gen.getPackOutput(), existingFileHelper));
        gen.addProvider(true, new ModItemModelProvider(gen.getPackOutput(), existingFileHelper));
        gen.addProvider(true, new ModLootTableProvider(gen.getPackOutput()));
        gen.addProvider(true, new ModRecipeProvider(gen.getPackOutput()));
    }
}