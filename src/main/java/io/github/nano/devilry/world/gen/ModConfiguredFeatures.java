package io.github.nano.devilry.devilry.world.gen;

import io.github.nano.devilry.devilry.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ConfiguredFeature<GeodeConfiguration, ?> LIMESTONE_GEODE =
            register("limestone_geode", Feature.GEODE.configured(new GeodeConfiguration(
                    new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
                            BlockStateProvider.simple(ModBlocks.LIMESTONE.get()),
                            BlockStateProvider.simple(ModBlocks.FESTERING_LIMESTONE.get()),
                            BlockStateProvider.simple(Blocks.DRIPSTONE_BLOCK),
                            BlockStateProvider.simple(Blocks.TUFF),
                            List.of(ModBlocks.SMALL_SALPETRE_BUD.get().defaultBlockState(),
                                    ModBlocks.MEDIUM_SALTPETRE_BUD.get().defaultBlockState(),
                                    ModBlocks.LARGE_SALTPETRE_BUD.get().defaultBlockState(),
                                    ModBlocks.SALTPETRE_CLUSTER.get().defaultBlockState()),
                            BlockTags.FEATURES_CANNOT_REPLACE.getName(),
                            BlockTags.GEODE_INVALID_BLOCKS.getName()),
                    new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D),
                    new GeodeCrackSettings(0.95D, 2.0D, 2), 0.35D, 0.083D, true,
                    UniformInt.of(4, 6), UniformInt.of(3, 4), UniformInt.of(1, 2),
                    -16, 16, 0.05D, 1)));

    private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String key,
                                                                                       ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }
}
