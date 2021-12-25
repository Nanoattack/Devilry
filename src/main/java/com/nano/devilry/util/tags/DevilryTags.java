package com.nano.devilry.util.tags;

import com.nano.devilry.ModMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class DevilryTags
{
    public static class Blocks{

        private static Tags.IOptionalNamedTag<Block> createTag(String name) {
            return BlockTags.createOptional(new ResourceLocation(ModMain.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Block> createForgeTag(String name) {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }

    public static class Items{

        //GENERAL
        public static final Tags.IOptionalNamedTag<Item> PESTLE_IN_MORTAR =
                createTag("pestle_in_mortar");

        public static final Tags.IOptionalNamedTag<Item> KNIFE_IN_WITTLING =
                createTag("knife_in_wittling");

        public static final Tags.IOptionalNamedTag<Item> MORTAR_SULPHUR =
                createTag("mortar_sulphur");

        public static final Tags.IOptionalNamedTag<Item> POTASSIUM_NITRATE =
                createTag("potassium_nitrate");

        //INGOTS
        public static final Tags.IOptionalNamedTag<Item> INGOTS_TIN =
                createForgeTag("ingots/tin");

        public static final Tags.IOptionalNamedTag<Item> INGOTS_BRONZE =
                createForgeTag("ingots/bronze");

        //DUSTS
        public static final Tags.IOptionalNamedTag<Item> DUST_BRONZE =
                createForgeTag("dusts/bronze");

        //NUGGETS
        public static final Tags.IOptionalNamedTag<Item> NUGGET_TIN =
                createForgeTag("nuggets/tin");

        public static final Tags.IOptionalNamedTag<Item> NUGGET_BRONZE =
                createForgeTag("nuggets/bronze");

        public static final Tags.IOptionalNamedTag<Item> NUGGET_COPPER =
                createForgeTag("nuggets/copper");

        private static Tags.IOptionalNamedTag<Item> createTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(ModMain.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Item> createForgeTag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
}