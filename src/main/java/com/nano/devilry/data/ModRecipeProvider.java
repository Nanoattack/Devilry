package com.nano.devilry.data;

import com.nano.devilry.ModMain;
import com.nano.devilry.block.ModBlocks;
import com.nano.devilry.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
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
                .pattern("  C")
                .pattern(" C ")
                .pattern("C  ")
                .unlockedBy("has_item", has(Blocks.CALCITE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(Items.BLAZE_POWDER, 3)
                .requires(Items.BLAZE_ROD)
                .requires(ModItems.PESTLE.get())
                .unlockedBy("has_item", has(Items.BLAZE_ROD))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(Items.BONE_MEAL, 4)
                .requires(Items.BONE)
                .requires(ModItems.PESTLE.get())
                .unlockedBy("has_item", has(Items.BONE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(Items.SUGAR, 3)
                .requires(Items.SUGAR_CANE)
                .requires(ModItems.PESTLE.get())
                .unlockedBy("has_item", has(Items.SUGAR_CANE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(Items.HONEY_BOTTLE)
                .requires(Items.HONEYCOMB)
                .requires(Items.GLASS_BOTTLE)
                .requires(ModItems.PESTLE.get())
                .unlockedBy("has_item", has(Items.HONEYCOMB))
                .save(consumer);

        //TIN
        ShapelessRecipeBuilder.shapeless(ModBlocks.TIN_BLOCK.get())
                .requires(ModItems.TIN_INGOT.get(), 9)
                .unlockedBy("has_item", has(ModItems.TIN_INGOT.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.TIN_INGOT.get(), 9)
                .requires (ModBlocks.TIN_BLOCK.get())
                .unlockedBy("has_item", has(ModBlocks.TIN_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.TIN_INGOT.get())
                .requires (ModItems.TIN_NUGGET.get(), 9)
                .unlockedBy("has_item", has(ModItems.TIN_INGOT.get()))
                .save(consumer, "tin_ingot_from_nugget");

        ShapelessRecipeBuilder.shapeless(ModItems.TIN_NUGGET.get(), 9)
                .requires (ModItems.TIN_INGOT.get())
                .unlockedBy("has_item", has(ModItems.TIN_INGOT.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.RAW_TIN_BLOCK.get())
                .requires(ModItems.RAW_TIN.get(), 9)
                .unlockedBy("has_item", has(ModItems.RAW_TIN.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.RAW_TIN.get(), 9)
                .requires (ModBlocks.RAW_TIN_BLOCK.get())
                .unlockedBy("has_item", has(ModBlocks.RAW_TIN_BLOCK.get()))
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
                .unlockedBy("has_item", has(ModItems.BRONZE_INGOT.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.BRONZE_INGOT.get(), 9)
                .requires (ModBlocks.BRONZE_BLOCK.get())
                .unlockedBy("has_item", has(ModBlocks.BRONZE_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(ModItems.BRONZE_INGOT.get())
                .requires (ModItems.BRONZE_NUGGET.get(), 9)
                .unlockedBy("has_item", has(ModItems.BRONZE_INGOT.get()))
                .save(consumer, "bronze_ingot_from_nugget");

        ShapelessRecipeBuilder.shapeless(ModItems.BRONZE_NUGGET.get(), 9)
                .requires (ModItems.BRONZE_INGOT.get())
                .unlockedBy("has_item", has(ModItems.BRONZE_INGOT.get()))
                .save(consumer);

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.BRONZE_BLEND.get()), ModItems.BRONZE_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_item", has(ModItems.BRONZE_BLEND.get()))
                .save(consumer, modId("bronze_ingot_from_blasting"));

        ShapedRecipeBuilder.shaped(ModBlocks.BRONZE_BARS.get(), 16)
                .define('B', ModItems.BRONZE_INGOT.get())
                .pattern("BBB")
                .pattern("BBB")
                .unlockedBy("has_item", has(ModItems.BRONZE_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.BRONZE_LANTERN.get())
                .define('B', ModItems.BRONZE_NUGGET.get())
                .define('T', Items.TORCH)
                .pattern("BBB")
                .pattern("BTB")
                .pattern("BBB")
                .unlockedBy("has_item", has(ModItems.BRONZE_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.BRONZE_CHAIN.get())
                .define('B', ModItems.BRONZE_INGOT.get())
                .define('b', ModItems.BRONZE_NUGGET.get())
                .pattern("b")
                .pattern("B")
                .pattern("b")
                .unlockedBy("has_item", has(ModItems.BRONZE_INGOT.get()))
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