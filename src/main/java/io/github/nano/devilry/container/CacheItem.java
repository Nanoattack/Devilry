package io.github.nano.devilry.container;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface CacheItem {
    ItemStack getItemStack();
    boolean equals(Object other);
    Item item();
}
