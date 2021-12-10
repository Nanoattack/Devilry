package com.nano.devilry.item;

import com.nano.devilry.block.ModBlocks;
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
                return new ItemStack(ModItems.TIN_INGOT.get());
            }
        };

    public static final CreativeModeTab MOD_BLOCK_GROUP = new CreativeModeTab("devilryblocks")
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModBlocks.BRONZE_BARS.get());
        }
    };
}
