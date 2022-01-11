package com.nano.devilry.world.gen;

import com.nano.devilry.entity.ModEntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEntityGeneration {
    @SubscribeEvent
    public static void doSpawning(BiomeLoadingEvent event) {
        if (Biomes.DARK_FOREST.location().equals(event.getName())) {
            event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(ModEntityTypes.OWL.get(), 40, 1, 3));
        }
    }
}
