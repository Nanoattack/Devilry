package io.github.nano.devilry.blockentity;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.github.nano.devilry.container.MortarMenu;
import io.github.nano.devilry.data.recipes.ModRecipeTypes;
import io.github.nano.devilry.data.recipes.MortarRecipe;
import io.github.nano.devilry.util.Utils;
import io.github.nano.devilry.util.tags.DevilryTags;
import it.unimi.dsi.fastutil.booleans.BooleanObjectPair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.*;
//todo

public class MortarBlockEntity extends BaseContainerBlockEntity
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

            ArrayList<ItemStack> container = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                container.add(MortarBlockEntity.this.itemHandler.getStackInSlot(i));
            }
            List<MortarRecipe> possibleRecipes = Utils.getPossibleRecipes(recipeCache, container);
            for (MortarRecipe possibleRecipe : possibleRecipes) {
                if (possibleRecipe.isShaped()) {
                    return possibleRecipe.getIngredients().get(slot-1).test(stack);
                } else {
                    return possibleRecipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(stack));
                }
            }
            return false;
        }

        @Override
        protected void onContentsChanged(int slot) {
            MortarBlockEntity.this.setChanged();
            turns = 0;
            ArrayList<ItemStack> container = new ArrayList<>();
            for (int i = 1; i <= 6; i++) {
                container.add(MortarBlockEntity.this.itemHandler.getStackInSlot(i));
            }
            maxTurns = Utils.getPossibleRecipes(recipeCache, container).get(0).getNeededCrushes();
        }

        @Override
        public int getSlotLimit(int slot) {
            return slot == 0 ? 1 : super.getSlotLimit(slot);
        }
    };


    public final LoadingCache<BooleanObjectPair<NonNullList<Ingredient>>, MortarRecipe> recipeCache = CacheBuilder.newBuilder().build(new CacheLoader<>() {
        @Override
        public @NotNull MortarRecipe load(@NotNull BooleanObjectPair<NonNullList<Ingredient>> key) throws Exception {
            if (level == null) throw new Exception("level is null, perhaps retrieving values too earlY?");
            return getRecipe(key);
        }

        @Override
        public @NotNull Map<BooleanObjectPair<NonNullList<Ingredient>>, MortarRecipe> loadAll(@NotNull Iterable<? extends BooleanObjectPair<NonNullList<Ingredient>>> keys) throws Exception {
            if (level == null) throw new Exception("level is null, perhaps retrieving values too early?");
            Map<BooleanObjectPair<NonNullList<Ingredient>>, MortarRecipe> toReturn = new HashMap<>();
            for (BooleanObjectPair<NonNullList<Ingredient>> key : keys) {
                toReturn.put(key, getRecipe(key));
            }
            return toReturn;
        }
    });

    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public final ContainerData mortarData;

    public MortarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MORTAR_ENTITY.get(), pos, state);
        mortarData = new SimpleContainerData(2) {
            @Override
            public int get(int index)
            {
                return switch (index) {
                    case 0 -> MortarBlockEntity.this.turns;
                    case 1 -> MortarBlockEntity.this.maxTurns;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: MortarBlockEntity.this.turns = value;
                    case 1: MortarBlockEntity.this.maxTurns  = value;
                }
            }
        };
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatableWithFallback("devilry.block_entities.mortar", "Mortar");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new MortarMenu(pContainerId, pInventory, this, this.mortarData);
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
    public @NotNull ItemStack getItem(int pSlot) {
        return null;
    }

    @Override
    public @NotNull ItemStack removeItem(int pSlot, int pAmount) {
        return null;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int pSlot) {
        return null;
    }

    @Override
    public void setItem(int pSlot, @NotNull ItemStack pStack) {

    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return false;
    }

    @Override
    public void clearContent() {

    }

    public void tick(){

    }

    private MortarRecipe getRecipe(BooleanObjectPair<NonNullList<Ingredient>> key) throws Exception {
        if (key.firstBoolean()) {
            return Objects.requireNonNull(level).getRecipeManager().getAllRecipesFor(ModRecipeTypes.MORTAR_RECIPE.get()).stream().max(Comparator.comparingInt((MortarRecipe recipe) -> {
                int sum = 0;
                @NotNull NonNullList<Ingredient> ingredients = recipe.getIngredients();
                for (int j = 0; j < ingredients.size(); j++) {
                    Ingredient ingredient = ingredients.get(j);
                    int i = Utils.ingredientEquals(ingredient, key.right().get(j)) ? 1 : -1;
                    sum += i;
                }
                return sum;
            })).orElseThrow(() -> new Exception("couldn't find recipe with items: " + key.right()));
        } else {
            return Objects.requireNonNull(level).getRecipeManager().getAllRecipesFor(ModRecipeTypes.MORTAR_RECIPE.get()).stream().max(Comparator.comparingInt((MortarRecipe recipe) -> {
                int sum = 0;
                @NotNull NonNullList<Ingredient> ingredients = recipe.getIngredients();
                int subtract = ingredients.size() - key.right().size();
                for (int j = 0; j < ingredients.size() - subtract; j++) {
                    int i = ingredients.contains(key.right().get(j)) ? 1 : -1;
                    sum += i;
                }
                return sum;
            })).orElseThrow(() -> new Exception("couldn't find recipe with items: " + key.right()));
        }
    }
}