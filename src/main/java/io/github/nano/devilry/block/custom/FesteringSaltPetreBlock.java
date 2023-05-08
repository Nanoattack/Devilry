package io.github.nano.devilry.block.custom;

import io.github.nano.devilry.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
//todo

@SuppressWarnings("deprecation")
public class FesteringSaltPetreBlock extends Block {
    public static final int GROWTH_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();

    public FesteringSaltPetreBlock(Properties properties) {super(properties);}

    public @NotNull PushReaction getPistonPushReaction(@NotNull BlockState p_152733_) {
        return PushReaction.DESTROY;
    }

    //todo
    @SuppressWarnings("unused")
    public void randomTick(BlockState state, ServerLevel level, BlockPos blockPos, Random random) {
        if (random.nextInt(GROWTH_CHANCE) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockpos = blockPos.relative(direction);
            BlockState blockstate = level.getBlockState(blockpos);
            Block block = null;
            if (canClusterGrowAtState(blockstate)) {
                block = ModBlocks.SMALL_SALPETRE_BUD.get();
            } else if (blockstate.is(ModBlocks.SMALL_SALPETRE_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = ModBlocks.MEDIUM_SALTPETRE_BUD.get();
            } else if (blockstate.is(ModBlocks.MEDIUM_SALTPETRE_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = ModBlocks.LARGE_SALTPETRE_BUD.get();
            } else if (blockstate.is(ModBlocks.LARGE_SALTPETRE_BUD.get()) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = ModBlocks.SALTPETRE_CLUSTER.get();
            }

            if (block != null) {
                BlockState blockState = block.defaultBlockState().setValue(AmethystClusterBlock.FACING, direction).setValue(AmethystClusterBlock.WATERLOGGED, blockstate.getFluidState().getType() == Fluids.WATER);
                level.setBlockAndUpdate(blockpos, blockState);
            }

        }
    }

    public static boolean canClusterGrowAtState(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) && state.getFluidState().getAmount() == 8;
    }
}
