package io.github.nano.devilry.container;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.datafixers.util.Pair;
import io.github.nano.devilry.data.recipes.ModRecipeTypes;
import io.github.nano.devilry.data.recipes.MortarRecipe;
import io.github.nano.devilry.util.BetterIngredient;
import io.github.nano.devilry.util.Utils;
import it.unimi.dsi.fastutil.booleans.BooleanObjectPair;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BetterIngredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * shapeless is right shaped is left.
 */
public abstract class RecipeCache<C extends Container, T extends Recipe<C>> extends CacheLoader<NonNullList<BetterIngredient>, Pair<List<T>, List<T>>> implements LoadingCache<NonNullList<BetterIngredient>, Pair<List<T>, List<T>>>{
    public abstract Level getLevel();

    @Override
    public @NotNull Pair<List<T>, List<T>> load(@NotNull NonNullList<BetterIngredient> key) throws Exception {
        if (getLevel() == null) throw new Exception("level is null, perhaps retrieving values too earlY?");
        return getRecipe(key);
    }

    @Override
    public @NotNull Map<NonNullList<BetterIngredient>, Pair<List<T>, List<T>>> loadAll(@NotNull Iterable<? extends NonNullList<BetterIngredient>> keys) throws Exception {
        if (getLevel() == null) throw new Exception("level is null, perhaps retrieving values too early?");
        Map<BooleanObjectPair<NonNullList<BetterIngredient>>, MortarRecipe> toReturn = new HashMap<>();
        for (BooleanObjectPair<NonNullList<BetterIngredient>> key : keys) {
            toReturn.put(key, getRecipe(key));
        }
        return toReturn;
    }

    private MortarRecipe getRecipe(BooleanObjectPair<NonNullList<BetterIngredient>> key) throws Exception {
        if (getLevel() != null) {
            getLevel().getProfiler().push("getRecipe");
        }
        if (key.firstBoolean()) {
            if (getLevel() != null) {
                getLevel().getProfiler().pop();
            }
            return Objects.requireNonNull(getLevel()).getRecipeManager().getAllRecipesFor(ModRecipeTypes.MORTAR_RECIPE.get()).stream().max(Comparator.comparingInt((MortarRecipe recipe) -> {
                int sum = 0;
                @NotNull NonNullList<BetterIngredient> BetterIngredients = recipe.getBetterIngredients();
                for (int j = 0; j < BetterIngredients.size(); j++) {
                    BetterIngredient BetterIngredient = BetterIngredients.get(j);
                    int i = Utils.BetterIngredientEquals(BetterIngredient, key.right().get(j)) ? 1 : -1;
                    sum += i;
                }
                return sum;
            })).orElseThrow(() -> new Exception("couldn't find recipe with items: " + key.right()));
        } else {
            if (getLevel() != null) {
                getLevel().getProfiler().pop();
            }
            return Objects.requireNonNull(getLevel()).getRecipeManager().getAllRecipesFor(ModRecipeTypes.MORTAR_RECIPE.get()).stream().max(Comparator.comparingInt((MortarRecipe recipe) -> {
                int sum = 0;
                @NotNull NonNullList<BetterIngredient> BetterIngredients = recipe.getBetterIngredients();
                int subtract = BetterIngredients.size() - key.right().size();
                for (int j = 0; j < BetterIngredients.size() - subtract; j++) {
                    int i = BetterIngredients.contains(key.right().get(j)) ? 1 : -1;
                    sum += i;
                }
                return sum;
            })).orElseThrow(() -> new Exception("couldn't find recipe with items: " + key.right()));
        }
    }

    private MortarRecipe getRecipes(BooleanObjectPair<NonNullList<BetterIngredient>> key) throws Exception {
        if (getLevel() != null) {
            getLevel().getProfiler().push("getRecipe");
        }
        if (key.firstBoolean()) {
            if (getLevel() != null) {
                getLevel().getProfiler().pop();
            }
            return Objects.requireNonNull(getLevel()).getRecipeManager().getAllRecipesFor(ModRecipeTypes.MORTAR_RECIPE.get()).stream().max(Comparator.comparingInt((MortarRecipe recipe) -> {
                int sum = 0;
                @NotNull NonNullList<BetterIngredient> BetterIngredients = recipe.getBetterIngredients();
                for (int j = 0; j < BetterIngredients.size(); j++) {
                    BetterIngredient BetterIngredient = BetterIngredients.get(j);
                    int i = Utils.BetterIngredientEquals(BetterIngredient, key.right().get(j)) ? 1 : -1;
                    sum += i;
                }
                return sum;
            })).orElseThrow(() -> new Exception("couldn't find recipe with items: " + key.right()));
        } else {
            if (getLevel() != null) {
                getLevel().getProfiler().pop();
            }
            return Objects.requireNonNull(getLevel()).getRecipeManager().getAllRecipesFor(ModRecipeTypes.MORTAR_RECIPE.get()).stream().max(Comparator.comparingInt((MortarRecipe recipe) -> {
                int sum = 0;
                @NotNull NonNullList<BetterIngredient> BetterIngredients = recipe.getBetterIngredients();
                int subtract = BetterIngredients.size() - key.right().size();
                for (int j = 0; j < BetterIngredients.size() - subtract; j++) {
                    int i = BetterIngredients.contains(key.right().get(j)) ? 1 : -1;
                    sum += i;
                }
                return sum;
            })).orElseThrow(() -> new Exception("couldn't find recipe with items: " + key.right()));
        }
    }

    public static <C extends Container, T extends Recipe<C>> List<T> getPossibleShapedRecipes(List<ItemStack> items) {
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
                                    copy.getKey().right().set(i, BetterIngredient.EMPTY);
                                }
                            }
                        }
                    }
                    return true;
                })
                .sorted(Comparator.comparingInt((Map.Entry<BooleanObjectPair<NonNullList<BetterIngredient>>, T> key) -> {
                    var copy = Map.Entry.copyOf(key);
                    if (copy.getKey().leftBoolean()) {
                        int sum = 0;
                        NonNullList<BetterIngredient> BetterIngredients = copy.getValue().getBetterIngredients();
                        for (int j = 0; j < BetterIngredients.size(); j++) {
                            BetterIngredient BetterIngredient = BetterIngredients.get(j);
                            int i = Utils.BetterIngredientEquals(BetterIngredient, copy.getKey().right().get(j)) ? 1 : -1;
                            sum += i;
                        }
                        return sum;
                    } else {
                        int sum = 0;
                        NonNullList<BetterIngredient> BetterIngredients = copy.getValue().getBetterIngredients();
                        int subtract = BetterIngredients.size() - copy.getKey().right().size();
                        for (int j = 0; j < BetterIngredients.size() - subtract; j++) {
                            int i;
                            if (BetterIngredients.contains(copy.getKey().right().get(j))) {
                                i = 1;
                                copy.getKey().right().set(j, BetterIngredient.EMPTY);
                            } else {
                                i = -1;
                            }
                            sum += i;
                        }
                        return sum;
                    }
                }).thenComparingDouble(e -> e.getKey().leftBoolean() ? 0.1f : 0).reversed()).map(Map.Entry::getValue).toList();
    }

    public List<T> getPossibleShapelessRecipes(NonNullList<BetterIngredient> items) {
        Map<NonNullList<BetterIngredient>, List<BetterIngredient>> differences = new HashMap<>();
        Map<Pair<List<T>, List<T>>, NonNullList<BetterIngredient>> lookup = new HashMap<>();
        Map<Pair<List<T>, List<T>>, NonNullList<BetterIngredient>> cache = new HashMap<>();

        for (Map.Entry<NonNullList<BetterIngredient>, Pair<List<T>, List<T>>> nonNullListPairEntry : this.asMap().entrySet()) {
            lookup.putIfAbsent(nonNullListPairEntry.getValue(), nonNullListPairEntry.getKey());
            cache.putIfAbsent(nonNullListPairEntry.getValue(), nonNullListPairEntry.getKey());
        }

        for (Map.Entry<Pair<List<T>, List<T>>, NonNullList<BetterIngredient>> nonNullListPairEntry : cache.entrySet()) {
            ArrayList<BetterIngredient> ingredients = new ArrayList<>(items);
            nonNullListPairEntry.getValue().removeIf(ingredient1 -> {
                for (BetterIngredient ingredient : ingredients) {
                    if (ingredient.equals(ingredient1)) {
                        return true;
                    }
                }
                return false;
            });
            differences.put(lookup.get(nonNullListPairEntry.getKey()), nonNullListPairEntry.getValue());
        }

        List<NonNullList<BetterIngredient>> similar = differences.entrySet().stream().map(map -> Map.entry(map.getKey(), map.getValue().size()))
                .filter(map -> map.getValue() <= 1).map(Map.Entry::getKey).toList();

        for (NonNullList<BetterIngredient> betterIngredients : similar) {

        }
    }

    private List<T> getPossibleShapelessRecipesFromCache() {
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
                                    copy.getKey().right().set(i, BetterIngredient.EMPTY);
                                }
                            }
                        }
                    }
                    return true;
                })
                .sorted(Comparator.comparingInt((Map.Entry<BooleanObjectPair<NonNullList<BetterIngredient>>, T> key) -> {
                    var copy = Map.Entry.copyOf(key);
                    if (copy.getKey().leftBoolean()) {
                        int sum = 0;
                        NonNullList<BetterIngredient> BetterIngredients = copy.getValue().getBetterIngredients();
                        for (int j = 0; j < BetterIngredients.size(); j++) {
                            BetterIngredient BetterIngredient = BetterIngredients.get(j);
                            int i = Utils.BetterIngredientEquals(BetterIngredient, copy.getKey().right().get(j)) ? 1 : -1;
                            sum += i;
                        }
                        return sum;
                    } else {
                        int sum = 0;
                        NonNullList<BetterIngredient> BetterIngredients = copy.getValue().getBetterIngredients();
                        int subtract = BetterIngredients.size() - copy.getKey().right().size();
                        for (int j = 0; j < BetterIngredients.size() - subtract; j++) {
                            int i;
                            if (BetterIngredients.contains(copy.getKey().right().get(j))) {
                                i = 1;
                                copy.getKey().right().set(j, BetterIngredient.EMPTY);
                            } else {
                                i = -1;
                            }
                            sum += i;
                        }
                        return sum;
                    }
                }).thenComparingDouble(e -> e.getKey().leftBoolean() ? 0.1f : 0).reversed()).map(Map.Entry::getValue).toList();
    }


}
