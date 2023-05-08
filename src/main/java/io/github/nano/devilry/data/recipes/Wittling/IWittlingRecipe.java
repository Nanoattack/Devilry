package io.github.nano.devilry.devilry.data.recipes.Wittling;

import io.github.nano.devilry.devilry.ModMain;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface IWittlingRecipe extends Recipe<SimpleContainer>
{
    ResourceLocation TYPE_ID = new ResourceLocation(ModMain.MOD_ID, "wittling");

    @Override
    default RecipeType<?> getType(){
        return Registry.RECIPE_TYPE.getOptional(TYPE_ID).get();
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    default boolean isSpecial(){
        return true;
    }
}
