package io.github.nano.devilry.block.custom;

import io.github.nano.devilry.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class FesteringSaltPetreBlock extends Block {
    public static final int GROWTH_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();

    public FesteringSaltPetreBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }


    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(GROWTH_CHANCE) == 0) {
            Direction direction = DIRECTIONS[pRandom.nextInt(DIRECTIONS.length)];
            BlockPos blockpos = pPos.relative(direction);
            BlockState blockstate = pLevel.getBlockState(blockpos);
            Block block = null;
            if (canClusterGrowAtState(blockstate)) {
                block = ModBlocks.SMALL_SALPETRE_BUD.get()
                ;
            } else if (blockstate.is(Blocks.SMALL_AMETHYST_BUD) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = Blocks.MEDIUM_AMETHYST_BUD;
            } else if (blockstate.is(Blocks.MEDIUM_AMETHYST_BUD) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = Blocks.LARGE_AMETHYST_BUD;
            } else if (blockstate.is(Blocks.LARGE_AMETHYST_BUD) && blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
                block = Blocks.AMETHYST_CLUSTER;
            }

            if (block != null) {
                BlockState blockState = block.defaultBlockState().setValue(AmethystClusterBlock.FACING, direction).setValue(AmethystClusterBlock.WATERLOGGED, blockstate.getFluidState().getType() == Fluids.WATER);
                pLevel.setBlockAndUpdate(blockpos, blockState);
            }

        }
    }

    public static boolean canClusterGrowAtState(BlockState pState) {
        return pState.isAir() || pState.is(Blocks.WATER) && pState.getFluidState().getAmount() == 8;
    }
}
