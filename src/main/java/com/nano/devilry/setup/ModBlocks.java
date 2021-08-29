package com.nano.devilry.setup;

import com.nano.devilry.ModMain;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final RegistryObject<Block> TIN_ORE = register("tin_ore", ()->
            new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3f,10f).harvestLevel(1).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> TIN_BLOCK = register("tin_block", ()->
            new Block(AbstractBlock.Properties.of(Material.METAL)
                    .strength(3f,10f).harvestLevel(1).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> COPPER_ORE = register("copper_ore", ()->
            new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3f,10f).harvestLevel(1).harvestTool(ToolType.PICKAXE).sound(SoundType.STONE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> COPPER_BLOCK = register("copper_block", ()->
            new Block(AbstractBlock.Properties.of(Material.METAL)
                    .strength(3f,10f).harvestLevel(1).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> BRONZE_BLOCK = register("bronze_block", ()->
            new Block(AbstractBlock.Properties.of(Material.METAL)
                    .strength(3f,10f).harvestLevel(2).harvestTool(ToolType.PICKAXE).sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ALCHEMY_STATION = register("alchemy_station", ()->
            new AlchemyStation(AbstractBlock.Properties.of(Material.METAL)
                    .strength(4f).harvestTool(ToolType.PICKAXE).sound(SoundType.GLASS)));

    public static final RegistryObject<Block> DEVILS_SNARE_CROP =
            Registration.BLOCKS.register("devils_snare_crop",
                    ()-> new DevilsSnareCrop(AbstractBlock.Properties.copy(Blocks.WHEAT)));

    public static void register() { }

    private static <T extends Block>RegistryObject<T> register(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = Registration.BLOCKS.register(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(toReturn.get(),
                new Item.Properties().tab(ModMain.DEVILRY_TAB)));
        return toReturn;
    }
}
