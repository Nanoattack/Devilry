package io.github.nano.devilry.util;

import com.google.common.cache.LoadingCache;
import io.github.nano.devilry.container.CacheItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Utils {
    @NotNull
    public static <T, V> T useNonNullOrElse(Function<V, T> function, V value, T orElse) {
        return value == null ? orElse : function.apply(value);
    }

    public static <T extends Recipe<Container>> boolean smartQuickMove(LoadingCache<List<? extends CacheItem>, List<T>> cache, ItemStack stack, boolean dumbQuickMove, AbstractContainerMenu menu, int recipesToCheck, Function<T, ? extends IntStream> mapper, Function<ItemStack, CacheItem> factory) throws ExecutionException {
        return moveOrdered(stack, cache.get(menu.getItems().stream().map(factory).toList().subList(37, 43)).stream().limit(recipesToCheck).flatMapToInt(mapper).toArray(), dumbQuickMove, menu);
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
                Slot slot = menu.slots.get(37 + slotIndex);
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
                Slot slot1 = menu.slots.get(37 + slot);
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
