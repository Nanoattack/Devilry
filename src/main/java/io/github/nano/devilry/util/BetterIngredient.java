package io.github.nano.devilry.util;

import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class BetterIngredient {
    private final @NotNull Ingredient ingredient;

    protected BetterIngredient(@NotNull Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(ingredient.getItems());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        return Arrays.equals(ingredient.getItems(), ((BetterIngredient) o).ingredient.getItems());
    }
}
