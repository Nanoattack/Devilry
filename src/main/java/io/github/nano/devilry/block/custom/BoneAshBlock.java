package io.github.nano.devilry.block.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import io.github.nano.devilry.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BoneAshBlock extends Block {
    public static final EnumProperty<RedstoneSide> NORTH = BlockStateProperties.NORTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> EAST = BlockStateProperties.EAST_REDSTONE;
    public static final EnumProperty<RedstoneSide> SOUTH = BlockStateProperties.SOUTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> WEST = BlockStateProperties.WEST_REDSTONE;
    public static final Map<Direction, EnumProperty<RedstoneSide>> PROPERTY_BY_DIRECTION = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST));
    private static final VoxelShape SHAPE_DOT = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
    private static final Map<Direction, VoxelShape> SHAPES_FLOOR = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Direction.SOUTH, Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Direction.EAST, Block.box(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Direction.WEST, Block.box(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));
    private static final Map<Direction, VoxelShape> SHAPES_UP = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Shapes.or(SHAPES_FLOOR.get(Direction.NORTH), Block.box(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)), Direction.SOUTH, Shapes.or(SHAPES_FLOOR.get(Direction.SOUTH), Block.box(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)), Direction.EAST, Shapes.or(SHAPES_FLOOR.get(Direction.EAST), Block.box(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)), Direction.WEST, Shapes.or(SHAPES_FLOOR.get(Direction.WEST), Block.box(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))));
    private static final Map<BlockState, VoxelShape> SHAPES_CACHE = Maps.newHashMap();
    private final BlockState crossState;

    public BoneAshBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, RedstoneSide.NONE).setValue(EAST, RedstoneSide.NONE).setValue(SOUTH, RedstoneSide.NONE).setValue(WEST, RedstoneSide.NONE));
        this.crossState = this.defaultBlockState().setValue(NORTH, RedstoneSide.SIDE).setValue(EAST, RedstoneSide.SIDE).setValue(SOUTH, RedstoneSide.SIDE).setValue(WEST, RedstoneSide.SIDE);

        for(BlockState blockstate : this.getStateDefinition().getPossibleStates()) {
            SHAPES_CACHE.put(blockstate, this.calculateShape(blockstate));
        }
    }

    private VoxelShape calculateShape(BlockState pState) {
        VoxelShape voxelshape = SHAPE_DOT;

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide redstoneside = pState.getValue(PROPERTY_BY_DIRECTION.get(direction));
            if (redstoneside == RedstoneSide.SIDE) {
                voxelshape = Shapes.or(voxelshape, SHAPES_FLOOR.get(direction));
            } else if (redstoneside == RedstoneSide.UP) {
                voxelshape = Shapes.or(voxelshape, SHAPES_UP.get(direction));
            }
        }

        return voxelshape;
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPES_CACHE.get(pState);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.getConnectionState(pContext.getLevel(), this.crossState, pContext.getClickedPos());
    }

    private BlockState getConnectionState(BlockGetter pLevel, BlockState pState, BlockPos pPos) {
        boolean flag = isDot(pState);
        pState = this.getMissingConnections(pLevel, this.defaultBlockState(), pPos);
        if (!flag || !isDot(pState)) {
            boolean flag1 = pState.getValue(NORTH).isConnected();
            boolean flag2 = pState.getValue(SOUTH).isConnected();
            boolean flag3 = pState.getValue(EAST).isConnected();
            boolean flag4 = pState.getValue(WEST).isConnected();
            boolean flag5 = !flag1 && !flag2;
            boolean flag6 = !flag3 && !flag4;
            if (!flag4 && flag5) {
                pState = pState.setValue(WEST, RedstoneSide.SIDE);
            }

            if (!flag3 && flag5) {
                pState = pState.setValue(EAST, RedstoneSide.SIDE);
            }

            if (!flag1 && flag6) {
                pState = pState.setValue(NORTH, RedstoneSide.SIDE);
            }

            if (!flag2 && flag6) {
                pState = pState.setValue(SOUTH, RedstoneSide.SIDE);
            }

        }
        return pState;
    }

    private BlockState getMissingConnections(BlockGetter pLevel, BlockState pState, BlockPos pPos) {
        boolean flag = !pLevel.getBlockState(pPos.above()).is(ModBlocks.BONE_ASH.get());

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (!pState.getValue(PROPERTY_BY_DIRECTION.get(direction)).isConnected()) {
                RedstoneSide redstoneside = this.getConnectingSide(pLevel, pPos, direction, flag);
                pState = pState.setValue(PROPERTY_BY_DIRECTION.get(direction), redstoneside);
            }
        }

        return pState;
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    public @NotNull BlockState updateShape(@NotNull BlockState pState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
        if (pFacing == Direction.DOWN) {
            return pState;
        } else if (pFacing == Direction.UP) {
            return this.getConnectionState(pLevel, pState, pCurrentPos);
        } else {
            RedstoneSide redstoneside = this.getConnectingSide(pLevel, pCurrentPos, pFacing);
            return redstoneside.isConnected() == pState.getValue(PROPERTY_BY_DIRECTION.get(pFacing)).isConnected() && !isCross(pState) ? pState.setValue(PROPERTY_BY_DIRECTION.get(pFacing), redstoneside) : this.getConnectionState(pLevel, this.crossState.setValue(PROPERTY_BY_DIRECTION.get(pFacing), redstoneside), pCurrentPos);
        }
    }

    private static boolean isCross(BlockState pState) {
        return pState.getValue(NORTH).isConnected() && pState.getValue(SOUTH).isConnected() && pState.getValue(EAST).isConnected() && pState.getValue(WEST).isConnected();
    }

    private static boolean isDot(BlockState pState) {
        return !pState.getValue(NORTH).isConnected() && !pState.getValue(SOUTH).isConnected() && !pState.getValue(EAST).isConnected() && !pState.getValue(WEST).isConnected();
    }

    /**
     * Performs updates on diagonal neighbors of the target position and passes in the flags.
     * The flags are equivalent to {@link net.minecraft.world.level.Level#setBlock}.
     */
    public void updateIndirectNeighbourShapes(@NotNull BlockState pState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, int pFlags, int pRecursionLeft) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide redstoneside = pState.getValue(PROPERTY_BY_DIRECTION.get(direction));
            if (redstoneside != RedstoneSide.NONE && !pLevel.getBlockState(blockpos$mutableblockpos.setWithOffset(pPos, direction)).is(this)) {
                blockpos$mutableblockpos.move(Direction.DOWN);
                BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos);
                if (blockstate.is(this)) {
                    BlockPos blockpos = blockpos$mutableblockpos.relative(direction.getOpposite());
                    pLevel.neighborShapeChanged(direction.getOpposite(), pLevel.getBlockState(blockpos), blockpos$mutableblockpos, blockpos, pFlags, pRecursionLeft);
                }

                blockpos$mutableblockpos.setWithOffset(pPos, direction).move(Direction.UP);
                BlockState blockstate1 = pLevel.getBlockState(blockpos$mutableblockpos);
                if (blockstate1.is(this)) {
                    BlockPos blockpos1 = blockpos$mutableblockpos.relative(direction.getOpposite());
                    pLevel.neighborShapeChanged(direction.getOpposite(), pLevel.getBlockState(blockpos1), blockpos$mutableblockpos, blockpos1, pFlags, pRecursionLeft);
                }
            }
        }

    }

    private RedstoneSide getConnectingSide(BlockGetter pLevel, BlockPos pPos, Direction pFace) {
        return this.getConnectingSide(pLevel, pPos, pFace, !pLevel.getBlockState(pPos.above()).is(ModBlocks.BONE_ASH.get()));
    }

    private RedstoneSide getConnectingSide(BlockGetter pLevel, BlockPos pPos, Direction pDirection, boolean pNonNormalCubeAbove) {
        BlockPos blockpos = pPos.relative(pDirection);
        BlockState blockstate = pLevel.getBlockState(blockpos);
        if (pNonNormalCubeAbove) {
            boolean flag = blockstate.getBlock() instanceof TrapDoorBlock || this.canSurviveOn(pLevel, blockpos, blockstate);
            if (flag && pLevel.getBlockState(blockpos.above()).is(ModBlocks.BONE_ASH.get())) {
                if (blockstate.isFaceSturdy(pLevel, blockpos, pDirection.getOpposite())) {
                    return RedstoneSide.UP;
                }

                return RedstoneSide.SIDE;
            }
        }

        if (blockstate.is(ModBlocks.BONE_ASH.get())) {
            return RedstoneSide.SIDE;
        } else {
            BlockPos blockPosBelow = blockpos.below();
            return pLevel.getBlockState(blockPosBelow).is(ModBlocks.BONE_ASH.get()) ? RedstoneSide.SIDE : RedstoneSide.NONE;
        }
    }

    public boolean canSurvive(@NotNull BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return this.canSurviveOn(pLevel, blockpos, blockstate);
    }

    private boolean canSurviveOn(BlockGetter pReader, BlockPos pPos, BlockState pState) {
        return pState.isFaceSturdy(pReader, pPos, Direction.UP) || pState.is(Blocks.HOPPER);
    }

    public void onPlace(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pOldState.is(pState.getBlock()) && !pLevel.isClientSide) {
            for(Direction direction : Direction.Plane.VERTICAL) {
                pLevel.updateNeighborsAt(pPos.relative(direction), this);
            }
        }
    }

    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        if (!pIsMoving && !pState.is(pNewState.getBlock())) {
            super.onRemove(pState, pLevel, pPos, pNewState, false);
            if (!pLevel.isClientSide) {
                for(Direction direction : Direction.values()) {
                    pLevel.updateNeighborsAt(pPos.relative(direction), this);
                }
            }
        }
    }

    public void neighborChanged(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Block pBlock, @NotNull BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide && !pState.canSurvive(pLevel, pPos)) {
            dropResources(pState, pLevel, pPos);
            pLevel.removeBlock(pPos, false);
        }
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#rotate} whenever
     * possible. Implementing/overriding is fine.
     */
    public @NotNull BlockState rotate(@NotNull BlockState pState, Rotation pRotation) {
        return switch (pRotation) {
            case CLOCKWISE_180 ->
                    pState.setValue(NORTH, pState.getValue(SOUTH)).setValue(EAST, pState.getValue(WEST)).setValue(SOUTH, pState.getValue(NORTH)).setValue(WEST, pState.getValue(EAST));
            case COUNTERCLOCKWISE_90 ->
                    pState.setValue(NORTH, pState.getValue(EAST)).setValue(EAST, pState.getValue(SOUTH)).setValue(SOUTH, pState.getValue(WEST)).setValue(WEST, pState.getValue(NORTH));
            case CLOCKWISE_90 ->
                    pState.setValue(NORTH, pState.getValue(WEST)).setValue(EAST, pState.getValue(NORTH)).setValue(SOUTH, pState.getValue(EAST)).setValue(WEST, pState.getValue(SOUTH));
            default -> pState;
        };
    }

    /**
     * Returns the blockState with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockState.
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#mirror} whenever
     * possible. Implementing/overriding is fine.
     */
    public @NotNull BlockState mirror(@NotNull BlockState pState, Mirror pMirror) {
        return switch (pMirror) {
            case LEFT_RIGHT -> pState.setValue(NORTH, pState.getValue(SOUTH)).setValue(SOUTH, pState.getValue(NORTH));
            case FRONT_BACK -> pState.setValue(EAST, pState.getValue(WEST)).setValue(WEST, pState.getValue(EAST));
            default -> super.mirror(pState, pMirror);
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, EAST, SOUTH, WEST);
    }
}
