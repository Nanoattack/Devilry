package com.nano.devilry.world.gen;

import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacements
{
      public static final PlacedFeature LIMESTONE_GEODE = PlacementUtils.register("limestone_geode",
              ModConfiguredFeatures.LIMESTONE_GEODE.placed(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
                      HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(30)), BiomeFilter.biome()));

    public static final PlacedFeature ORE_LIMESTONE_UPPER = PlacementUtils.register("ore_limestone_upper",
            ModOreFeatures.ORE_LIMESTONE.placed(rareOrePlacement(6,
                    HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(128)))));

    public static final PlacedFeature ORE_LIMESTONE_LOWER = PlacementUtils.register("ore_limestone_lower",
            ModOreFeatures.ORE_LIMESTONE.placed(commonOrePlacement(2,
                    HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(60)))));

    public static final PlacedFeature ORE_TIN = PlacementUtils.register("ore_tin",
            ModOreFeatures.ORE_TIN_SMALL.placed(commonOrePlacement(8,
                    HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112)))));

    public static final PlacedFeature ORE_TIN_LARGE = PlacementUtils.register("ore_tin_large",
            ModOreFeatures.ORE_TIN_LARGE.placed(commonOrePlacement(6,
                    HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112)))));


    public static List<PlacementModifier> worldSurfaceSquaredWithCount(int p_195475_) {
            return List.of(CountPlacement.of(p_195475_), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
      }

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }
 }
