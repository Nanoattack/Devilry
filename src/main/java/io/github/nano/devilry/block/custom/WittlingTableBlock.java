package io.github.nano.devilry.devilry.block.custom;

import io.github.nano.devilry.devilry.blockentity.ModBlockEntities;
import io.github.nano.devilry.devilry.blockentity.WittlingTableEntity;
import io.github.nano.devilry.devilry.container.WittlingContainer;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class WittlingTableBlock extends BaseEntityBlock
{
    public WittlingTableBlock(Properties builder) {
        super(builder);
    }

    @Override
    public RenderShape getRenderShape(BlockState iBlockState) {
        return RenderShape.MODEL;
    }

    //BLOCK ENTITY STUFF

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> entityType){
        return entityType == ModBlockEntities.WITTLING_ENTITY.get() ?
                (world2, pos, state2, entity) -> ((WittlingTableEntity)entity).tick() : null;
    }

    // drop blocks in getInventory() of the tile entity
    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        WittlingTableEntity te = (WittlingTableEntity) worldIn.getBlockEntity(pos);

        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {

            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(0)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(1)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(2)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(3)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(4)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(5)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(6)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(7)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(8)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(9)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(10)));
        });
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, Level worldIn, BlockPos pos, Explosion explosion) {
        if (worldIn instanceof ServerLevel) {
        WittlingTableEntity te = (WittlingTableEntity) worldIn.getBlockEntity(pos);

        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {

            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(0)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(1)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(2)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(3)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(4)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(5)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(6)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(7)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(8)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(9)));
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, h.getStackInSlot(10)));
        });
    }
        super.onBlockExploded(state, worldIn, pos, explosion);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return new WittlingTableEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof WittlingTableEntity) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("screen.devilry.wittlingtable");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new WittlingContainer(windowId, level, pos, playerInventory, playerEntity, ((WittlingTableEntity)blockEntity).wittlingData);
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

