package io.github.nano.devilry.data.datagens.client;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
//todo

@SuppressWarnings("unused")
public class ModBlockStateProvider extends BlockStateProvider
{


    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ModMain.MOD_ID, exFileHelper);
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    protected void registerStatesAndModels()
    {
        simpleBlock(ModBlocks.TIN_BLOCK.get());
        simpleBlock(ModBlocks.TIN_ORE.get());
        simpleBlock(ModBlocks.RAW_TIN_BLOCK.get());
        simpleBlock(ModBlocks.DEEPSLATE_TIN_ORE.get());
        simpleBlock(ModBlocks.BRONZE_BLOCK.get());
        simpleBlock(ModBlocks.LIMESTONE.get());
        simpleBlock(ModBlocks.FESTERING_LIMESTONE.get());
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
        wallBlock(ModBlocks.LIMESTONE_WALL.get(), "limestone", limestone);
        wallBlock(ModBlocks.CALCITE_WALL.get(), "calcite", calcite);
        wallBlock(ModBlocks.TUFF_WALL.get(), "tuff", tuff);
        wallBlock(ModBlocks.DRIPSTONE_WALL.get(), "dripstone", dripstone);
    }
}



