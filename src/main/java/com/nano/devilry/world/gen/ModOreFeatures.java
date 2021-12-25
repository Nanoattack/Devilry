package com.nano.devilry.world.gen;

import com.nano.devilry.block.ModBlocks;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModOreFeatures
{
    public static final RuleTest NATURAL_STONE = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
    public static final RuleTest STONE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    public static final RuleTest DEEPSLATE_ORE_REPLACEABLES = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    public static final List<OreConfiguration.TargetBlockState> ORE_TIN_TARGET_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.TIN_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_TIN_ORE.get().defaultBlockState()));

    public static final ConfiguredFeature<?, ?> ORE_LIMESTONE = FeatureUtils.register("ore_limestone", Feature.ORE.configured(new OreConfiguration(NATURAL_STONE, ModBlocks.LIMESTONE.get().defaultBlockState(), 64)));
    public static final ConfiguredFeature<?, ?> ORE_TIN_SMALL = FeatureUtils.register("ore_tin_small", Feature.ORE.configured(new OreConfiguration(ORE_TIN_TARGET_LIST, 10)));
    public static final ConfiguredFeature<?, ?> ORE_TIN_LARGE = FeatureUtils.register("ore_tin_large", Feature.ORE.configured(new OreConfiguration(ORE_TIN_TARGET_LIST, 20)));



}
