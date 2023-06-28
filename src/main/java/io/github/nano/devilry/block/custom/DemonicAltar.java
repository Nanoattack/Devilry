package io.github.nano.devilry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class DemonicAltar extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    VoxelShape NORTH = Stream.of(
            Block.box(0, 0, 2, 16, 2, 14),
            Block.box(0, 2, 4, 12, 14, 12),
            Block.box(12, 2, 3, 15, 14, 13),
            Block.box(0, 14, 2, 16, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    VoxelShape EAST = Stream.of(
            Block.box(2, 0, 0, 14, 2, 16),
            Block.box(4, 2, 0, 12, 14, 12),
            Block.box(3, 2, 12, 13, 14, 15),
            Block.box(2, 14, 0, 14, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    VoxelShape SOUTH = Stream.of(
            Block.box(0, 0, 2, 16, 2, 14),
            Block.box(4, 2, 4, 16, 14, 12),
            Block.box(1, 2, 3, 4, 14, 13),
            Block.box(0, 14, 2, 16, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    VoxelShape WEST = Stream.of(
            Block.box(2, 0, 0, 14, 2, 16),
            Block.box(4, 2, 4, 12, 14, 16),
            Block.box(3, 2, 1, 13, 14, 4),
            Block.box(2, 14, 0, 14, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public DemonicAltar(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    public @NotNull BlockState rotate(@NotNull BlockState pState, Rotation pRot) {
        return switch (pRot) {
            case CLOCKWISE_180 ->
                    pState.setValue(FACING, Direction.SOUTH);
            case COUNTERCLOCKWISE_90 ->
                    pState.setValue(FACING, Direction.WEST);
            case CLOCKWISE_90 ->
                    pState.setValue(FACING, Direction.EAST);
            default -> pState;
        };
    }
    public @NotNull BlockState mirror(@NotNull BlockState pState, Mirror pMirror) {
        return switch (pMirror) {
            case LEFT_RIGHT ->
                    pState.setValue(FACING, Direction.SOUTH);
            case FRONT_BACK ->
                    pState.setValue(FACING, Direction.WEST);
            default -> super.mirror(pState, pMirror);
        };
    }
    @Override
    public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            default -> NORTH;
        };
    }
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        BlockState state = super.getStateForPlacement(pContext);
        if (state == null) {
            return null;
        }
        return state.setValue(FACING, pContext.getHorizontalDirection());
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }
}