package io.github.nano.devilry.util;

import com.google.common.cache.LoadingCache;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.booleans.BooleanObjectPair;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Utils {
    @NotNull
    public static <T, V> T useNonNullOrElse(Function<V, T> function, V value, T orElse) {
        return value == null ? orElse : function.apply(value);
    }

    public static <C extends Container, T extends Recipe<C>> List<T> getPossibleRecipes(LoadingCache<BooleanObjectPair<NonNullList<Ingredient>>, T> cache, List<ItemStack> items) {
        return cache.asMap().entrySet().stream()
                .filter(entry -> {
                    for (ItemStack item : items) {
                        if (!item.isEmpty()) {
                            break;
                        }
                        return true;
                    }
                    var copy = Map.Entry.copyOf(entry);
                    if (copy.getKey().firstBoolean()) {
                        for (int i = 0; i < items.size(); i++) {
                            if (!copy.getKey().right().get(i).test(items.get(i))) {
                                return false;
                            }
                        }
                    } else {
                        for (ItemStack item : items) {
                            for (int i = 0; i < copy.getKey().right().size(); i++) {
                                if (copy.getKey().right().get(i).test(item)) {
                                    copy.getKey().right().set(i, Ingredient.EMPTY);
                                }
                            }
                        }
                    }
                    return true;
                })
                .sorted(Comparator.comparingInt((Map.Entry<BooleanObjectPair<NonNullList<Ingredient>>, T> key) -> {
                    var copy = Map.Entry.copyOf(key);
                    if (copy.getKey().leftBoolean()) {
                        int sum = 0;
                        NonNullList<Ingredient> ingredients = copy.getValue().getIngredients();
                        for (int j = 0; j < ingredients.size(); j++) {
                            Ingredient ingredient = ingredients.get(j);
                            int i = Utils.ingredientEquals(ingredient, copy.getKey().right().get(j)) ? 1 : -1;
                            sum += i;
                        }
                        return sum;
                    } else {
                        int sum = 0;
                        NonNullList<Ingredient> ingredients = copy.getValue().getIngredients();
                        int subtract = ingredients.size() - copy.getKey().right().size();
                        for (int j = 0; j < ingredients.size() - subtract; j++) {
                            int i;
                            if (ingredients.contains(copy.getKey().right().get(j))) {
                                i = 1;
                                copy.getKey().right().set(j, Ingredient.EMPTY);
                            } else {
                                i = -1;
                            }
                            sum += i;
                        }
                        return sum;
                    }
                }).thenComparingDouble(e -> e.getKey().leftBoolean() ? 0.1f : 0).reversed()).map(Map.Entry::getValue).toList();
    }

    public static boolean ingredientEquals(Ingredient one, Ingredient other) {
        if (one == other) return true;
        return Arrays.equals(one.getItems(), other.getItems());
    }

    public static <T extends Recipe<Container>> boolean smartQuickMove(LoadingCache<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>> cache, ItemStack stack, boolean dumbQuickMove, AbstractContainerMenu menu, int recipesToCheck, Function<T, ? extends IntStream> mapper) throws ExecutionException {
        return moveOrdered(stack, cache.get(Pair.of(null, menu.getItems())).stream().limit(recipesToCheck).flatMapToInt(mapper).toArray(), dumbQuickMove, menu);
    }

    public static boolean moveOrdered(ItemStack pStack, int[] slots, boolean pReverseDirection, AbstractContainerMenu menu) {
        boolean flag = false;
        if (pReverseDirection) {
            ArrayUtils.reverse(slots);
        }
        for (int slot : slots) {
            if (slot != -1) {
                break;
            }
            return false;
        }
        if (pStack.isStackable()) {
            for (int slotIndex : slots) {
                Slot slot = menu.getSlot(slotIndex);
                ItemStack itemstack = slot.getItem();
                if (!itemstack.isEmpty() && ItemStack.isSameItemSameTags(pStack, itemstack)) {
                    int j = itemstack.getCount() + pStack.getCount();
                    int maxSize = Math.min(slot.getMaxStackSize(), pStack.getMaxStackSize());
                    if (j <= maxSize) {
                        pStack.setCount(0);
                        itemstack.setCount(j);
                        slot.setChanged();
                        flag = true;
                    } else if (itemstack.getCount() < maxSize) {
                        pStack.shrink(maxSize - itemstack.getCount());
                        itemstack.setCount(maxSize);
                        slot.setChanged();
                        flag = true;
                    }
                }
            }
        }
        if (!pStack.isEmpty()) {
            for (int slot : slots) {
                Slot slot1 = menu.getSlot(slot);
                ItemStack itemStack = slot1.getItem();
                if (itemStack.isEmpty() && slot1.mayPlace(pStack)) {
                    if (pStack.getCount() > slot1.getMaxStackSize()) {
                        slot1.setByPlayer(pStack.split(slot1.getMaxStackSize()));
                    } else {
                        slot1.setByPlayer(pStack.split(pStack.getCount()));
                    }

                    slot1.setChanged();
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
