package com.nano.devilry.block.custom;

import com.nano.devilry.block.ModBlocks;
import com.nano.devilry.blockentity.ModBlockEntities;
import com.nano.devilry.blockentity.MortarEntity;
import com.nano.devilry.container.MortarContainer;
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
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.Stream;

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
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    //BLOCK ENTITY STUFF

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> entityType){
        return entityType == ModBlockEntities.MORTAR_ENTITY.get() ?
                (world2, pos, state2, entity) -> ((MortarEntity)entity).tick() : null;
    }

    // drop blocks in getInventory() of the tile entity
    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        MortarEntity te = (MortarEntity) worldIn.getBlockEntity(pos);

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
        MortarEntity te = (MortarEntity) worldIn.getBlockEntity(pos);

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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new MortarEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MortarEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("screen.devilry.mortar");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new MortarContainer(windowId, level, pos, playerInventory, playerEntity, ((MortarEntity)blockEntity).mortarData);
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

