package io.github.nano.devilry.block.custom;

import io.github.nano.devilry.blockentity.ModBlockEntities;
import io.github.nano.devilry.blockentity.MortarEntity;
import io.github.nano.devilry.container.MortarContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.stream.Stream;
//todo
@SuppressWarnings("deprecation")
public class MortarBlock extends BaseEntityBlock
{
    private static final Property<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public MortarBlock(Properties builder) {
        super(builder);
    }

    public static final VoxelShape SHAPE_N = Stream.of(
            Block.box(10.5, 1, 5.5, 11.5, 3, 10.5),
            Block.box(4.5, 1, 5.5, 5.5, 3, 10.5),
            Block.box(5.5, 1, 10.5, 10.5, 3, 11.5),
            Block.box(5.5, 1, 4.5, 10.5, 3, 5.5),
            Block.box(5.5, 0, 5.5, 10.5, 1, 10.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_W = Stream.of(
            Block.box(5.5, 1, 4.5, 10.5, 3, 5.5),
            Block.box(5.5, 1, 10.5, 10.5, 3, 11.5),
            Block.box(10.5, 1, 5.5, 11.5, 3, 10.5),
            Block.box(4.5, 1, 5.5, 5.5, 3, 10.5),
            Block.box(5.5, 0, 5.5, 10.5, 1, 10.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_S = Stream.of(
            Block.box(4.5, 1, 5.5, 5.5, 3, 10.5),
            Block.box(10.5, 1, 5.5, 11.5, 3, 10.5),
            Block.box(5.5, 1, 4.5, 10.5, 3, 5.5),
            Block.box(5.5, 1, 10.5, 10.5, 3, 11.5),
            Block.box(5.5, 0, 5.5, 10.5, 1, 10.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_E = Stream.of(
            Block.box(5.5, 1, 10.5, 10.5, 3, 11.5),
            Block.box(5.5, 1, 4.5, 10.5, 3, 5.5),
            Block.box(4.5, 1, 5.5, 5.5, 3, 10.5),
            Block.box(10.5, 1, 5.5, 11.5, 3, 10.5),
            Block.box(5.5, 0, 5.5, 10.5, 1, 10.5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

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
    public @NotNull RenderShape getRenderShape(@NotNull BlockState iBlockState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HorizontalDirectionalBlock.FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    //BLOCK ENTITY STUFF

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level world, @NotNull BlockState state, @NotNull BlockEntityType<T> entityType){
        return entityType == ModBlockEntities.MORTAR_ENTITY.get() ?
                (world2, pos, state2, entity) -> ((MortarEntity)entity).tick() : null;
    }

    // drop blocks in getInventory() of the tile entity
    @Override
    public void playerWillDestroy(@NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        //todo: drop contents in onRemove using  Containers.dropContents

        super.playerWillDestroy(worldIn, pos, state, player);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState)
    {
        return new MortarEntity(pPos, pState);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult trace) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MortarEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public @NotNull Component getDisplayName() {
                        return Component.translatable("screen.devilry.mortar");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player playerEntity) {
                        return new MortarContainer(windowId, level, pos, playerInventory, playerEntity, ((MortarEntity)blockEntity).mortarData);
                    }
                };
                NetworkHooks.openScreen((ServerPlayer) player, containerProvider, blockEntity.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }
}

