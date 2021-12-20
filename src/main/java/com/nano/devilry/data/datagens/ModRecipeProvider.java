package com.nano.devilry.data.datagens;

import com.nano.devilry.ModMain;
import com.nano.devilry.block.ModBlocks;
import com.nano.devilry.item.ModItems;
import com.nano.devilry.util.tags.DevilryTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.IRecipeContainer;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        // GENERAL RECIPES

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.BONE_MEAL), ModItems.BONE_ASH.get(), 0.35f, 200)
                .unlockedBy("has_item", has(Items.BONE_MEAL))
                .save(consumer, modId("bone_ash_campfire_cooking"));

        ShapedRecipeBuilder.shaped(ModItems.MORTAR.get())
                .define('D', Items.POLISHED_DEEPSLATE)
                .define('S', Items.POLISHED_DEEPSLATE_SLAB)
                .pattern("D D")
                .pattern("DSD")
                .unlockedBy("has_item", has(Blocks.POLISHED_DEEPSLATE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.PESTLE.get())
                .define('C', Items.CALCITE)
                .define('T', DevilryTags.Items.INGOTS_TIN)
                .pattern("  T")
                .pattern(" C ")
                .pattern("C  ")
                .unlockedBy("has_item", has(Blocks.CALCITE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.FLINT_KNIFE.get())
                .define('F', Items.FLINT)
                .define('S', Items.STICK)
                .pattern(" F ")
                .pattern("S  ")
                .unlockedBy("has_item", has(Items.FLINT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.BRONZE_KNIFE.get())
                .define('B', ModItems.BRONZE_INGOT.get())
                .define('S', Items.STICK)
                .pattern(" B ")
                .pattern("S  ")
                .unlockedBy("has_item", has(ModItems.BRONZE_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(Items.SOUL_TORCH, 12)
                .define('s', ModItems.SULPHUR_DUST.get())
                .define('S', Items.STICK)
                .pattern("s  ")
                .pattern("S  ")
                .unlockedBy("has_item", has(ModItems.SULPHUR_DUST.get()))
                .save(consumer, "soul_torch_from_sulphur");

        ShapedRecipeBuilder.shaped(Items.SOUL_CAMPFIRE)
                .define('s', ModItems.SULPHUR_DUST.get())
                .define('S', Items.STICK)
                .define('L', ItemTags.LOGS)
                .pattern(" S ")
                .pattern("SsS")
                .pattern("LLL")
                .unlockedBy("has_item", has(ModItems.SULPHUR_DUST.get()))
                .save(consumer, "soul_campfire_from_sulphur");

        ShapelessRecipeBuilder.shapeless(Items.BLAZE_POWDER, 3)
                .requires(Items.BLAZE_ROD)
                .requires(DevilryTags.Items.PESTLE_IN_MORTAR)
                .unlockedBy("has_item", has(Items.BLAZE_ROD))
                .save(consumer, "blaze_powder_from_pestle");

        ShapelessRecipeBuilder.shapeless(ModItems.CURED_FLESH.get())
                .requires(Items.ROTTEN_FLESH)
                .requires(ModItems.SALTPETRE.get())
                .unlockedBy("has_item", has(ModItems.SALTPETRE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(Items.BONE_MEAL, 4)
                .requires(Items.BONE)
                .requires(DevilryTags.Items.PESTLE_IN_MORTAR)
                .unlockedBy("has_item", has(Items.BONE))
                .save(consumer, "bone_meal_from_pestle");

        ShapelessRecipeBuilder.shapeless(Items.SUGAR, 3)
                .requires(Items.SUGAR_CANE)
                .requires(DevilryTags.Items.PESTLE_IN_MORTAR)
                .unlockedBy("has_item", has(Items.SUGAR_CANE))
                .save(consumer, "sugar_from_pestle");

        ShapelessRecipeBuilder.shapeless(Items.HONEY_BOTTLE)
                .requires(Items.HONEYCOMB)
                .requires(Items.GLASS_BOTTLE)
                .requires(DevilryTags.Items.PESTLE_IN_MORTAR)
                .unlockedBy("has_item", has(Items.HONEYCOMB))
                .save(consumer, "honey_bottle_from_pestle");
        //STONE

        ShapedRecipeBuilder.shaped(ModBlocks.POLISHED_LIMESTONE.get())
                .define('L', ModBlocks.LIMESTONE.get())
                .pattern("LL")
                .pattern("LL")
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.POLISHED_LIMESTONE_STAIRS.get(), 4)
                .define('L', ModBlocks.POLISHED_LIMESTONE.get())
                .pattern("L  ")
                .pattern("LL ")
                .pattern("LLL")
                .unlockedBy("has_item", has(ModBlocks.POLISHED_LIMESTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.POLISHED_LIMESTONE_SLAB.get(), 6)
                .define('L', ModBlocks.POLISHED_LIMESTONE.get())
                .pattern("LLL")
                .unlockedBy("has_item", has(ModBlocks.POLISHED_LIMESTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.LIMESTONE_STAIRS.get(), 4)
                .define('L', ModBlocks.LIMESTONE.get())
                .pattern("L  ")
                .pattern("LL ")
                .pattern("LLL")
                .unlockedBy("has_item", has(ModBlocks.POLISHED_LIMESTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.LIMESTONE_SLAB.get(), 6)
                .define('L', ModBlocks.LIMESTONE.get())
                .pattern("LLL")
                .unlockedBy("has_item", has(ModBlocks.POLISHED_LIMESTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.CALCITE_STAIRS.get(), 4)
                .define('C', Blocks.CALCITE)
                .pattern("C  ")
                .pattern("CC ")
                .pattern("CCC")
                .unlockedBy("has_item", has(Blocks.CALCITE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.CALCITE_SLAB.get(), 6)
                .define('C', Blocks.CALCITE)
                .pattern("CCC")
                .unlockedBy("has_item", has(Blocks.CALCITE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.TUFF_STAIRS.get(), 4)
                .define('T', Blocks.TUFF)
                .pattern("T  ")
                .pattern("TT ")
                .pattern("TTT")
                .unlockedBy("has_item", has(Blocks.TUFF))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.TUFF_SLAB.get(), 6)
                .define('T', Blocks.TUFF)
                .pattern("TTT")
                .unlockedBy("has_item", has(Blocks.TUFF))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.DRIPSTONE_STAIRS.get(), 4)
                .define('D', Blocks.DRIPSTONE_BLOCK)
                .pattern("D  ")
                .pattern("DD ")
                .pattern("DDD")
                .unlockedBy("has_item", has(Blocks.DRIPSTONE_BLOCK))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.DRIPSTONE_SLAB.get(), 6)
                .define('D', Blocks.DRIPSTONE_BLOCK)
                .pattern("DDD")
                .unlockedBy("has_item", has(Blocks.DRIPSTONE_BLOCK))
                .save(consumer);

        //STONECUTTING

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), ModBlocks.POLISHED_LIMESTONE.get())
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer, "polished_limestone_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), ModBlocks.LIMESTONE_SLAB.get(), 2)
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer, "limestone_slab_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), ModBlocks.LIMESTONE_STAIRS.get())
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer, "limestone_slairs_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.POLISHED_LIMESTONE.get()), ModBlocks.POLISHED_LIMESTONE_STAIRS.get())
                .unlockedBy("has_item", has(ModBlocks.POLISHED_LIMESTONE.get()))
                .save(consumer, "polished_limestone_stairs_from_polished");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), ModBlocks.POLISHED_LIMESTONE_STAIRS.get())
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer, "polished_limestone_stairs_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.POLISHED_LIMESTONE.get()), ModBlocks.POLISHED_LIMESTONE_SLAB.get(), 2)
                .unlockedBy("has_item", has(ModBlocks.POLISHED_LIMESTONE.get()))
                .save(consumer, "polished_limestone_slab_from_polished");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), ModBlocks.POLISHED_LIMESTONE_SLAB.get(), 2)
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer, "polished_limestone_slab_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.CALCITE), ModBlocks.CALCITE_SLAB.get(), 2)
                .unlockedBy("has_item", has(Blocks.CALCITE))
                .save(consumer, "calcite_slab_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.CALCITE), ModBlocks.CALCITE_STAIRS.get())
                .unlockedBy("has_item", has(Blocks.CALCITE))
                .save(consumer, "calcite_stairs_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.TUFF), ModBlocks.TUFF_SLAB.get(), 2)
                .unlockedBy("has_item", has(Blocks.TUFF))
                .save(consumer, "tuff_slab_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.TUFF), ModBlocks.TUFF_STAIRS.get())
                .unlockedBy("has_item", has(Blocks.TUFF))
                .save(consumer, "tuff_stairs_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.DRIPSTONE_BLOCK), ModBlocks.DRIPSTONE_SLAB.get(), 2)
                .unlockedBy("has_item", has(Blocks.DRIPSTONE_BLOCK))
                .save(consumer, "dripstone_slab_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.DRIPSTONE_BLOCK), ModBlocks.DRIPSTONE_STAIRS.get())
                .unlockedBy("has_item", has(Blocks.DRIPSTONE_BLOCK))
                .save(consumer, "dripstone_stairs_from_stonecutting");

        //TIN
        ShapelessRecipeBuilder.shapeless(ModBlocks.TIN_BLOCK.get())
                .requires(ModItems.TIN_INGOT.get(), 9)
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_TIN))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.TIN_INGOT.get(), 9)
                .requires (ModBlocks.TIN_BLOCK.get())
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_TIN))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.TIN_INGOT.get())
                .requires (ModItems.TIN_NUGGET.get(), 9)
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_TIN))
                .save(consumer, "tin_ingot_from_nugget");

        ShapelessRecipeBuilder.shapeless(ModItems.TIN_NUGGET.get(), 9)
                .requires (ModItems.TIN_INGOT.get())
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_TIN))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.RAW_TIN_BLOCK.get())
                .requires(ModItems.RAW_TIN.get(), 9)
                .unlockedBy("has_item", has(ModItems.RAW_TIN.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.RAW_TIN.get(), 9)
                .requires (ModBlocks.RAW_TIN_BLOCK.get())
                .unlockedBy("has_item", has(ModItems.RAW_TIN.get()))
                .save(consumer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_TIN.get()), ModItems.TIN_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_item", has(ModItems.RAW_TIN.get()))
                .save(consumer, modId("tin_ingot_smelting"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_TIN.get()), ModItems.TIN_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_item", has(ModItems.RAW_TIN.get()))
                .save(consumer, modId("tin_ingot_blasting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.TIN_ORE.get()), ModItems.TIN_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_item", has(ModBlocks.TIN_ORE.get()))
                .save(consumer, modId("tin_ingot_smelting_ore"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.TIN_ORE.get()), ModItems.TIN_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_item", has(ModBlocks.TIN_ORE.get()))
                .save(consumer, modId("tin_ingot_blasting_ore"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.DEEPSLATE_TIN_ORE.get()), ModItems.TIN_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_item", has(ModBlocks.DEEPSLATE_TIN_ORE.get()))
                .save(consumer, modId("tin_ingot_smelting_deepslate_ore"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.DEEPSLATE_TIN_ORE.get()), ModItems.TIN_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_item", has(ModBlocks.DEEPSLATE_TIN_ORE.get()))
                .save(consumer, modId("tin_ingot_blasting_deepslate_ore"));

        //BRONZE
        ShapelessRecipeBuilder.shapeless(ModBlocks.BRONZE_BLOCK.get())
                .requires(ModItems.BRONZE_INGOT.get(), 9)
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.BRONZE_INGOT.get(), 9)
                .requires (ModBlocks.BRONZE_BLOCK.get())
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.BRONZE_INGOT.get())
                .requires (ModItems.BRONZE_NUGGET.get(), 9)
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer, "bronze_ingot_from_nugget");

        ShapelessRecipeBuilder.shapeless(ModItems.BRONZE_NUGGET.get(), 9)
                .requires (DevilryTags.Items.INGOTS_BRONZE)
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer);

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.BRONZE_BLEND.get()), ModItems.BRONZE_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_item", has(ModItems.BRONZE_BLEND.get()))
                .save(consumer, modId("bronze_ingot_from_blasting"));

        ShapedRecipeBuilder.shaped(ModBlocks.BRONZE_BARS.get(), 16)
                .define('B', DevilryTags.Items.INGOTS_BRONZE)
                .pattern("BBB")
                .pattern("BBB")
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.BRONZE_LANTERN.get())
                .define('B', ModItems.BRONZE_NUGGET.get())
                .define('T', Items.TORCH)
                .pattern("BBB")
                .pattern("BTB")
                .pattern("BBB")
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.BRONZE_CHAIN.get())
                .define('B', DevilryTags.Items.INGOTS_BRONZE)
                .define('b', ModItems.BRONZE_NUGGET.get())
                .pattern("b")
                .pattern("B")
                .pattern("b")
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer);

        //COPPER
        ShapelessRecipeBuilder.shapeless(Items.COPPER_INGOT)
                .requires (ModItems.COPPER_NUGGET.get(), 9)
                .unlockedBy("has_item", has(Items.COPPER_INGOT))
                .save(consumer, "copper_ingot_from_nugget");

        ShapelessRecipeBuilder.shapeless(ModItems.COPPER_NUGGET.get(), 9)
                .requires(Items.COPPER_INGOT)
                .unlockedBy("has_item", has(Items.COPPER_INGOT))
                .save(consumer);

    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(ModMain.MOD_ID, path);
    }
}