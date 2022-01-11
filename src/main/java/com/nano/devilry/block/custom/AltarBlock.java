package com.nano.devilry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class AltarBlock extends HorizontalDirectionalBlock {

    public AltarBlock(Properties p_54120_) {
        super(p_54120_);
    }

    private static final Property<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final VoxelShape SHAPE_N = Stream.of(
            Block.box(28, 16, 11, 30, 22, 13),
            Block.box(2, 16, 5.5, 10, 17, 13.5),
            Block.box(0, 0, 3, 32, 16, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_W = Stream.of(
            Block.box(11., 16, -14, 13, 22, -12),
            Block.box(5.5, 16, 6, 13.5, 17, 14),
            Block.box(3, 0, -16, 15, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_S = Stream.of(
            Block.box(-14, 16, 3, -12, 22, 5),
            Block.box(6, 16, 2.5, 14, 17, 10.5),
            Block.box(-16, 0, 1, 16, 16, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_E = Stream.of(
            Block.box(3, 16, 28, 5, 22, 30),
            Block.box(2.5, 16, 2, 10.5, 17, 10),
            Block.box(1, 0, 0, 13, 16, 32)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(FACING)) {
            case NORTH:
                return SHAPE_N;
            case EAST:
                return SHAPE_E;
            case WEST:
                return SHAPE_W;
            case SOUTH:
                return SHAPE_S;
            default:
                return SHAPE_N;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
            Direction direction = pContext.getHorizontalDirection().getOpposite();
            Direction check = pContext.getHorizontalDirection().getClockWise().getOpposite();
            BlockPos blockpos = pContext.getClickedPos();
            BlockPos blockpos1 = blockpos.relative(check);
            Level level = pContext.getLevel();
            return level.getBlockState(blockpos1).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(blockpos1) ? this.defaultBlockState().setValue(FACING, direction) : null;
    }
}



