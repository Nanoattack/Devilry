package com.nano.devilry.blockentity;

import com.nano.devilry.data.recipes.ModRecipeTypes;
import com.nano.devilry.data.recipes.MortarRecipe;
import com.nano.devilry.item.ModItems;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class MortarEntity extends BlockEntity
{
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(()-> itemHandler);

    public MortarEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.MORTAR_ENTITY.get(), pos, state);
    }
        
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(8)
        {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot) {
                    case 0: return stack.getItem() == ModItems.PESTLE.get();
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        return true;
                    case 7: return stack.getItem() == ModItems.ALCHEMICAL_ESSENCE.get() ||
                            stack.getItem() == ModItems.BRONZE_BLEND.get();
                    default:
                        return false;
                }
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(!isItemValid(slot, stack)) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }
    public void tick() {
        if(level.isClientSide)
               return;
        craft();
    }

    public void craft()
        {
            SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                inv.setItem(i, itemHandler.getStackInSlot(i));
            }

            Optional<MortarRecipe> recipe = level.getRecipeManager()
                    .getRecipeFor(ModRecipeTypes.MORTAR_RECIPE, inv, level);

            recipe.ifPresent(iRecipe -> {
                ItemStack output = iRecipe.getResultItem();

                craftTheItem(output);

                setChanged();
            });

        }

    private void craftTheItem(ItemStack output) {
        itemHandler.extractItem(0, 1, false);
        itemHandler.extractItem(1, 1, false);
        itemHandler.extractItem(2, 1, false);
        itemHandler.extractItem(3, 1, false);
        itemHandler.extractItem(4, 1, false);
        itemHandler.extractItem(5, 1, false);
        itemHandler.extractItem(6, 1, false);


        itemHandler.insertItem(7, output, false);
    }

        @Override
    public void load(CompoundTag pTag) {
        itemHandler.deserializeNBT(serializeNBT().getCompound("inv"));
        super.load(pTag);
    }

    @Override
    public CompoundTag save(CompoundTag pTag) {
        pTag.put("inv", itemHandler.serializeNBT());
        return super.save(pTag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }

}
