package com.nano.devilry.data.datagens;

import com.nano.devilry.block.ModBlocks;
import com.nano.devilry.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLoot
{
    @Override
    protected void addTables()
    {
        //GENERAL

        dropSelf(ModBlocks.MORTAR.get());
        dropSelf(ModBlocks.LIMESTONE.get());
        this.add(ModBlocks.FESTERING_LIMESTONE.get(), noDrop());
        dropSelf(ModBlocks.POLISHED_LIMESTONE.get());
        dropSelf(ModBlocks.LIMESTONE_STAIRS.get());
        dropSelf(ModBlocks.LIMESTONE_WALL.get());
        dropSelf(ModBlocks.POLISHED_LIMESTONE_STAIRS.get());
        dropSelf(ModBlocks.LIMESTONE_SLAB.get());
        dropSelf(ModBlocks.POLISHED_LIMESTONE_SLAB.get());
        dropSelf(ModBlocks.CALCITE_SLAB.get());
        dropSelf(ModBlocks.CALCITE_STAIRS.get());
        dropSelf(ModBlocks.TUFF_SLAB.get());
        dropSelf(ModBlocks.TUFF_STAIRS.get());
        dropSelf(ModBlocks.DRIPSTONE_SLAB.get());
        dropSelf(ModBlocks.DRIPSTONE_STAIRS.get());

        //SALTPETRE


        this.add(ModBlocks.SALTPETRE_CLUSTER.get(), (p_176063_) -> {
            return createSilkTouchDispatchTable(p_176063_, LootItem.lootTableItem(ModItems.SALTPETRE.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(applyExplosionDecay(p_176063_, LootItem.lootTableItem(ModItems.SALTPETRE.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
        });
        this.dropWhenSilkTouch(ModBlocks.SMALL_SALPETRE_BUD.get());
        this.dropWhenSilkTouch(ModBlocks.MEDIUM_SALTPETRE_BUD.get());
        this.dropWhenSilkTouch(ModBlocks.LARGE_SALTPETRE_BUD.get());

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
    }
    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
