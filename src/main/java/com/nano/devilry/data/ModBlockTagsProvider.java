package com.nano.devilry.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import com.nano.devilry.ModMain;
import com.nano.devilry.setup.ModBlocks;
import com.nano.devilry.setup.ModTags;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, ModMain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Blocks.ORES_TIN).add(ModBlocks.TIN_ORE.get());
        tag(Tags.Blocks.ORES).addTag(ModTags.Blocks.ORES_TIN);
        tag(ModTags.Blocks.STORAGE_BLOCKS_TIN).add(ModBlocks.TIN_BLOCK.get());
        tag(Tags.Blocks.STORAGE_BLOCKS).addTag(ModTags.Blocks.STORAGE_BLOCKS_TIN);
    }
}