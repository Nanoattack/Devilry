package com.nano.devilry.block;

import com.nano.devilry.ModMain;
import com.nano.devilry.block.custom.MortarBlock;
import com.nano.devilry.item.ModItemGroups;
import com.nano.devilry.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMain.MOD_ID);
    //GENERAL

    public static final RegistryObject<Block> MORTAR = BLOCKS.register("mortar",
            ()-> new MortarBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops().noOcclusion()));


    //TIN
    public static final RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block",
            ()-> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> TIN_ORE = registerBlock("tin_ore",
            ()-> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore",
            ()-> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> RAW_TIN_BLOCK = registerBlock("raw_tin_block",
            ()-> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(5f).requiresCorrectToolForDrops()));

    //BRONZE
    public static final RegistryObject<Block> BRONZE_BLOCK = registerBlock("bronze_block",
            ()-> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.COPPER)));

    public static final RegistryObject<Block> BRONZE_BARS = BLOCKS.register("bronze_bars",
            ()-> new IronBarsBlock(BlockBehaviour.Properties.of(Material.METAL).strength(6f).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.COPPER)));

    public static final RegistryObject<Block> BRONZE_CHAIN = BLOCKS.register("bronze_chain",
            ()-> new ChainBlock(BlockBehaviour.Properties.of(Material.METAL).strength(6f).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.CHAIN)));

    public static final RegistryObject<Block> BRONZE_LANTERN = BLOCKS.register("bronze_lantern",
            ()-> new LanternBlock(BlockBehaviour.Properties.of(Material.METAL).strength(6f).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.LANTERN)));


    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static<T extends Block> void registerBlockItem(String name, RegistryObject<T> block)
    {
        ModItems.ITEMS.register(name, ()-> new BlockItem(block.get()
                                    , new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}