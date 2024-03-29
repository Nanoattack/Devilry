package io.github.nano.devilry.util.tags;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.data.recipes.CarveRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
//todo
@SuppressWarnings("unused")

public class DevilryTags
{
    public static class Blocks{

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(new ResourceLocation(ModMain.MOD_ID, name));
        }

        private static TagKey<Block> createForgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }

        public static final TagKey<Block> CARVING_MATERIAL_WOOD =
                createTag("carving_material_wood");
        public static final TagKey<Block> CARVING_MATERIAL_SOFT_STONE =
                createTag("carving_material_soft_stone");
        public static final TagKey<Block> CARVING_MATERIAL_STONE =
                createTag("carving_material_stone");
        public static final TagKey<Block> CARVING_MATERIAL_OBSIDIAN =
                createTag("carving_material_obsidian");
    }

    public static class Items{

        //GENERAL
        public static final TagKey<Item> PESTLE_IN_MORTAR =
                createTag("pestle_in_mortar");

        public static final TagKey<Item> MORTAR_SULPHUR =
                createTag("mortar_sulphur");

        public static final TagKey<Item> POTASSIUM_NITRATE =
                createTag("potassium_nitrate");

        //INGOTS
        public static final TagKey<Item> INGOTS_TIN =
                createForgeTag("ingots/tin");

        public static final TagKey<Item> INGOTS_BRONZE =
                createForgeTag("ingots/bronze");

        //DUSTS
        public static final TagKey<Item> DUST_BRONZE =
                createForgeTag("dusts/bronze");

        //NUGGETS
        public static final TagKey<Item> NUGGET_TIN =
                createForgeTag("nuggets/tin");

        public static final TagKey<Item> NUGGET_BRONZE =
                createForgeTag("nuggets/bronze");

        public static final TagKey<Item> NUGGET_COPPER =
                createForgeTag("nuggets/copper");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(new ResourceLocation(ModMain.MOD_ID, name));
        }

        private static TagKey<Item> createForgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}