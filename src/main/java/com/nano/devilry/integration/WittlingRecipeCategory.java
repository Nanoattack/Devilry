package com.nano.devilry.integration;

import com.nano.devilry.ModMain;
import com.nano.devilry.block.ModBlocks;
import com.nano.devilry.data.recipes.Mortar.MortarRecipe;
import com.nano.devilry.data.recipes.Wittling.WittlingRecipe;
import com.nano.devilry.item.ModItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class WittlingRecipeCategory implements IRecipeCategory<WittlingRecipe>

{
    public final static ResourceLocation UID = new ResourceLocation(ModMain.MOD_ID, "wittling");
    public final static ResourceLocation TEXTURE = new ResourceLocation(ModMain.MOD_ID, "textures/gui/wittling_gui_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public WittlingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 114, 86);
        this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.WITTLING_TABLE.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends WittlingRecipe> getRecipeClass() {
        return WittlingRecipe.class;
    }

    @Override
    public Component getTitle() {
        return ModBlocks.WITTLING_TABLE.get().getName();
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
    public void setIngredients(WittlingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WittlingRecipe recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 13,32);
        recipeLayout.getItemStacks().init(1, true, 45,14);
        recipeLayout.getItemStacks().init(2, true, 63,14);
        recipeLayout.getItemStacks().init(3, true, 81,14);
        recipeLayout.getItemStacks().init(4, true, 45,32);
        recipeLayout.getItemStacks().init(5, true, 63,32);
        recipeLayout.getItemStacks().init(6, true, 81,32);
        recipeLayout.getItemStacks().init(7, true, 45,50);
        recipeLayout.getItemStacks().init(8, true, 63,50);
        recipeLayout.getItemStacks().init(9, true, 81,50);
        recipeLayout.getItemStacks().init(10, false, 139,32);
        recipeLayout.getItemStacks().set(ingredients);
        recipeLayout.setShapeless();
    }
}
