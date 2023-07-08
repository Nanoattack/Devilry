package io.github.nano.devilry.blockentity;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.mojang.datafixers.util.Either;
import io.github.nano.devilry.container.DemonicAltarMenu;
import io.github.nano.devilry.container.cache.CacheItem;
import io.github.nano.devilry.container.cache.basicItem;
import io.github.nano.devilry.container.cache.RecipeCache;
import io.github.nano.devilry.data.recipes.AltarRecipe;
import io.github.nano.devilry.data.recipes.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
//todo

public class DemonicAltarBlockEntity extends BlockEntity implements MenuProvider {
    private LivingEntity sacrifice;

    public final ItemStackHandler itemHandler = new ItemStackHandler(7) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot == 6) return false;
            if (level == null) return true;

            ArrayList<ItemStack> container = new ArrayList<>();
            for (int i = 0; i <= 5; i++) {
                container.add(DemonicAltarBlockEntity.this.itemHandler.getStackInSlot(i));
            }
            List<AltarRecipe> possibleRecipes;
            try {
                possibleRecipes = cache.get().get(container.stream().map(map -> new basicItem(map.getItem())).toList());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            for (AltarRecipe possibleRecipe : possibleRecipes) {
                var list = new ArrayList<>(possibleRecipe.getIngredients());
                var items = new ArrayList<>(container);
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < items.size(); j++) {
                        if (list.get(i).test(items.get(j))) {
                            items.set(j, ItemStack.EMPTY);
                            list.set(i, Ingredient.EMPTY);
                        }
                    }
                }
                if (list.stream().anyMatch(ingredient -> ingredient.test(stack))) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int getSlotLimit(int slot) {
            return slot == 0 ? 1 : super.getSlotLimit(slot);
        }
    };


    public final AtomicReference<LoadingCache<List<? extends CacheItem>, List<AltarRecipe>>> cache = new AtomicReference<>();

    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public final ContainerData altarData;

    public DemonicAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DEMONIC_ALTAR_ENTITY.get(), pos, state);
        cache.set(CacheBuilder.newBuilder().build(new RecipeCache<>() {
            @Override
            public Level getLevel() {
                return level;
            }

            @Override
            public AtomicReference<LoadingCache<List<? extends CacheItem>, List<AltarRecipe>>> getCache() {
                return cache;
            }

            @Override
            public RecipeType<AltarRecipe> getRecipeType() {
                return ModRecipeTypes.DEMON_ALTAR_RECIPE.get();
            }
        }));

        altarData = new SimpleContainerData(0) {
            @Override
            public int get(int index)
            {
                return 0;
            }

            @Override
            public void set(int index, int value) {
            }
        };
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatableWithFallback("devilry.block_entities.demonic_altar", "Demonic Altar");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new DemonicAltarMenu(pContainerId, pPlayerInventory, this, this.altarData);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        handler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        handler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());

        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public boolean hasRecipe() {
        if (level == null) return false;
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Optional<AltarRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.DEMON_ALTAR_RECIPE.get(), inventory, level);

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) && correctSacrifice(recipe.get()) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().assemble(inventory, level.registryAccess()));
    }

    public boolean correctSacrifice(AltarRecipe recipe){
        if (sacrifice == null) {
            return false;
        }
        for (Either<EntityType<?>, MobType> entityTypeMobTypeEither : recipe.getSacrifice()) {
            if (entityTypeMobTypeEither.left().isPresent()) {
                if (sacrifice.getType().equals(entityTypeMobTypeEither.left().get())) {
                    return true;
                }
            } else {
                if (sacrifice.getMobType() == entityTypeMobTypeEither.right().get()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(6).getItem() == stack.getItem() || inventory.getItem(6).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(6).getMaxStackSize() > inventory.getItem(6).getCount();
    }

    public void craftItem() {
        if (level == null) return;
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Optional<AltarRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.DEMON_ALTAR_RECIPE.get(), inventory, level);

        if(hasRecipe()) {
            for (int i = 1; i < 6; i++) {
                itemHandler.extractItem(i, 1, false);
            }
            setSacrifice(null);
            ItemStack stack = itemHandler.getStackInSlot(0);
            stack.setDamageValue(itemHandler.getStackInSlot(0).getDamageValue() +1);
            itemHandler.setStackInSlot(0, stack);
            itemHandler.setStackInSlot(6, new ItemStack(recipe.get().assemble(inventory, level.registryAccess()).getItem(),
                    itemHandler.getStackInSlot(6).getCount() + recipe.get().assemble(inventory, level.registryAccess()).getCount()));

        }
    }

    public LivingEntity getSacrifice() {
        return sacrifice;
    }

    public void setSacrifice(LivingEntity sacrifice) {
        this.sacrifice = sacrifice;
    }
}