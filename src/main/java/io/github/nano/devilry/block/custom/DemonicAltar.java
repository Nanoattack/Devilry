package io.github.nano.devilry.block.custom;

import io.github.nano.devilry.block.ModBlocks;
import io.github.nano.devilry.blockentity.DemonicAltarBlockEntity;
import io.github.nano.devilry.blockentity.MortarBlockEntity;
import io.github.nano.devilry.container.DemonicAltarMenu;
import io.github.nano.devilry.container.MortarMenu;
import io.github.nano.devilry.events.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
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
        BlockPos relative = pContext.getClickedPos().relative(pContext.getHorizontalDirection().getCounterClockWise());
        return pContext.getLevel().getBlockState(relative).canBeReplaced(pContext) && pContext.getLevel().getWorldBorder().isWithinBounds(relative) ? state.setValue(FACING, pContext.getHorizontalDirection()) : null;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DemonicAltarBlockEntity(pPos, pState);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @javax.annotation.Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (!pLevel.isClientSide) {
            BlockPos blockpos = pPos.relative(pState.getValue(FACING).getCounterClockWise());
            pLevel.setBlock(blockpos, ModBlocks.DEMONIC_ALTAR_SIDE.get().defaultBlockState().setValue(FACING, pState.getValue(FACING).getOpposite()), 3);
            pLevel.blockUpdated(pPos, Blocks.AIR);
            pState.updateNeighbourShapes(pLevel, pPos, 3);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pDirection.getAxis().isHorizontal()) {
            if (pDirection == pState.getValue(FACING).getCounterClockWise()) {
                if (pLevel.getBlockState(pCurrentPos.relative(pState.getValue(FACING).getCounterClockWise())).is(Blocks.AIR)) {
                    return ModBlocks.LIMESTONE_ALTAR.get().defaultBlockState().setValue(FACING, pDirection.getClockWise());
                }
            }
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.getBlockEntity(pPos) instanceof DemonicAltarBlockEntity demonicAltarBlockEntity) {
            if (!pLevel.isClientSide() && pHand == InteractionHand.MAIN_HAND) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public @NotNull Component getDisplayName() {
                        return Component.translatableWithFallback("screen.devilry.demonic_altar", "Demonic Altar");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player playerEntity) {
                        return new DemonicAltarMenu(windowId, playerInventory, demonicAltarBlockEntity, demonicAltarBlockEntity.altarData);
                    }
                };
                NetworkHooks.openScreen((ServerPlayer) pPlayer, containerProvider, pPos);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
