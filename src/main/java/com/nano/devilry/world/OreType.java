package com.nano.devilry.world;

import com.nano.devilry.setup.ModBlocks;
import net.minecraft.block.Block;

public enum OreType
{
    COPPER(ModBlocks.COPPER_ORE.get(), 8, 25, 50, 10),
    TIN(ModBlocks.TIN_ORE.get(), 8, 25, 50, 10);

    private final Block block;
    private final int maxVeinSize;
    private final int minHeight;
    private final int maxHeight;
    private final int count;

    OreType(Block block, int maxVeinSize, int minHeight, int maxHeight, int count)
    {
        this.block = block;
        this.maxVeinSize = maxVeinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.count = count;
    }


    public Block getBlock()
    {
        return block;
    }

    public int getMaxVeinSize()
    {
        return maxVeinSize;
    }

    public int getMinHeight()
    {
        return minHeight;
    }

    public int getMaxHeight()
    {
        return maxHeight;
    }

    public int getCount() {
        return count;
    }

    public static OreType get(Block block)
    {
        for(OreType ore : values())
        {
            if(block == ore.block)
            {
                return ore;
            }
        }
        return null;
    }
}
