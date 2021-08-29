package com.nano.devilry.data;

import com.nano.devilry.setup.ModBlocks;
import com.nano.devilry.setup.ModItems;
import com.nano.devilry.setup.Registration;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraftforge.fml.RegistryObject;

public class ModBlockLootTables extends BlockLootTables
{
    @Override
    protected void addTables() {
        dropSelf(ModBlocks.TIN_BLOCK.get());
        dropSelf(ModBlocks.TIN_ORE.get());
        dropSelf(ModBlocks.COPPER_BLOCK.get());
        dropSelf(ModBlocks.COPPER_ORE.get());
        dropSelf(ModBlocks.BRONZE_BLOCK.get());
        dropSelf(ModBlocks.ALCHEMY_STATION.get());

        ILootCondition.IBuilder ilootcondition$ibuilder1 = BlockStateProperty.hasBlockStateProperties(ModBlocks.DEVILS_SNARE_CROP.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropsBlock.AGE, 7));
        add(ModBlocks.DEVILS_SNARE_CROP.get(), createCropDrops(ModBlocks.DEVILS_SNARE_CROP.get(), Items.GHAST_TEAR, ModItems.DEVILS_SNARE_SEED.get(), ilootcondition$ibuilder1));
    }
        @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
