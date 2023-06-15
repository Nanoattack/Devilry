package io.github.nano.devilry.data.datagens;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.block.ModBlocks;
import io.github.nano.devilry.item.ModItems;
import io.github.nano.devilry.util.tags.DevilryTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
//todo

@SuppressWarnings("SpellCheckingInspection")
public class ModRecipeProvider extends RecipeProvider {


    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        // GENERAL RECIPES
        //todo fix the tags
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.BONE_MEAL), RecipeCategory.MISC, ModItems.BONE_ASH.get(), 0.35f, 200)
                .unlockedBy("has_item", has(Items.BONE_MEAL))
                .save(consumer, modId("bone_ash_campfire_cooking"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MORTAR.get())
                .define('D', Items.POLISHED_DEEPSLATE)
                .define('S', Items.POLISHED_DEEPSLATE_SLAB)
                .pattern("D D")
                .pattern("DSD")
                .unlockedBy("has_item", has(Blocks.POLISHED_DEEPSLATE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PESTLE.get())
                .define('C', Items.CALCITE)
                .define('T', ItemTags.create(new ResourceLocation("forge:ingots/tin")))
                .pattern("  T")
                .pattern(" C ")
                .pattern("C  ")
                .unlockedBy("has_item", has(Blocks.CALCITE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.FLINT_KNIFE.get())
                .define('F', Items.FLINT)
                .define('S', Items.STICK)
                .pattern(" F ")
                .pattern("S  ")
                .unlockedBy("has_item", has(Items.FLINT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BRONZE_KNIFE.get())
                .define('B', ItemTags.create(new ResourceLocation("forge:ingots/bronze")))
                .define('S', Items.STICK)
                .pattern(" B ")
                .pattern("S  ")
                .unlockedBy("has_item", has(ModItems.BRONZE_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.SOUL_TORCH, 12)
                .define('s', ModItems.SULPHUR.get())
                .define('S', Items.STICK)
                .pattern("s  ")
                .pattern("S  ")
                .unlockedBy("has_item", has(ModItems.SULPHUR.get()))
                .save(consumer, "soul_torch_from_sulphur");

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.SOUL_CAMPFIRE)
                .define('s', ModItems.SULPHUR.get())
                .define('S', Items.STICK)
                .define('L', ItemTags.LOGS)
                .pattern(" S ")
                .pattern("SsS")
                .pattern("LLL")
                .unlockedBy("has_item", has(ModItems.SULPHUR.get()))
                .save(consumer, "soul_campfire_from_sulphur");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BLAZE_POWDER, 3)
                .requires(Items.BLAZE_ROD)
                .requires(DevilryTags.Items.PESTLE_IN_MORTAR)
                .unlockedBy("has_item", has(Items.BLAZE_ROD))
                .save(consumer, "blaze_powder_from_pestle");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ModItems.CURED_FLESH.get())
                .requires(Items.ROTTEN_FLESH)
                .requires(ModItems.SALTPETRE.get())
                .unlockedBy("has_item", has(ModItems.SALTPETRE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, Items.BONE_MEAL, 4)
                .requires(Items.BONE)
                .requires(DevilryTags.Items.PESTLE_IN_MORTAR)
                .unlockedBy("has_item", has(Items.BONE))
                .save(consumer, "bone_meal_from_pestle");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, Items.SUGAR, 3)
                .requires(Items.SUGAR_CANE)
                .requires(DevilryTags.Items.PESTLE_IN_MORTAR)
                .unlockedBy("has_item", has(Items.SUGAR_CANE))
                .save(consumer, "sugar_from_pestle");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, Items.HONEY_BOTTLE)
                .requires(Items.HONEYCOMB)
                .requires(Items.GLASS_BOTTLE)
                .requires(DevilryTags.Items.PESTLE_IN_MORTAR)
                .unlockedBy("has_item", has(Items.HONEYCOMB))
                .save(consumer, "honey_bottle_from_pestle");
        //STONE

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_LIMESTONE.get())
                .define('L', ModBlocks.LIMESTONE.get())
                .pattern("LL")
                .pattern("LL")
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIMESTONE_WALL.get(), 6)
                .define('L', ModBlocks.LIMESTONE.get())
                .pattern("LLL")
                .pattern("LLL")
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_LIMESTONE_STAIRS.get(), 4)
                .define('L', ModBlocks.POLISHED_LIMESTONE.get())
                .pattern("L  ")
                .pattern("LL ")
                .pattern("LLL")
                .unlockedBy("has_item", has(ModBlocks.POLISHED_LIMESTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_LIMESTONE_SLAB.get(), 6)
                .define('L', ModBlocks.POLISHED_LIMESTONE.get())
                .pattern("LLL")
                .unlockedBy("has_item", has(ModBlocks.POLISHED_LIMESTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIMESTONE_STAIRS.get(), 4)
                .define('L', ModBlocks.LIMESTONE.get())
                .pattern("L  ")
                .pattern("LL ")
                .pattern("LLL")
                .unlockedBy("has_item", has(ModBlocks.POLISHED_LIMESTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIMESTONE_SLAB.get(), 6)
                .define('L', ModBlocks.LIMESTONE.get())
                .pattern("LLL")
                .unlockedBy("has_item", has(ModBlocks.POLISHED_LIMESTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCITE_STAIRS.get(), 4)
                .define('C', Blocks.CALCITE)
                .pattern("C  ")
                .pattern("CC ")
                .pattern("CCC")
                .unlockedBy("has_item", has(Blocks.CALCITE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCITE_SLAB.get(), 6)
                .define('C', Blocks.CALCITE)
                .pattern("CCC")
                .unlockedBy("has_item", has(Blocks.CALCITE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCITE_WALL.get(), 6)
                .define('C', Blocks.CALCITE)
                .pattern("CCC")
                .pattern("CCC")
                .unlockedBy("has_item", has(Blocks.CALCITE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TUFF_STAIRS.get(), 4)
                .define('T', Blocks.TUFF)
                .pattern("T  ")
                .pattern("TT ")
                .pattern("TTT")
                .unlockedBy("has_item", has(Blocks.TUFF))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TUFF_SLAB.get(), 6)
                .define('T', Blocks.TUFF)
                .pattern("TTT")
                .unlockedBy("has_item", has(Blocks.TUFF))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TUFF_WALL.get(), 6)
                .define('T', Blocks.TUFF)
                .pattern("TTT")
                .pattern("TTT")
                .unlockedBy("has_item", has(Blocks.TUFF))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRIPSTONE_STAIRS.get(), 4)
                .define('D', Blocks.DRIPSTONE_BLOCK)
                .pattern("D  ")
                .pattern("DD ")
                .pattern("DDD")
                .unlockedBy("has_item", has(Blocks.DRIPSTONE_BLOCK))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRIPSTONE_SLAB.get(), 6)
                .define('D', Blocks.DRIPSTONE_BLOCK)
                .pattern("DDD")
                .unlockedBy("has_item", has(Blocks.DRIPSTONE_BLOCK))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRIPSTONE_WALL.get(), 6)
                .define('D', Blocks.DRIPSTONE_BLOCK)
                .pattern("DDD")
                .pattern("DDD")
                .unlockedBy("has_item", has(Blocks.DRIPSTONE_BLOCK))
                .save(consumer);

        //STONECUTTING

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_LIMESTONE.get())
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer, "polished_limestone_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIMESTONE_SLAB.get(), 2)
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer, "limestone_slab_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIMESTONE_STAIRS.get())
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer, "limestone_stairs_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.LIMESTONE_WALL.get())
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer, "limestone_wall_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.POLISHED_LIMESTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_LIMESTONE_STAIRS.get())
                .unlockedBy("has_item", has(ModBlocks.POLISHED_LIMESTONE.get()))
                .save(consumer, "polished_limestone_stairs_from_polished");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_LIMESTONE_STAIRS.get())
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer, "polished_limestone_stairs_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.POLISHED_LIMESTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_LIMESTONE_SLAB.get(), 2)
                .unlockedBy("has_item", has(ModBlocks.POLISHED_LIMESTONE.get()))
                .save(consumer, "polished_limestone_slab_from_polished");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_LIMESTONE_SLAB.get(), 2)
                .unlockedBy("has_item", has(ModBlocks.LIMESTONE.get()))
                .save(consumer, "polished_limestone_slab_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.CALCITE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCITE_SLAB.get(), 2)
                .unlockedBy("has_item", has(Blocks.CALCITE))
                .save(consumer, "calcite_slab_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.CALCITE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCITE_STAIRS.get())
                .unlockedBy("has_item", has(Blocks.CALCITE))
                .save(consumer, "calcite_stairs_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.CALCITE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCITE_WALL.get())
                .unlockedBy("has_item", has(Blocks.CALCITE))
                .save(consumer, "calcite_wall_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.TUFF), RecipeCategory.BUILDING_BLOCKS, ModBlocks.TUFF_SLAB.get(), 2)
                .unlockedBy("has_item", has(Blocks.TUFF))
                .save(consumer, "tuff_slab_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.TUFF), RecipeCategory.BUILDING_BLOCKS, ModBlocks.TUFF_STAIRS.get())
                .unlockedBy("has_item", has(Blocks.TUFF))
                .save(consumer, "tuff_stairs_from_stonecutting");


        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.TUFF), RecipeCategory.BUILDING_BLOCKS, ModBlocks.TUFF_WALL.get())
                .unlockedBy("has_item", has(Blocks.TUFF))
                .save(consumer, "tuff_wall_from_stonecutting");


        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.DRIPSTONE_BLOCK), RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRIPSTONE_SLAB.get(), 2)
                .unlockedBy("has_item", has(Blocks.DRIPSTONE_BLOCK))
                .save(consumer, "dripstone_slab_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.DRIPSTONE_BLOCK), RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRIPSTONE_STAIRS.get())
                .unlockedBy("has_item", has(Blocks.DRIPSTONE_BLOCK))
                .save(consumer, "dripstone_stairs_from_stonecutting");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.DRIPSTONE_BLOCK), RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRIPSTONE_WALL.get())
                .unlockedBy("has_item", has(Blocks.DRIPSTONE_BLOCK))
                .save(consumer, "dripstone_wall_from_stonecutting");

        //TIN
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TIN_BLOCK.get())
                .requires(ModItems.TIN_INGOT.get(), 9)
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_TIN))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 9)
                .requires (ModBlocks.TIN_BLOCK.get())
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_TIN))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TIN_INGOT.get())
                .requires (ModItems.TIN_NUGGET.get(), 9)
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_TIN))
                .save(consumer, "tin_ingot_from_nugget");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TIN_NUGGET.get(), 9)
                .requires (ModItems.TIN_INGOT.get())
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_TIN))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_TIN_BLOCK.get())
                .requires(ModItems.RAW_TIN.get(), 9)
                .unlockedBy("has_item", has(ModItems.RAW_TIN.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_TIN.get(), 9)
                .requires (ModBlocks.RAW_TIN_BLOCK.get())
                .unlockedBy("has_item", has(ModItems.RAW_TIN.get()))
                .save(consumer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_TIN.get()), RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_item", has(ModItems.RAW_TIN.get()))
                .save(consumer, modId("tin_ingot_smelting"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_TIN.get()), RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_item", has(ModItems.RAW_TIN.get()))
                .save(consumer, modId("tin_ingot_blasting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.TIN_ORE.get()), RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_item", has(ModBlocks.TIN_ORE.get()))
                .save(consumer, modId("tin_ingot_smelting_ore"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.TIN_ORE.get()), RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_item", has(ModBlocks.TIN_ORE.get()))
                .save(consumer, modId("tin_ingot_blasting_ore"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.DEEPSLATE_TIN_ORE.get()), RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_item", has(ModBlocks.DEEPSLATE_TIN_ORE.get()))
                .save(consumer, modId("tin_ingot_smelting_deepslate_ore"));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.DEEPSLATE_TIN_ORE.get()), RecipeCategory.MISC, ModItems.TIN_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_item", has(ModBlocks.DEEPSLATE_TIN_ORE.get()))
                .save(consumer, modId("tin_ingot_blasting_deepslate_ore"));

        //BRONZE
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRONZE_BLOCK.get())
                .requires(ModItems.BRONZE_INGOT.get(), 9)
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRONZE_INGOT.get(), 9)
                .requires (ModBlocks.BRONZE_BLOCK.get())
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRONZE_INGOT.get())
                .requires (ModItems.BRONZE_NUGGET.get(), 9)
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer, "bronze_ingot_from_nugget");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BRONZE_NUGGET.get(), 9)
                .requires (DevilryTags.Items.INGOTS_BRONZE)
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer);

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.BRONZE_BLEND.get()), RecipeCategory.MISC, ModItems.BRONZE_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_item", has(ModItems.BRONZE_BLEND.get()))
                .save(consumer, modId("bronze_ingot_from_blasting"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.BRONZE_BARS.get(), 16)
                .define('B', DevilryTags.Items.INGOTS_BRONZE)
                .pattern("BBB")
                .pattern("BBB")
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.BRONZE_LANTERN.get())
                .define('B', ModItems.BRONZE_NUGGET.get())
                .define('T', Items.TORCH)
                .pattern("BBB")
                .pattern("BTB")
                .pattern("BBB")
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.BRONZE_CHAIN.get())
                .define('B', DevilryTags.Items.INGOTS_BRONZE)
                .define('b', ModItems.BRONZE_NUGGET.get())
                .pattern("b")
                .pattern("B")
                .pattern("b")
                .unlockedBy("has_item", has(DevilryTags.Items.INGOTS_BRONZE))
                .save(consumer);

        //COPPER
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.COPPER_INGOT)
                .requires (ModItems.COPPER_NUGGET.get(), 9)
                .unlockedBy("has_item", has(Items.COPPER_INGOT))
                .save(consumer, "copper_ingot_from_nugget");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COPPER_NUGGET.get(), 9)
                .requires(Items.COPPER_INGOT)
                .unlockedBy("has_item", has(Items.COPPER_INGOT))
                .save(consumer);

    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(ModMain.MOD_ID, path);
    }
}