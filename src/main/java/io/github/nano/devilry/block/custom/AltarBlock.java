package io.github.nano.devilry.devilry.block.custom;

import io.github.nano.devilry.devilry.blockentity.AltarEntity;
import io.github.nano.devilry.devilry.blockentity.ModBlockEntities;
import io.github.nano.devilry.devilry.container.AltarContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class AltarBlock extends BaseEntityBlock {

    private static final Property<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public AltarBlock(Properties builder) {
        super(builder);
    }

    public static final VoxelShape SHAPE_N = Stream.of(
            Block.box(2, 16, 5.5, 10, 17, 13.5),
            Block.box(0, 0, 3, 16, 16, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_W = Stream.of(
            Block.box(5.5, 16, 6, 13.5, 17, 14),
            Block.box(3, 0, 0, 15, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_S = Stream.of(
            Block.box(6, 16, 2.5, 14, 17, 10.5),
            Block.box(0, 0, 1, 16, 16, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_E = Stream.of(
            Block.box(2.5, 16, 2, 10.5, 17, 10),
            Block.box(1, 0, 0, 13, 16, 16)
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
    public RenderShape getRenderShape(BlockState iBlockState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HorizontalDirectionalBlock.FACING);
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

    //BLOCK ENTITY

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> entityType){
        return entityType == ModBlockEntities.ALTAR_ENTITY.get() ?
                (world2, pos, state2, entity) -> ((AltarEntity)entity).tick() : null;
    }

    // drop blocks in getInventory() of the tile entity
    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        AltarEntity te = (AltarEntity) worldIn.getBlockEntity(pos);

        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {

            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(0)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(1)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(2)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(3)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(4)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(5)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(6)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(7)));
        });
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, Level worldIn, BlockPos pos, Explosion explosion) {
        if (worldIn instanceof ServerLevel) {
            AltarEntity te = (AltarEntity) worldIn.getBlockEntity(pos);

            te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {

                worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(0)));
                worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(1)));
                worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(2)));
                worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(3)));
                worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(4)));
                worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(5)));
                worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(6)));
                worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(7)));
            });
        }
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    @javax.annotation.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new AltarEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AltarEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("screen.devilry.altar");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new AltarContainer(windowId, level, pos, playerInventory, playerEntity, ((AltarEntity)blockEntity).altarData);
                    }
                };
                NetworkHooks.openGui((ServerPlayer) player, containerProvider, blockEntity.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }
}




