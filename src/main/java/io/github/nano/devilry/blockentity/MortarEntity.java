package io.github.nano.devilry.blockentity;

import io.github.nano.devilry.data.recipes.ModRecipeTypes;
import io.github.nano.devilry.data.recipes.MortarRecipe;
import io.github.nano.devilry.events.ModSoundEvents;
import io.github.nano.devilry.item.ModItems;
import io.github.nano.devilry.util.tags.DevilryTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
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

public class MortarEntity extends BaseContainerBlockEntity
{
    public int turns = 0;
    public int maxTurns = 4;

    public final ItemStackHandler itemHandler = new ItemStackHandler(8) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot == 0) {
                return stack.is(DevilryTags.Items.PESTLE_IN_MORTAR);
            }
            if (slot == 7) return false;
            if (level == null) return true;
            MortarRecipe recipe = MortarEntity.this.recipeCache.getRecipeFor(MortarEntity.this, level).orElse(null);
            if (recipe == null) return true;
            return recipe.isShaped() ? recipe.getIngredients().get(slot).test(stack) : recipe.getIngredients().stream().anyMatch(item -> item.test(stack));
        }

        @Override
        protected void onContentsChanged(int slot) {
            MortarEntity.this.setChanged();
            maxTurns = recipeCache.getRecipeFor(MortarEntity.this, level).get().getNeededCrushes();
        }
    };


    private final RecipeManager.CachedCheck<Container, MortarRecipe> recipeCache;

    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    int progress = 0;
    int max_progress = 72;
    public final ContainerData mortarData;

    public MortarEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MORTAR_ENTITY.get(), pos, state);
        recipeCache = RecipeManager.createCheck(ModRecipeTypes.MORTAR_RECIPE.get());
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
    protected Component getDefaultName() {
        return null;
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return null;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return null;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {

    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}