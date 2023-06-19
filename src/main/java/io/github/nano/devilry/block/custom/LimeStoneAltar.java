package io.github.nano.devilry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
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

    VoxelShape CENTRE = Stream.of(
            Block.box(0, 0, 2, 16, 2, 14),
            Block.box(0, 2, 4, 16, 14, 12),
            Block.box(0, 14, 2, 16, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    VoxelShape CENTRE_90 = Stream.of(
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
    }

    public BlockState rotate(@NotNull BlockState pState, Rotation pRot) {
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

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return switch (pMirror) {
            case LEFT_RIGHT -> pState.setValue(NORTH, pState.getValue(SOUTH)).setValue(SOUTH, pState.getValue(NORTH));
            case FRONT_BACK -> pState.setValue(EAST, pState.getValue(WEST)).setValue(WEST, pState.getValue(EAST));
            default -> super.mirror(pState, pMirror);
        };
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pDirection.getAxis().isHorizontal() && pNeighborState.is(this)) {
            boolean nNorth = pNeighborState.getValue(NORTH);
            boolean nEast = pNeighborState.getValue(EAST);
            boolean nSouth = pNeighborState.getValue(SOUTH);
            boolean nWest = pNeighborState.getValue(WEST);

            boolean north = pState.getValue(NORTH);
            boolean east = pState.getValue(EAST);
            boolean south = pState.getValue(SOUTH);
            boolean west = pState.getValue(WEST);
            if (pNeighborPos.distManhattan(pCurrentPos) == 1) {
                if (!(nNorth || nEast || nSouth || nWest)) {
                    if (!(north || east || south || west)) {
                        return pState.setValue(getPropertyFromDirection(pDirection), true);
                    }
                    if (pState.getValue(getPropertyFromDirection(pDirection.getOpposite()))) {
                        return pState.setValue(getPropertyFromDirection(pDirection), true);
                    }
                    return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
                }
                if (!(pNeighborState.getValue(getPropertyFromDirection(pDirection.getCounterClockWise())) ||
                        pNeighborState.getValue(getPropertyFromDirection(pDirection.getClockWise())))) {
                    return pState.setValue(getPropertyFromDirection(pDirection), true);
                } else {
                    return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
                }
            } else {
                return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
            }
        } else {
            return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
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

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockGetter blockgetter = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        BlockPos north = blockpos.north();
        BlockPos east = blockpos.east();
        BlockPos south = blockpos.south();
        BlockPos west = blockpos.west();
        BlockState northState = blockgetter.getBlockState(north);
        BlockState eastState = blockgetter.getBlockState(east);
        BlockState southState = blockgetter.getBlockState(south);
        BlockState westState = blockgetter.getBlockState(west);
        return super.getStateForPlacement(pContext)
                .setValue(NORTH,
                        northState.is(this) && (
                                northState.getValue(NORTH) ||
                                        !(northState.getValue(NORTH) ||
                                                northState.getValue(EAST) ||
                                                northState.getValue(SOUTH) ||
                                                northState.getValue(WEST))))
                .setValue(EAST,
                        eastState.is(this) && (
                                eastState.getValue(EAST) ||
                                        !(eastState.getValue(NORTH) ||
                                                eastState.getValue(EAST) ||
                                                eastState.getValue(SOUTH) ||
                                                eastState.getValue(WEST))))
                .setValue(SOUTH,
                        southState.is(this) && (
                                southState.getValue(SOUTH) ||
                                        !(southState.getValue(NORTH) ||
                                                southState.getValue(EAST) ||
                                                southState.getValue(SOUTH) ||
                                                southState.getValue(WEST))))
                .setValue(WEST,
                        westState.is(this) && (
                                westState.getValue(WEST) ||
                                        !(westState.getValue(NORTH) ||
                                                westState.getValue(EAST) ||
                                                westState.getValue(SOUTH) ||
                                                westState.getValue(WEST))))
                .setValue(FACING,
                        pContext.getHorizontalDirection());
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, EAST, WEST, SOUTH, FACING);
    }
}
