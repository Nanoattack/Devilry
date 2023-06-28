package io.github.nano.devilry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class LimeStoneAltar extends Block {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    VoxelShape SINGLE = Stream.of(
            Block.box(0, 0, 2, 16, 2, 14),
            Block.box(4, 2, 4, 12, 14, 12),
            Block.box(0, 14, 2, 16, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    VoxelShape SINGLE_90 = Stream.of(
            Block.box(2, 0, 0, 14, 2, 16),
            Block.box(4, 2, 4, 12, 14, 12),
            Block.box(2, 14, 0, 14, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    VoxelShape CENTRE_90 = Stream.of(
            Block.box(0, 0, 2, 16, 2, 14),
            Block.box(0, 2, 4, 16, 14, 12),
            Block.box(0, 14, 2, 16, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    VoxelShape CENTRE = Stream.of(
            Block.box(2, 0, 0, 14, 2, 16),
            Block.box(4, 2, 0, 12, 14, 16),
            Block.box(2, 14, 0, 14, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    VoxelShape EDGE_NORTH = Stream.of(
            Block.box(2, 0, 0, 14, 2, 16),
            Block.box(4, 2, 0, 12, 14, 12),
            Block.box(2, 14, 0, 14, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    VoxelShape EDGE_EAST = Stream.of(
            Block.box(0, 0, 2, 16, 2, 14),
            Block.box(4, 2, 4, 16, 14, 12),
            Block.box(0, 14, 2, 16, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    VoxelShape EDGE_SOUTH = Stream.of(
            Block.box(2, 0, 0, 14, 2, 16),
            Block.box(4, 2, 4, 12, 14, 16),
            Block.box(2, 14, 0, 14, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    VoxelShape EDGE_WEST = Stream.of(
            Block.box(0, 0, 2, 16, 2, 14),
            Block.box(0, 2, 4, 12, 14, 12),
            Block.box(0, 14, 2, 16, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public LimeStoneAltar(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(getStateDefinition().any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(FACING, Direction.NORTH));
    }

    @Override
    public @NotNull BlockState rotate(@NotNull BlockState pState, Rotation pRot) {
        return switch (pRot) {
            case CLOCKWISE_180 ->
                    pState.setValue(NORTH, pState.getValue(SOUTH)).setValue(EAST, pState.getValue(WEST)).setValue(SOUTH, pState.getValue(NORTH)).setValue(WEST, pState.getValue(EAST));
            case COUNTERCLOCKWISE_90 ->
                    pState.setValue(NORTH, pState.getValue(EAST)).setValue(EAST, pState.getValue(SOUTH)).setValue(SOUTH, pState.getValue(WEST)).setValue(WEST, pState.getValue(NORTH));
            case CLOCKWISE_90 ->
                    pState.setValue(NORTH, pState.getValue(WEST)).setValue(EAST, pState.getValue(NORTH)).setValue(SOUTH, pState.getValue(EAST)).setValue(WEST, pState.getValue(SOUTH));
            default -> pState;
        };
    }

    @Override
    public @NotNull BlockState mirror(@NotNull BlockState pState, Mirror pMirror) {
        return switch (pMirror) {
            case LEFT_RIGHT -> pState.setValue(NORTH, pState.getValue(SOUTH)).setValue(SOUTH, pState.getValue(NORTH));
            case FRONT_BACK -> pState.setValue(EAST, pState.getValue(WEST)).setValue(WEST, pState.getValue(EAST));
            default -> super.mirror(pState, pMirror);
        };
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState pState, Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pNeighborPos) {
        pNeighborState = pLevel.getBlockState(pNeighborPos);
        if (pDirection.getAxis().isHorizontal()) {
            if (pNeighborState.is(this)) {
                if (pNeighborPos.distManhattan(pCurrentPos) == 1 && canConnect(pNeighborState, pDirection.getOpposite()) && canConnect(pState, pDirection)) {
                    return setConnection(pDirection, pState, true);
                }
            } else {
                if (hasConnection(pDirection, pState)) {
                    return setConnection(pDirection, pState, false);
                }
            }
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    public boolean canConnect(BlockState state, Direction direction) {
        return state.is(this) && !state.getValue(getPropertyFromDirection(direction.getClockWise())) &&
                !state.getValue(getPropertyFromDirection(direction.getCounterClockWise()));
    }

    public BlockState setConnection(Direction direction, BlockState state, boolean value) {
        return state.setValue(getPropertyFromDirection(direction), value);
    }

    public boolean hasConnection(Direction direction, BlockState state) {
        return state.getValue(getPropertyFromDirection(direction));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        boolean north = pState.getValue(NORTH);
        boolean east = pState.getValue(EAST);
        boolean south = pState.getValue(SOUTH);
        boolean west = pState.getValue(WEST);

        if (!(north || east || south || west)) {
            return pState.getValue(FACING) == Direction.NORTH || pState.getValue(FACING) == Direction.SOUTH ? SINGLE : SINGLE_90;
        } else if (north && south) {
            return CENTRE;
        } else if (east && west) {
            return CENTRE_90;
        } else if (north) {
            return EDGE_NORTH;
        } else if (east) {
            return EDGE_EAST;
        } else if (south) {
            return EDGE_SOUTH;
        } else {
            return EDGE_WEST;
        }
    }

    static BooleanProperty getPropertyFromDirection(Direction direction) {
        return switch (direction) {
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            default -> NORTH;
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        Direction[] directions = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        BlockPos blockpos = pContext.getClickedPos();

        BlockState state = super.getStateForPlacement(pContext);
        if (state == null) {
            return null;
        }
        state.setValue(FACING, pContext.getHorizontalDirection());
        for (Direction direction : directions) {
            updateShape(state, direction, level.getBlockState(blockpos.relative(direction)),
                    level, blockpos, blockpos.relative(direction));
        }

        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, EAST, WEST, SOUTH, FACING);
    }
}
