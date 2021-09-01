package com.nano.devilry.world;

import com.google.common.collect.Lists;
import com.nano.devilry.ModMain;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModOreGeneration
{
    @SubscribeEvent
    public static void generatesOres(FMLLoadCompleteEvent event)
    {
        for(OreType ore : OreType.values())
        {
            OreFeatureConfig oreFeatureConfig = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                    ore.getBlock().defaultBlockState(), ore.getMaxVeinSize());

            ConfiguredPlacement configuredPlacement = Placement.DEPTH_AVERAGE.configured(
                    new DepthAverageConfig(ore.getMinHeight(), ore.getMaxHeight()));

            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,
                    ore.getBlock().getRegistryName(),
                    Feature.ORE.configured(oreFeatureConfig).decorated(configuredPlacement)
                            .squared().count(ore.getCount()));

            for(Biome biome : ForgeRegistries.BIOMES)
            {
                if(!biome.getBiomeCategory().equals(Biome.Category.NETHER)
                    && !biome.getBiomeCategory().equals(Biome.Category.THEEND))
                {
                    addFeatureToBiome(biome, GenerationStage.Decoration.UNDERGROUND_ORES,
                            WorldGenRegistries.CONFIGURED_FEATURE.get(ore.getBlock().getRegistryName()));
                }
            }

        }
    }
    private static void addFeatureToBiome(Biome biome, GenerationStage.Decoration decoration
            , ConfiguredFeature<?, ?> configuredFeature) {
        List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = new ArrayList<>(
                biome.getGenerationSettings().features()
        );

        while (biomeFeatures.size() <= decoration.ordinal()) {
            biomeFeatures.add(Lists.newArrayList());
        }

        List<Supplier<ConfiguredFeature<?, ?>>> features = new ArrayList<>(biomeFeatures.get(decoration.ordinal()));
        features.add(() -> configuredFeature);
        biomeFeatures.set(decoration.ordinal(), features);

        /* Change field_242484_f that contains the Configured Features of the Biome */
        ObfuscationReflectionHelper.setPrivateValue(BiomeGenerationSettings.class, biome.getGenerationSettings(),
                biomeFeatures, "field_242484_f");
    }
}
