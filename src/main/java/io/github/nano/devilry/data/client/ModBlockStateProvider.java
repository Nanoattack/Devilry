package io.github.nano.devilry.data.client;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
//todo

@SuppressWarnings("unused")
public class ModBlockStateProvider extends BlockStateProvider
{

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ModMain.MOD_ID, exFileHelper);
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



