package com.nano.devilry.data.datagens.client;

import com.nano.devilry.ModMain;
import com.nano.devilry.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider
{
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper)
    {
        super(gen, ModMain.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        simpleBlock(ModBlocks.TIN_BLOCK.get());
        simpleBlock(ModBlocks.TIN_ORE.get());
        simpleBlock(ModBlocks.RAW_TIN_BLOCK.get());
        simpleBlock(ModBlocks.DEEPSLATE_TIN_ORE.get());
        simpleBlock(ModBlocks.BRONZE_BLOCK.get());
        simpleBlock(ModBlocks.LIMESTONE.get());
        simpleBlock(ModBlocks.POLISHED_LIMESTONE.get());
        ResourceLocation limestone = modLoc("block/limestone");
        ResourceLocation polished_limestone = modLoc("block/polished_limestone");
        stairsBlock(ModBlocks.LIMESTONE_STAIRS.get(), limestone);
        stairsBlock(ModBlocks.POLISHED_LIMESTONE_STAIRS.get(), polished_limestone);
        slabBlock(ModBlocks.LIMESTONE_SLAB.get(), limestone, limestone);
        slabBlock(ModBlocks.POLISHED_LIMESTONE_SLAB.get(), polished_limestone, polished_limestone);
        ResourceLocation calcite = mcLoc("block/calcite");
        ResourceLocation tuff = mcLoc("block/tuff");
        ResourceLocation dripstone = mcLoc("block/dripstone_block");
        stairsBlock(ModBlocks.CALCITE_STAIRS.get(), calcite);
        stairsBlock(ModBlocks.TUFF_STAIRS.get(), tuff);
        stairsBlock(ModBlocks.DRIPSTONE_STAIRS.get(), dripstone);
        slabBlock(ModBlocks.CALCITE_SLAB.get(), calcite, calcite);
        slabBlock(ModBlocks.TUFF_SLAB.get(), tuff, tuff);
        slabBlock(ModBlocks.DRIPSTONE_SLAB.get(), dripstone, dripstone);

    }
}



