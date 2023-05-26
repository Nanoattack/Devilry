package io.github.nano.devilry.data.recipes;

import io.github.nano.devilry.container.CacheItem;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;
import java.util.Objects;

public abstract class HashedRecipe<C extends Container> implements Recipe<C> {
    @Override
    public int hashCode() {
        return Objects.hashCode(this.getIngredients());
    }

    public abstract boolean isPossible(List<? extends CacheItem> itemStackList, boolean isShaped);

    public abstract int toInt(List<? extends CacheItem> itemStackList, boolean isShaped);
    public abstract boolean isShaped();
}
