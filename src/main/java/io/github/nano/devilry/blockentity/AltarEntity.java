package io.github.nano.devilry.devilry.blockentity;

import io.github.nano.devilry.devilry.data.recipes.Altar.AltarRecipe;
import io.github.nano.devilry.devilry.data.recipes.ModRecipeTypes;
import io.github.nano.devilry.devilry.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

public class AltarEntity extends BlockEntity
{
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(()-> itemHandler);
    int progress = 0;
    int max_progress = 72;
    public final ContainerData altarData;

    public AltarEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALTAR_ENTITY.get(), pos, state);
        altarData = new SimpleContainerData(2) {
            @Override
            public int get(int index)
            {
                switch (index) {
                    case 0: return AltarEntity.this.progress;
                    case 1: return AltarEntity.this.max_progress;
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: AltarEntity.this.progress = value;
                    case 1: AltarEntity.this.max_progress  = value;
                }
            }
        };
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inv", itemHandler.serializeNBT());
        return tag;
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        compound.put("inv", itemHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        itemHandler.deserializeNBT(compoundTag.getCompound("inv"));
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.save(new CompoundTag());
    }

    @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket() {

        return ClientboundBlockEntityDataPacket.create(this, (tag) -> this.getUpdateTag());
    }

    @Override
    public void onDataPacket(final Connection net, final ClientboundBlockEntityDataPacket pkt)
    {
        this.deserializeNBT(pkt.getTag());
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
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
                    case 0: return stack.getItem() == ModItems.PESTLE.get()||
                            stack.getItem() == ModItems.NETHERITE_PESTLE.get();
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        return true;
                    case 7: return stack.getItem() == ModItems.ALCHEMICAL_ESSENCE.get()||
                                    stack.getItem() == ModItems.SULPHUR_DUST.get()||
                                    stack.getItem() == ModItems.BRONZE_BLEND.get()||
                                    stack.getItem() == Items.GUNPOWDER;
                    default:
                        return false;
                }
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
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
    private boolean hasRecipe(AltarEntity entity) {
        Level world = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        Optional<AltarRecipe> match = world.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.ALTAR_RECIPE, inventory, world);
        return match.isPresent();
    }

    private void resetProgress() {
        this.progress = 0;
    }

    public void craft()
    {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Optional<AltarRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.ALTAR_RECIPE, inv, level);

        recipe.ifPresent(iRecipe -> {

            ItemStack output = iRecipe.getResultItem();
            Integer amount = iRecipe.getAmount();

            if (hasSpace(amount)) {

                craftTheItem(output, amount);

                checkDurability();

                setChanged();
            }
        });
    }

    public void tick() {

        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Optional<AltarRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.ALTAR_RECIPE, inv, level);

        recipe.ifPresent(iRecipe -> {
            Integer amount = iRecipe.getAmount();
            ItemStack output = iRecipe.getResultItem();

            if (!level.isClientSide) {
                if (hasRecipe(this)
                        && (hasSpace(amount))
                            &&(sameOutput(output))) {
                    this.progress++;
                    if (this.progress > this.max_progress) {
                        craft();
                    }
                } else {
                    this.resetProgress();
                }
            }
        });
        if (recipe.isEmpty()) {
            resetProgress();
        }
    }

    public boolean hasSpace(Integer amount)
    {
        if (itemHandler.getStackInSlot(7).getCount() + amount <= itemHandler.getStackInSlot(7).getMaxStackSize())
        {
            return true;
        }
        return false;
    }

    private void checkDurability()
    {
        if (itemHandler.getStackInSlot(0).getDamageValue() > itemHandler.getStackInSlot(0).getMaxDamage() - 1)
        {
            itemHandler.extractItem(0,1,false);
            level.playSound((Player) null, getBlockPos(), SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
        }
    }

    private void craftTheItem(ItemStack output, Integer amount) {

        if (sameOutput(output)) {

            itemHandler.extractItem(1, 1, false);
            itemHandler.extractItem(2, 1, false);
            itemHandler.extractItem(3, 1, false);
            itemHandler.extractItem(4, 1, false);
            itemHandler.extractItem(5, 1, false);
            itemHandler.extractItem(6, 1, false);
            itemHandler.extractItem(7, 1, false);

            itemHandler.getStackInSlot(0).hurt(1, new Random(), null);

            level.playSound((Player) null, getBlockPos(), SoundEvents.BELL_RESONATE, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

            this.resetProgress();
        }
        output.setCount(itemHandler.insertItem(7, output, false).getCount() + amount);
    }

    public boolean sameOutput(ItemStack output)
    {
        if (itemHandler.insertItem(7, output, true) != output) {
            return true;
        }
        return false;
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