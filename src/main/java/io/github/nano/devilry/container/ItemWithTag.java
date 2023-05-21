package io.github.nano.devilry.container;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;

import java.util.Objects;

public record ItemWithTag(Item item, CompoundTag tag) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemWithTag that = (ItemWithTag) o;
        return item.equals(that.item) && tag != null ? tag.equals(that.tag) : tag == null && that.tag == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, tag);
    }
}
