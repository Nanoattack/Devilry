package com.nano.devilry.setup.tileentity;

import com.nano.devilry.setup.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AlchemyStationTile extends TileEntity implements ITickableTileEntity
{

    private final ItemStackHandler itemHandler = createHandler();
    private final CustomEnergyStorage energyStorage = createEnergyStorage();

    private final LazyOptional<IItemHandler> handler = LazyOptional.of(()-> itemHandler);
    private final LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(() -> energyStorage);

    private int tick = 0;

    public AlchemyStationTile(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }

    public AlchemyStationTile()
    {
        this(ModTileEntities.ALCHEMY_STATION_TILE_ENTITY.get());
    }

    private CustomEnergyStorage createEnergyStorage() {
        return new CustomEnergyStorage(100, 0) {
            @Override
            protected void onEnergyChanged()
            {
             setChanged();
            }
        };
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
        energyHandler.invalidate();
    }

    @Override
    public void tick()
    {
        if(level.isClientSide)
        {
            return;
        }

        tick++;
        if(tick > 10) // should be configurable
        {
            if(this.itemHandler.getStackInSlot(0).getItem() == Items.TOTEM_OF_UNDYING && this.energyStorage.getEnergyStored() < 64)
            {
                itemHandler.extractItem(0, 1, false);
                energyStorage.generatePower(1);
            }

            if(this.itemHandler.getStackInSlot(1).getItem() == Items.HEART_OF_THE_SEA
                && energyStorage.getEnergyStored() > 0 && this.itemHandler.getStackInSlot(2).getCount() < 64)
            {
                itemHandler.extractItem(1, 1, false);
                itemHandler.insertItem(2, new ItemStack(ModItems.ALCHEMICAL_BLEND.get(), 1), false);
                energyStorage.extractEnergy(1, false);
            }

            tick = 0;
            setChanged();
        }
    }
    @Override
    public void load(BlockState state, CompoundNBT tag)

    {
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        energyStorage.deserializeNBT(tag.getCompound("energy"));

        tick = tag.getInt("counter");
        super.load(state,tag);
    }
    @Override
    public CompoundNBT save(CompoundNBT tag)
    {
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("energy", energyStorage.serializeNBT());

        tag.putInt("counter", tick);
        return super.save(tag);
    }

    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler(3)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack)
            {
                switch (slot)
                {
                    case 0: return stack.getItem() == ModItems.ALCHEMICAL_BLEND.get();
                    case 1: return stack.getItem() == Items.HEART_OF_THE_SEA;
                    case 2: return stack.getItem() == ModItems.ELDRITCH_IDOL.get();
                    default: return false;

                }
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
            {
                if(!isItemValid(slot, stack))
                {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    public <T> LazyOptional<T> getCapability (@Nonnull Capability<T> capability, @Nullable Direction side)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return handler.cast();
        }

        else if(capability == CapabilityEnergy.ENERGY)

        {
            return energyHandler.cast();
        }
        return super.getCapability(capability, side);
    }

}