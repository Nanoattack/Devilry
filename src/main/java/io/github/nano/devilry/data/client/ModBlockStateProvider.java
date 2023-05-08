package io.github.nano.devilry.devilry.data.client;

import io.github.nano.devilry.devilry.ModMain;
import io.github.nano.devilry.devilry.block.ModBlocks;
import net.minecraft.data.DataGenerator;
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
    }
}



