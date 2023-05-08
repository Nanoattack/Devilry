package io.github.nano.devilry.blockentity;

import io.github.nano.devilry.data.recipes.ModRecipeTypes;
import io.github.nano.devilry.data.recipes.MortarRecipe;
import io.github.nano.devilry.events.ModSoundEvents;
import io.github.nano.devilry.item.ModItems;
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
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
//todo

public class MortarEntity extends BlockEntity
{
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(()-> itemHandler);
    int progress = 0;
    int max_progress = 72;
    public final ContainerData mortarData;

    public MortarEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MORTAR_ENTITY.get(), pos, state);
        mortarData = new SimpleContainerData(2) {
            @Override
            public int get(int index)
            {
                return switch (index) {
                    case 0 -> MortarEntity.this.progress;
                    case 1 -> MortarEntity.this.max_progress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: MortarEntity.this.progress = value;
                    case 1: MortarEntity.this.max_progress  = value;
                }
            }
        };
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("inv", itemHandler.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        itemHandler.deserializeNBT(compoundTag.getCompound("inv"));
    }

    //todo look at this
    @Override
    public @NotNull CompoundTag getUpdateTag()
    {
        return super.getUpdateTag();
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
                return switch (slot) {
                    case 0 -> stack.getItem() == ModItems.PESTLE.get() ||
                            stack.getItem() == ModItems.NETHERITE_PESTLE.get();
                    case 1, 2, 3, 4, 5, 6 -> true;
                    case 7 -> stack.getItem() == ModItems.ALCHEMICAL_ESSENCE.get() ||
                            stack.getItem() == ModItems.SULPHUR_DUST.get() ||
                            stack.getItem() == ModItems.BRONZE_BLEND.get() ||
                            stack.getItem() == Items.GUNPOWDER;
                    default -> false;
                };
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
    private boolean hasRecipe(MortarEntity entity) {
        Level world = entity.level;
        if (world == null) return false;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }
        Optional<MortarRecipe> match = world.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.MORTAR_RECIPE, inventory, world);
        return match.isPresent();
    }

    private void resetProgress() {
        this.progress = 0;
    }

    public void craft()
    {
        //todo look at duplicate code
        //noinspection DuplicatedCode
        if (level == null) return;

        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Optional<MortarRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.MORTAR_RECIPE, inv, level);

        recipe.ifPresent(iRecipe -> {

            ItemStack output = iRecipe.getResultItem();
            //fixme
            Integer amount = null;

            if (hasSpace(amount)) {

                craftTheItem(output, amount);

                checkDurability();

                setChanged();
            }
        });
    }

    public void tick() {
        //todo look at duplicate code
        //noinspection DuplicatedCode
        if (level == null) return;

        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Optional<MortarRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.MORTAR_RECIPE, inv, level);
        //fixme
        recipe.ifPresent(iRecipe -> {
            //fixme
            Integer amount = null;
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
        return itemHandler.getStackInSlot(7).getCount() + amount <= itemHandler.getStackInSlot(7).getMaxStackSize();
    }

    private void checkDurability()
    {
        if (level != null && itemHandler.getStackInSlot(0).getDamageValue() > itemHandler.getStackInSlot(0).getMaxDamage() - 1)
        {
            itemHandler.extractItem(0,1,false);
            level.playSound(null, getBlockPos(), SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
        }
    }

    private void craftTheItem(ItemStack output, Integer amount) {

        if (sameOutput(output) && level != null) {

            itemHandler.extractItem(1, 1, false);
            itemHandler.extractItem(2, 1, false);
            itemHandler.extractItem(3, 1, false);
            itemHandler.extractItem(4, 1, false);
            itemHandler.extractItem(5, 1, false);
            itemHandler.extractItem(6, 1, false);
            itemHandler.extractItem(7, 1, false);

            itemHandler.getStackInSlot(0).hurt(1, level.getRandom(), null);

            level.playSound(null, getBlockPos(), ModSoundEvents.MORTAR_GRIND.get(), SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

            this.resetProgress();
        }
        output.setCount(itemHandler.insertItem(7, output, false).getCount() + amount);
    }

    public boolean sameOutput(ItemStack output)
    {
        return itemHandler.insertItem(7, output, true) != output;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        //fixme
        if(cap == ForgeCapabilities.ITEM_HANDLER)
        {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

}