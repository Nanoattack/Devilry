package io.github.nano.devilry.block;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.block.custom.*;
import io.github.nano.devilry.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.Blocks.AMETHYST_CLUSTER;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMain.MOD_ID);
    //GENERAL

    public static final RegistryObject<Block> MORTAR = BLOCKS.register("mortar",
            ()-> new MortarBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).requiresCorrectToolForDrops().noOcclusion()));

    //BUDDING BLOCKS
    public static final RegistryObject<Block> FESTERING_LIMESTONE = registerBlock("festering_limestone",
            ()-> new FesteringSaltPetreBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).randomTicks()
                    .strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));

    public static final RegistryObject<Block> SALTPETRE_CLUSTER = registerBlock("saltpetre_cluster", ()->
            new SaltPetreClusterBlock(7, 3, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE)
                    .noOcclusion().randomTicks().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F).requiresCorrectToolForDrops()
                    .lightLevel((p_152632_) -> 5)));

    public static final RegistryObject<Block> LARGE_SALTPETRE_BUD = registerBlock("large_saltpetre_bud",
            ()-> new SaltPetreClusterBlock(5, 3, BlockBehaviour.Properties.copy(AMETHYST_CLUSTER).sound(SoundType.LARGE_AMETHYST_BUD).requiresCorrectToolForDrops()
                    .lightLevel((p_152629_) -> 4)));

    public static final RegistryObject<Block> MEDIUM_SALTPETRE_BUD = registerBlock("medium_saltpetre_bud",
            ()-> new SaltPetreClusterBlock(4, 3, BlockBehaviour.Properties.copy(AMETHYST_CLUSTER).sound(SoundType.MEDIUM_AMETHYST_BUD).requiresCorrectToolForDrops()
                    .lightLevel((p_152617_) -> 2)));

    public static final RegistryObject<Block> SMALL_SALPETRE_BUD = registerBlock("small_saltpetre_bud",
            ()-> new SaltPetreClusterBlock(3, 4, BlockBehaviour.Properties.copy(AMETHYST_CLUSTER).sound(SoundType.SMALL_AMETHYST_BUD).requiresCorrectToolForDrops()
                    .lightLevel((p_187409_) -> 1)));
    //STONES

    public static final RegistryObject<Block> LIMESTONE = registerBlock("limestone",
            ()-> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));

    public static final RegistryObject<Block> POLISHED_LIMESTONE = registerBlock("polished_limestone",
            ()-> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));

    public static final RegistryObject<WallBlock> LIMESTONE_WALL = registerBlock("limestone_wall",
            ()-> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));

    public static final RegistryObject<StairBlock> LIMESTONE_STAIRS = registerBlock("limestone_stairs",
            () -> new StairBlock(()-> LIMESTONE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.GRANITE_STAIRS).sound(SoundType.TUFF).requiresCorrectToolForDrops()));

    public static final RegistryObject<StairBlock> POLISHED_LIMESTONE_STAIRS = registerBlock("polished_limestone_stairs",
            () -> new StairBlock(()-> POLISHED_LIMESTONE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.GRANITE_STAIRS).sound(SoundType.TUFF).requiresCorrectToolForDrops()));

    public static final RegistryObject<SlabBlock> LIMESTONE_SLAB = registerBlock("limestone_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE_SLAB).strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.TUFF).requiresCorrectToolForDrops()));

    public static final RegistryObject<SlabBlock> POLISHED_LIMESTONE_SLAB = registerBlock("polished_limestone_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE_SLAB).strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.TUFF).requiresCorrectToolForDrops()));

    public static final RegistryObject<StairBlock> CALCITE_STAIRS = registerBlock("calcite_stairs",
            () -> new StairBlock(Blocks.CALCITE::defaultBlockState,
                    BlockBehaviour.Properties.copy(Blocks.GRANITE_STAIRS).sound(SoundType.CALCITE).requiresCorrectToolForDrops()));

    public static final RegistryObject<SlabBlock> CALCITE_SLAB = registerBlock("calcite_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE_SLAB).strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.CALCITE)));

    public static final RegistryObject<WallBlock> CALCITE_WALL = registerBlock("calcite_wall",
            ()-> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.CALCITE)));

    public static final RegistryObject<StairBlock> TUFF_STAIRS = registerBlock("tuff_stairs",
            () -> new StairBlock(Blocks.TUFF::defaultBlockState,
                    BlockBehaviour.Properties.copy(Blocks.GRANITE_STAIRS).sound(SoundType.TUFF).requiresCorrectToolForDrops()));

    public static final RegistryObject<SlabBlock> TUFF_SLAB = registerBlock("tuff_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE_SLAB).strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.TUFF).requiresCorrectToolForDrops()));

    public static final RegistryObject<WallBlock> TUFF_WALL = registerBlock("tuff_wall",
            ()-> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.TUFF)));

    public static final RegistryObject<StairBlock> DRIPSTONE_STAIRS = registerBlock("dripstone_stairs",
            () -> new StairBlock(Blocks.DRIPSTONE_BLOCK::defaultBlockState,
                    BlockBehaviour.Properties.copy(Blocks.GRANITE_STAIRS).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    public static final RegistryObject<SlabBlock> DRIPSTONE_SLAB = registerBlock("dripstone_slab",
            ()-> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GRANITE_SLAB).strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops()));

    public static final RegistryObject<WallBlock> DRIPSTONE_WALL = registerBlock("dripstone_wall",
            ()-> new WallBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.DRIPSTONE_BLOCK)));

    //TIN
    public static final RegistryObject<Block> TIN_BLOCK = registerBlock("tin_block",
            ()-> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> TIN_ORE = registerBlock("tin_ore",
            ()-> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore",
            ()-> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(4.5f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> RAW_TIN_BLOCK = registerBlock("raw_tin_block",
            ()-> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(5f).requiresCorrectToolForDrops()));

    //BRONZE
    public static final RegistryObject<Block> BRONZE_BLOCK = registerBlock("bronze_block",
            ()-> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(6f).requiresCorrectToolForDrops().sound(SoundType.COPPER)));

    public static final RegistryObject<Block> BRONZE_BARS = BLOCKS.register("bronze_bars",
            ()-> new IronBarsBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(6f).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.COPPER)));

    public static final RegistryObject<Block> BRONZE_CHAIN = BLOCKS.register("bronze_chain",
            ()-> new ChainBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(6f).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.CHAIN)));

    public static final RegistryObject<Block> BRONZE_LANTERN = BLOCKS.register("bronze_lantern",
            ()-> new LanternBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(6f).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.LANTERN)));

    public static final RegistryObject<Block> BONE_ASH = BLOCKS.register("bone_ash",
            () -> new BoneAshBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).noCollission().noOcclusion().instabreak().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> SULPHUR_BLOCK = registerBlock("sulphur_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.YELLOW)));

    public static final RegistryObject<LimeStoneAltar> LIMESTONE_ALTAR = registerBlock("limestone_altar",
            () -> new LimeStoneAltar(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_WHITE)));

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static<T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.register(name, ()-> new BlockItem(block.get(), new Item.Properties()), ModItems.Tab.DEVILRY_BLOCKS);
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}