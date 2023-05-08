//package io.github.nano.devilry.devilry.integration;
//
//import io.github.nano.devilry.devilry.block.ModBlocks;
//import io.github.nano.devilry.devilry.ModMain;
//import io.github.nano.devilry.devilry.data.recipes.Wittling.WittlingRecipe;
//import mezz.jei.api.constants.VanillaTypes;
//import mezz.jei.api.gui.IRecipeLayout;
//import mezz.jei.api.gui.drawable.IDrawable;
//import mezz.jei.api.helpers.IGuiHelper;
//import mezz.jei.api.ingredients.IIngredients;
//import mezz.jei.api.recipe.category.IRecipeCategory;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//
//public class WittlingRecipeCategory implements IRecipeCategory<WittlingRecipe>
//
//{
//    public final static ResourceLocation UID = new ResourceLocation(ModMain.MOD_ID, "wittling");
//    public final static ResourceLocation TEXTURE = new ResourceLocation(ModMain.MOD_ID, "textures/gui/wittling_gui_jei.png");
//
//    private final IDrawable background;
//    private final IDrawable icon;
//
//    public WittlingRecipeCategory(IGuiHelper helper) {
//        this.background = helper.createDrawable(TEXTURE, 0, 0, 152, 54);
//        this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.WITTLING_TABLE.get()));
//    }
//
//    @Override
//    public ResourceLocation getUid() {
//        return UID;
//    }
//
//    @Override
//    public Class<? extends WittlingRecipe> getRecipeClass() {
//        return WittlingRecipe.class;
//    }
//
//    @Override
//    public Component getTitle() {
//        return ModBlocks.WITTLING_TABLE.get().getName();
//    }
//
//    @Override
//    public IDrawable getBackground() {
//        return this.background;
//    }
//
//    @Override
//    public IDrawable getIcon() {
//        return this.icon;
//    }
//
//    @Override
//    public void setIngredients(WittlingRecipe recipe, IIngredients ingredients) {
//        ingredients.setInputIngredients(recipe.getIngredients());
//        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
//    }
//
//    @Override
//    public void setRecipe(IRecipeLayout recipeLayout, WittlingRecipe recipe, IIngredients ingredients) {
//        recipeLayout.getItemStacks().init(0, true, 4,18);
//        recipeLayout.getItemStacks().init(1, true, 36,0);
//        recipeLayout.getItemStacks().init(2, true, 54,0);
//        recipeLayout.getItemStacks().init(3, true, 72,0);
//        recipeLayout.getItemStacks().init(4, true, 36,18);
//        recipeLayout.getItemStacks().init(5, true, 54,18);
//        recipeLayout.getItemStacks().init(6, true, 72,18);
//        recipeLayout.getItemStacks().init(7, true, 36,36);
//        recipeLayout.getItemStacks().init(8, true, 54,36);
//        recipeLayout.getItemStacks().init(9, true, 72,36);
//        recipeLayout.getItemStacks().init(10, false, 130,18);
//        recipeLayout.getItemStacks().set(ingredients);
//    }
//}
