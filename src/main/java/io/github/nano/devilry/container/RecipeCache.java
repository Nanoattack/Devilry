package io.github.nano.devilry.container;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.github.nano.devilry.data.recipes.HashedRecipe;
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
        Map<List<? extends CacheItem>, List<T>> singleDifferenceCacheMap = new HashMap<>();

        for (Map.Entry<List<? extends CacheItem>, List<T>> listListEntry : getCache().get().asMap().entrySet()) {
            if (getDifference(listListEntry.getKey(), items) <= 1) {
                singleDifferenceCacheMap.put(listListEntry.getKey(), listListEntry.getValue());
            }
        }

        if (singleDifferenceCacheMap.isEmpty()) {
            singleDifferenceCacheMap.put(items, getLevel().getRecipeManager().getAllRecipesFor(getRecipeType()));
        }

        return singleDifferenceCacheMap.entrySet().stream()
                .map(entry -> filter(entry, items))
                .distinct()
                .flatMap(entry -> entry.getValue().stream())
                .sorted(Comparator
                        .<T>comparingInt(recipe -> recipe.toInt(items, recipe.isShaped()))
                        .thenComparing(HashedRecipe::isShaped)
                        .reversed())
                .distinct()
                .toList();
    }

    private Map.Entry<List<? extends CacheItem>, List<T>> filter(Map.Entry<List<? extends CacheItem>, List<T>> cacheEntry, List<? extends CacheItem> items) {
       return Map.entry(cacheEntry.getKey(), cacheEntry.getValue().stream()
               .filter(recipe -> recipe.isPossible(items, recipe.isShaped())).toList());
    }

    private int getDifference(List<? extends CacheItem> cacheItems, List<? extends CacheItem> newItems) {
        List<? super CacheItem> noDupes = new ArrayList<>(newItems);
        cacheItems.forEach(ingredient -> {
                    for (int i = 0; i < noDupes.size(); i++) {
                        CacheItem item = (CacheItem) noDupes.get(i);
                        if (item.equals(ingredient)) {
                            noDupes.set(i, new MortarItem(Items.AIR));
                            break;
                        } else {
                            noDupes.set(i, new MortarItem(Items.STONE));
                        }
                    }
                });
        
        return ((int) noDupes.stream().filter(item -> !((CacheItem) item).getItemStack().isEmpty()).count());
    }
}
