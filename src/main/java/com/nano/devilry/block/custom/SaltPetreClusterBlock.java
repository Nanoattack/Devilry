package com.nano.devilry.block.custom;

import com.nano.devilry.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class SaltPetreClusterBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    protected final VoxelShape northAabb;
    protected final VoxelShape southAabb;
    protected final VoxelShape eastAabb;
    protected final VoxelShape westAabb;
    protected final VoxelShape upAabb;
    protected final VoxelShape downAabb;

    public SaltPetreClusterBlock(int int1, int int2, BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.UP));
        this.upAabb = Block.box((double) int2, 0.0D, (double) int2, (double) (16 - int2), (double) int1, (double) (16 - int2));
        this.downAabb = Block.box((double) int2, (double) (16 - int1), (double) int2, (double) (16 - int2), 16.0D, (double) (16 - int2));
        this.northAabb = Block.box((double) int2, (double) int2, (double) (16 - int1), (double) (16 - int2), (double) (16 - int2), 16.0D);
        this.southAabb = Block.box((double) int2, (double) int2, 0.0D, (double) (16 - int2), (double) (16 - int2), (double) int1);
        this.eastAabb = Block.box(0.0D, (double) int2, (double) int2, (double) int1, (double) (16 - int2), (double) (16 - int2));
        this.westAabb = Block.box((double) (16 - int1), (double) int2, (double) int2, 16.0D, (double) (16 - int2), (double) (16 - int2));
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos blockPos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        switch (direction) {
            case NORTH:
                return this.northAabb;
            case SOUTH:
                return this.southAabb;
            case EAST:
                return this.eastAabb;
            case WEST:
                return this.westAabb;
            case DOWN:
                return this.downAabb;
            case UP:
            default:
                return this.upAabb;
        }
    }

    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = blockPos.relative(direction.getOpposite());
        return levelReader.getBlockState(blockpos).isFaceSturdy(levelReader, blockpos, direction);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState blockState, LevelAccessor accessor, BlockPos pos, BlockPos blockPos) {
        if (state.getValue(WATERLOGGED)) {
            accessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
        }

        return direction == state.getValue(FACING).getOpposite() && !state.canSurvive(accessor, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, blockState, accessor, pos, blockPos);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        LevelAccessor levelaccessor = placeContext.getLevel();
        BlockPos blockpos = placeContext.getClickedPos();
        return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER)).setValue(FACING, placeContext.getClickedFace());
    }
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(WATERLOGGED, FACING);
    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
}
