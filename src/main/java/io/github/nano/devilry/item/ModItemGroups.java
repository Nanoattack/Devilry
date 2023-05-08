package io.github.nano.devilry.devilry.item;

import io.github.nano.devilry.devilry.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups
 //Can't have capitals in json denotation else data generators stop functioning
{
        public static final CreativeModeTab MOD_MATERIAL_GROUP = new CreativeModeTab("devilrymaterials")
        {
            @Override
            public ItemStack makeIcon()
            {
                return new ItemStack(ModItems.ALCHEMICAL_ESSENCE.get());
            }
        };

    public static final CreativeModeTab MOD_BLOCK_GROUP = new CreativeModeTab("devilryblocks")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModBlocks.MORTAR.get());
        }
    };

    public static final CreativeModeTab MOD_MISC_GROUP = new CreativeModeTab("devilrymisc")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModItems.ENCHANTED_FOREST_MUSIC_DISC.get());
        }
    };
}
