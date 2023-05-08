package io.github.nano.devilry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
//todo

public class AltarSideBlock extends HorizontalDirectionalBlock {

    public AltarSideBlock(Properties p_54120_) {
        super(p_54120_);
    }

    private static final Property<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final VoxelShape SHAPE_N = java.util.Optional.of(Block.box(0, 0, 3, 16, 16, 15)).get();

    public static final VoxelShape SHAPE_W = java.util.Optional.of(Block.box(3, 0, 0, 15, 16, 16)).get();

    public static final VoxelShape SHAPE_S = java.util.Optional.of(Block.box(0, 0, 1, 16, 16, 13)).get();

    public static final VoxelShape SHAPE_E = java.util.Optional.of(Block.box(1, 0, 0, 13, 16, 16)).get();

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case EAST -> SHAPE_E;
            case WEST -> SHAPE_W;
            case SOUTH -> SHAPE_S;
            default -> SHAPE_N;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()); }
}



