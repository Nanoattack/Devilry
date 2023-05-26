package io.github.nano.devilry.container;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.github.nano.devilry.data.recipes.HashedRecipe;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.booleans.BooleanObjectPair;
import net.minecraft.world.Container;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public abstract class RecipeCache<T extends HashedRecipe<Container>> extends CacheLoader<List<? extends CacheItem>, List<T>> {
    public abstract Level getLevel();

    public abstract AtomicReference<LoadingCache<List<? extends CacheItem>, List<T>>> getCache();
    public abstract RecipeType<T> getRecipeType();

    @Override
    public @NotNull List<T> load(@NotNull List<? extends CacheItem> key) throws Exception {
        if (getLevel() == null) throw new Exception("level is null, perhaps retrieving values too earlY?");
        return getPossibleRecipes(key);
    }

    @Override
    public @NotNull Map<List<? extends CacheItem>, List<T>> loadAll(@NotNull Iterable<? extends List<? extends CacheItem>> keys) throws Exception {
        if (getLevel() == null) throw new Exception("level is null, perhaps retrieving values too early?");
        Map<List<? extends CacheItem>, List<T>> toReturn = new HashMap<>();
        for (List<? extends CacheItem> key : keys) {
            toReturn.put(key, getPossibleRecipes(key));
        }
        return toReturn;
    }

    public List<T> getPossibleRecipes(List<? extends CacheItem> items) {
        Map<List<? extends CacheItem>, List<BooleanObjectPair<T>>> cacheMap = new HashMap<>();

        for (Map.Entry<List<? extends CacheItem>, List<T>> listListEntry : getCache().get().asMap().entrySet()) {
            cacheMap.put(listListEntry.getKey(), listListEntry.getValue().stream().map(entry -> BooleanObjectPair.of(entry.isShaped(), entry)).toList());
        }

        Map<List<? extends CacheItem>, List<BooleanObjectPair<T>>> singleDifferenceCacheMap = new HashMap<>();

        for (Map.Entry<List<? extends CacheItem>, List<BooleanObjectPair<T>>> listListEntry : cacheMap.entrySet()) {
            if (getDifference(listListEntry.getKey(), items) <= 1) {
                singleDifferenceCacheMap.put(listListEntry.getKey(), listListEntry.getValue());
            }
        }

        if (singleDifferenceCacheMap.isEmpty()) {
            singleDifferenceCacheMap.put(items, getLevel().getRecipeManager().getAllRecipesFor(getRecipeType()).stream()
                    .map(entry -> BooleanObjectPair.of(entry.isShaped(), entry)).toList());
        }


        return singleDifferenceCacheMap.entrySet().stream()
                .map(entry -> filter(entry, items))
                .distinct().flatMap(entry -> entry.getValue().stream())
                .sorted(Comparator.<BooleanObjectPair<T>>comparingInt(recipe -> recipe.right().toInt(items, recipe.leftBoolean()))
                        .thenComparingDouble(recipe -> recipe.leftBoolean() ? 1.01d : 1.0d).reversed())
                .map(Pair::right)
                .distinct()
                .toList();
    }

    private Map.Entry<List<? extends CacheItem>, List<BooleanObjectPair<T>>> filter(Map.Entry<List<? extends CacheItem>, List<BooleanObjectPair<T>>> cacheEntry, List<? extends CacheItem> items) {
        List<BooleanObjectPair<T>> toReturn = new ArrayList<>();
        for (BooleanObjectPair<T> pair : cacheEntry.getValue()) {
            if (pair.right().isPossible(items, pair.leftBoolean())) {
                toReturn.add(pair);
            }
        }
        return Map.entry(cacheEntry.getKey(),toReturn);
    }

    private int getDifference(List<? extends CacheItem> cacheItems, List<? extends CacheItem> newItems) {
        var list = new ArrayList<>(newItems);
        List<? super CacheItem> noDupes = new ArrayList<>(cacheItems);
        list.removeIf(ingredient -> {
            for (int i = 0; i < noDupes.size(); i++) {
                CacheItem item = (CacheItem) noDupes.get(i);
                if (item.equals(ingredient)) {
                    noDupes.set(i, new MortarItem(Items.AIR));
                    return true;
                }
            }
            return false;
        });
        return list.size();
    }
}
