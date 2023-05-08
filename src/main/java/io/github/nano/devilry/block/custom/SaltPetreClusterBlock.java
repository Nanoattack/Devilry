package io.github.nano.devilry.block.custom;

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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
//todo
@SuppressWarnings("deprecation")
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
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.FALSE).setValue(FACING, Direction.UP));
        this.upAabb = Block.box(int2, 0.0D, int2, 16 - int2, int1, 16 - int2);
        this.downAabb = Block.box(int2, 16 - int1, int2, 16 - int2, 16.0D, 16 - int2);
        this.northAabb = Block.box(int2, int2, 16 - int1, 16 - int2, 16 - int2, 16.0D);
        this.southAabb = Block.box(int2, int2, 0.0D, 16 - int2, 16 - int2, int1);
        this.eastAabb = Block.box(0.0D, int2, int2, int1, 16 - int2, 16 - int2);
        this.westAabb = Block.box(16 - int1, int2, int2, 16.0D, 16 - int2, 16 - int2);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            case NORTH -> this.northAabb;
            case SOUTH -> this.southAabb;
            case EAST -> this.eastAabb;
            case WEST -> this.westAabb;
            case DOWN -> this.downAabb;
            case UP -> this.upAabb;
        };
    }

    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = blockPos.relative(direction.getOpposite());
        return levelReader.getBlockState(blockpos).isFaceSturdy(levelReader, blockpos, direction);
    }

    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState blockState, @NotNull LevelAccessor accessor, @NotNull BlockPos pos, @NotNull BlockPos blockPos) {
        if (state.getValue(WATERLOGGED)) {
            accessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(accessor));
        }

        return direction == state.getValue(FACING).getOpposite() && !state.canSurvive(accessor, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, blockState, accessor, pos, blockPos);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        LevelAccessor levelaccessor = placeContext.getLevel();
        BlockPos blockpos = placeContext.getClickedPos();
        return this.defaultBlockState().setValue(WATERLOGGED, levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER).setValue(FACING, placeContext.getClickedFace());
    }
    public @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    public @NotNull FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(WATERLOGGED, FACING);
    }

    public @NotNull PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.DESTROY;
    }
}
