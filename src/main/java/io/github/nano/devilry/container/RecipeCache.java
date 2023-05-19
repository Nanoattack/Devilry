package io.github.nano.devilry.container;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Streams;
import com.mojang.datafixers.util.Pair;
import io.github.nano.devilry.util.BetterIngredient;
import it.unimi.dsi.fastutil.booleans.BooleanObjectPair;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * shapeless is right shaped is left.
 */
public abstract class RecipeCache<T extends Recipe<Container>> extends CacheLoader<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>> {
    public abstract Level getLevel();

    public abstract AtomicReference<LoadingCache<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>>> getCache();
    public abstract RecipeType<T> getRecipeType();

    @Override
    public @NotNull List<T> load(@NotNull Pair<NonNullList<BetterIngredient>, List<ItemStack>> key) throws Exception {
        if (getLevel() == null) throw new Exception("level is null, perhaps retrieving values too earlY?");
        return getPossibleRecipes(key);
    }

    @Override
    public @NotNull Map<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>> loadAll(@NotNull Iterable<? extends Pair<NonNullList<BetterIngredient>, List<ItemStack>>> keys) throws Exception {
        if (getLevel() == null) throw new Exception("level is null, perhaps retrieving values too early?");
        Map<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>> toReturn = new HashMap<>();
        for (Pair<NonNullList<BetterIngredient>, List<ItemStack>> key : keys) {
            toReturn.put(key, getPossibleRecipes(key));
        }
        return toReturn;
    }

    public List<T> getPossibleRecipes(Pair<NonNullList<BetterIngredient>, List<ItemStack>> items) {
        Map<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<ItemStack>> differencesShapeless = new HashMap<>();
        Map<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<ItemStack>> differencesShaped = new HashMap<>();
        Map<List<T>, Pair<NonNullList<BetterIngredient>, List<ItemStack>>> lookup = new HashMap<>();
        Map<List<T>, Pair<NonNullList<BetterIngredient>, List<ItemStack>>> cache = new HashMap<>();

        //flip the lists
        for (Map.Entry<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>> nonNullListPairEntry : getCache().get().asMap().entrySet()) {
            lookup.putIfAbsent(nonNullListPairEntry.getValue(), nonNullListPairEntry.getKey());
            cache.putIfAbsent(nonNullListPairEntry.getValue(), nonNullListPairEntry.getKey());
        }

        shapedDifference(items, differencesShaped, cache, lookup);
        shapelessDifference(items, differencesShapeless, cache, lookup);

        Map<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>> similarShaped = getSimilar(differencesShaped);
        Map<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>> similarShapeless = getSimilar(differencesShapeless);

        if (similarShaped.isEmpty()) {
            similarShaped.put(Pair.of(items.getFirst(), items.getSecond()), getLevel().getRecipeManager().getAllRecipesFor(getRecipeType()));
        }
        if (similarShapeless.isEmpty()) {
            similarShapeless.put(Pair.of(items.getFirst(), items.getSecond()), getLevel().getRecipeManager().getAllRecipesFor(getRecipeType()));
        }

        return Streams.concat(getPossibleShapedRecipesFromCache(similarShaped).map(entry -> BooleanObjectPair.of(true, entry)),
                        getPossibleShapelessRecipesFromCache(similarShapeless).map(entry -> BooleanObjectPair.of(false, entry)))
                .distinct()
                .flatMap(entry -> {
                    ArrayList<Map.Entry<List<ItemStack>, BooleanObjectPair<T>>> list = new ArrayList<>();
                    for (T t : entry.second().getValue()) {
                        list.add(Map.entry(entry.second().getKey().getSecond(), BooleanObjectPair.of(entry.leftBoolean(), t)));
                    }
                    return list.stream();
                })
                .sorted(Comparator.comparingInt(entry -> entry.getValue().firstBoolean() ? shapedComparator(entry) : shapelessComparator(entry)))
                .map(entry -> entry.getValue().right()).toList();
    }

    @NotNull
    private Map<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>> getSimilar(Map<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<ItemStack>> differencesShaped) {
        return differencesShaped.entrySet().stream()
                .map(map -> Map.entry(map.getKey(), map.getValue().size()))
                .filter(map -> map.getValue() <= 1).map(Map.Entry::getKey)
                .collect(Collectors.toMap(entry -> entry, entry -> getCache().get().asMap().get(entry) != null ? getCache().get().asMap().get(entry) : List.of()));
    }

    private void shapedDifference(Pair<NonNullList<BetterIngredient>, List<ItemStack>> items, Map<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<ItemStack>> differences, Map<List<T>, Pair<NonNullList<BetterIngredient>, List<ItemStack>>> cache, Map<List<T>, Pair<NonNullList<BetterIngredient>, List<ItemStack>>> lookup) {
        int i = 0;
        for (Map.Entry<List<T>, Pair<NonNullList<BetterIngredient>, List<ItemStack>>> nonNullListPairEntry : cache.entrySet()) {
            ArrayList<ItemStack> ingredients = new ArrayList<>(items.getSecond());
            int finalI = i;
            nonNullListPairEntry.getValue().getSecond().removeIf(ingredient1 -> ingredient1.is(ingredients.get(finalI).getItem()));
            differences.put(lookup.get(nonNullListPairEntry.getKey()), nonNullListPairEntry.getValue().getSecond());
            i++;
        }
    }

    private void shapelessDifference(Pair<NonNullList<BetterIngredient>, List<ItemStack>> items, Map<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<ItemStack>> differences, Map<List<T>, Pair<NonNullList<BetterIngredient>, List<ItemStack>>> cache, Map<List<T>, Pair<NonNullList<BetterIngredient>, List<ItemStack>>> lookup) {
        for (Map.Entry<List<T>, Pair<NonNullList<BetterIngredient>, List<ItemStack>>> nonNullListPairEntry : cache.entrySet()) {
            ArrayList<ItemStack> ingredients = new ArrayList<>(items.getSecond());
            nonNullListPairEntry.getValue().getSecond().removeIf(ingredient1 -> {
                for (ItemStack itemStack : ingredients) {
                    if (itemStack.is(ingredient1.getItem())) {
                        return true;
                    }
                }
                return false;
            });
            differences.put(lookup.get(nonNullListPairEntry.getKey()), nonNullListPairEntry.getValue().getSecond());
        }
    }


    private Stream<Map.Entry<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>>> getPossibleShapedRecipesFromCache(Map<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>> entries) {
        return entries.entrySet().stream()
                .filter(this::shapedPredicate);
    }

    private Stream<Map.Entry<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>>> getPossibleShapelessRecipesFromCache(Map<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>> entries) {
        return entries.entrySet().stream()
                .filter(this::shapelessPredicate);
    }

    private int shapedComparator(Map.Entry<List<ItemStack>, BooleanObjectPair<T>> entry) {
        var copy = Map.Entry.copyOf(entry);

        int sum = 0;
        NonNullList<Ingredient> ingredients = copy.getValue().right().getIngredients();

        List<ItemStack> toCompare = copy.getKey();

        for (int j = 0; j < ingredients.size(); j++) {
            Ingredient ingredient = ingredients.get(j);
            int i = ingredient.test(toCompare.get(j)) ? 1 : -1;
            sum += i;
        }
        return sum;
    }

    private int shapelessComparator(Map.Entry<List<ItemStack>, BooleanObjectPair<T>> entry) {
        var copy = Map.Entry.copyOf(entry);

        int sum = 0;
        NonNullList<Ingredient> ingredients = copy.getValue().right().getIngredients();

        List<ItemStack> toCompare = copy.getKey();

        for (int i = 0; i < toCompare.size(); i++) {
            int j;
            int finalI = i;
            if (ingredients.stream().anyMatch(ingredient -> ingredient.test(toCompare.get(finalI)))) {
                j = 1;
                ingredients.set(i, Ingredient.EMPTY);
            } else {
                j = -1;
            }
            sum += j;
        }

        return sum;
    }

    private boolean shapedPredicate(Map.Entry<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>> keys) {
        var items = keys.getKey().getSecond();
        NonNullList<BetterIngredient> ingredients = NonNullList.withSize(7, BetterIngredient.EMPTY);
        if (keys.getKey().getFirst() != null) {
            NonNullList<BetterIngredient> first = keys.getKey().getFirst();
            for (int i = 0; i < first.size(); i++) {
                BetterIngredient ingredient = first.get(i);
                ingredients.set(i, ingredient);
            }
        }
        //check if it's empty
        for (ItemStack item : items) {
            if (!item.isEmpty()) {
                break;
            }
            return true;
        }
        //check if all items are inside the recipe
        for (int i = 0; i < items.size(); i++) {
            if (!ingredients.get(i).getIngredient().test(items.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean shapelessPredicate(Map.Entry<Pair<NonNullList<BetterIngredient>, List<ItemStack>>, List<T>> keys) {
        var items = keys.getKey().getSecond();
        NonNullList<BetterIngredient> ingredients = NonNullList.withSize(7, BetterIngredient.EMPTY);
        if (keys.getKey().getFirst() != null) {
            NonNullList<BetterIngredient> first = keys.getKey().getFirst();
            for (int i = 0; i < first.size(); i++) {
                BetterIngredient ingredient = first.get(i);
                ingredients.set(i, ingredient);
            }
        }
        //check if it's empty
        for (ItemStack item : items) {
            if (!item.isEmpty()) {
                break;
            }
            return true;
        }
        //check if all items are inside the recipe
        for (ItemStack item : items) {
            for (int i = 0; i < ingredients.size(); i++) {
                if (ingredients.get(i).getIngredient().test(item)) {
                    ingredients.set(i, BetterIngredient.EMPTY);

                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
