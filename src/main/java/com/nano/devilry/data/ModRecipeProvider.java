package com.nano.devilry.data;

import net.minecraft.data.*;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import com.nano.devilry.ModMain;
import com.nano.devilry.setup.ModBlocks;
import com.nano.devilry.setup.ModItems;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {

        // GENERAL RECIPES

        ShapedRecipeBuilder.shaped(ModItems.ALCHEMICAL_BLEND.get())
                .define('R', Items.REDSTONE)
                .define('G', Items.GLOWSTONE_DUST)
                .define('g', Items.GUNPOWDER)
                .define('S', Items.SUGAR)
                .define('B', ModItems.BONE_ASH.get())
                .define('b', Items.BLAZE_POWDER)
                .pattern("BSB")
                .pattern("gRG")
                .pattern("bBb")
                .unlockedBy("has_item", has(ModItems.BONE_ASH.get()))
                .save(consumer);

        CookingRecipeBuilder.smelting(Ingredient.of(Items.BONE_MEAL), ModItems.BONE_ASH.get(), 0.7f, 200)
                .unlockedBy("has_item", has(Items.BONE_MEAL))
                .save(consumer, modId("bone_ash_smelting"));

         // TIN RELATED RECIPES

        ShapelessRecipeBuilder.shapeless(ModItems.TIN_INGOT.get(), 9)
                .requires(ModBlocks.TIN_BLOCK.get())
                .unlockedBy("has_item", has(ModItems.TIN_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.TIN_BLOCK.get())
                .define('#', ModItems.TIN_INGOT.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ModItems.TIN_INGOT.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.TIN_NUGGET.get(), 9)
                .requires(ModItems.TIN_INGOT.get())
                .unlockedBy("has_item", has(ModItems.TIN_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.TIN_INGOT.get())
                .define('#', ModItems.TIN_NUGGET.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ModItems.TIN_INGOT.get()))
                .save(consumer, "tin_ingot_from_nugget");

        CookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.TIN_ORE.get()), ModItems.TIN_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_item", has(ModBlocks.TIN_ORE.get()))
                .save(consumer, modId("tin_ingot_smelting"));

        CookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.TIN_ORE.get()), ModItems.TIN_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_item", has(ModBlocks.TIN_ORE.get()))
                .save(consumer, modId("tin_ingot_blasting"));

        // COPPER RELATED RECIPES

        ShapelessRecipeBuilder.shapeless(ModItems.COPPER_INGOT.get(), 9)
                .requires(ModBlocks.COPPER_BLOCK.get())
                .unlockedBy("has_item", has(ModItems.COPPER_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.COPPER_BLOCK.get())
                .define('#', ModItems.COPPER_INGOT.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ModItems.COPPER_INGOT.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.COPPER_NUGGET.get(), 9)
                .requires(ModItems.COPPER_INGOT.get())
                .unlockedBy("has_item", has(ModItems.COPPER_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.COPPER_INGOT.get())
                .define('#', ModItems.COPPER_NUGGET.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ModItems.COPPER_INGOT.get()))
                .save(consumer, "copper_ingot_from_nugget");

        CookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.COPPER_ORE.get()), ModItems.COPPER_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_item", has(ModBlocks.COPPER_ORE.get()))
                .save(consumer, modId("copper_ingot_smelting"));

        CookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.COPPER_ORE.get()), ModItems.COPPER_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_item", has(ModBlocks.COPPER_ORE.get()))
                .save(consumer, modId("copper_ingot_blasting"));

        // BRONZE RELATED RECIPES


        ShapelessRecipeBuilder.shapeless(ModItems.BRONZE_INGOT.get(), 9)
                .requires(ModBlocks.BRONZE_BLOCK.get())
                .unlockedBy("has_item", has(ModItems.BRONZE_INGOT.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.BRONZE_NUGGET.get(), 9)
                .requires(ModItems.BRONZE_INGOT.get())
                .unlockedBy("has_item", has(ModItems.BRONZE_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.BRONZE_BLOCK.get())
                .define('#', ModItems.BRONZE_INGOT.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ModItems.BRONZE_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.BRONZE_INGOT.get())
                .define('#', ModItems.BRONZE_NUGGET.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ModItems.BRONZE_INGOT.get()))
                .save(consumer, "bronze_ingot_from_nugget");

        ShapedRecipeBuilder.shaped(ModItems.BRONZE_BLEND.get())
                .define('C', ModItems.COPPER_NUGGET.get())
                .define('T', ModItems.TIN_NUGGET.get())
                .pattern(" C ")
                .pattern("CCC")
                .pattern("CTC")
                .unlockedBy("has_item", has(ModItems.COPPER_INGOT.get()))
                .save(consumer);

        CookingRecipeBuilder.blasting(Ingredient.of(ModItems.BRONZE_BLEND.get()), ModItems.BRONZE_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_item", has(ModItems.BRONZE_BLEND.get()))
                .save(consumer, modId("bronze_ingot_from_blasting"));

        // CORINTHIAN BRONZE RELATED

  //      SmithingRecipeBuilder.smithing(Ingredient.of(ModItems.CORINTHIAN_BRONZE_HELMET.get()), Ingredient.of(Items.GOLDEN_HELMET), ModItems.BRONZE_INGOT.get())
  //              .save(consumer, modId("corinthian_helmet_smithing"));
            }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(ModMain.MOD_ID, path);
    }
}