package com.nano.devilry.world;

import com.nano.devilry.ModMain;
import com.nano.devilry.world.gen.ModPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModMain.MOD_ID)
public class ModWorldEvents
{
        @SubscribeEvent
        public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
//        ModOreGeneration.generateOres(event);
//        ModStructureGeneration.generateStructures(event);
//        ModTreeGeneration.generateTrees(event);
//        ModFlowerGeneration.generateFlowers(event);

            if (event.getCategory() == Biome.BiomeCategory.UNDERGROUND) {
                event.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModPlacements.LIMESTONE_GEODE);
            }
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacements.ORE_LIMESTONE_UPPER);
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacements.ORE_LIMESTONE_LOWER);
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacements.ORE_TIN);
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacements.ORE_TIN_LARGE);
        }
}
