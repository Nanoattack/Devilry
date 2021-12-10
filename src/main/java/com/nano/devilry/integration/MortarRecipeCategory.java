package com.nano.devilry.integration;

import com.nano.devilry.ModMain;
import com.nano.devilry.block.ModBlocks;
import com.nano.devilry.data.recipes.MortarRecipe;
import com.nano.devilry.item.ModItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class MortarRecipeCategory implements IRecipeCategory<MortarRecipe>

{
    public final static ResourceLocation UID = new ResourceLocation(ModMain.MOD_ID, "grinding");
    public final static ResourceLocation TEXTURE = new ResourceLocation(ModMain.MOD_ID, "textures/gui/mortar_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public MortarRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(new ItemStack(ModItems.PESTLE.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends MortarRecipe> getRecipeClass() {
        return MortarRecipe.class;
    }

    @Override
    public Component getTitle() {
        return ModBlocks.MORTAR.get().getName();
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(MortarRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MortarRecipe recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 80,10);
        recipeLayout.getItemStacks().init(1, false, 80,46);
        recipeLayout.getItemStacks().init(2, true, 48,10);
        recipeLayout.getItemStacks().init(3, true, 35,32);
        recipeLayout.getItemStacks().init(4, true, 48,54);
        recipeLayout.getItemStacks().init(5, true, 112,10);
        recipeLayout.getItemStacks().init(6, true, 125,32);
        recipeLayout.getItemStacks().init(7, true, 112,54);
        recipeLayout.getItemStacks().set(ingredients);
    }
}
