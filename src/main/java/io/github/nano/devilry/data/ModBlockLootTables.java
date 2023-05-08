package io.github.nano.devilry.devilry.data;

import io.github.nano.devilry.devilry.block.ModBlocks;
import io.github.nano.devilry.devilry.item.ModItems;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot
{
    @Override
    protected void addTables()
    {
        //TIN
        dropSelf(ModBlocks.TIN_BLOCK.get());

        dropSelf(ModBlocks.RAW_TIN_BLOCK.get());

        this.add(ModBlocks.TIN_ORE.get(), (p_124076_) -> {
            return createOreDrop(p_124076_, ModItems.RAW_TIN.get());
        });

        this.add(ModBlocks.DEEPSLATE_TIN_ORE.get(), (p_124076_) -> {
            return createOreDrop(p_124076_, ModItems.RAW_TIN.get());
        });

        //BRONZE
        dropSelf(ModBlocks.BRONZE_BLOCK.get());
        dropSelf(ModBlocks.BRONZE_BARS.get());
        dropSelf(ModBlocks.BRONZE_CHAIN.get());
        dropSelf(ModBlocks.BRONZE_LANTERN.get());
        dropSelf(ModBlocks.MORTAR.get());
    }
    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
