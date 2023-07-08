package io.github.nano.devilry.container.cache;

import com.google.common.base.Objects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record basicItem(Item item) implements CacheItem{
    @Override
    public ItemStack getItemStack() {
        return new ItemStack(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        basicItem that = (basicItem) o;
        return item.equals(that.item);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(item);
    }
}
