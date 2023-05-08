//package io.github.nano.devilry.devilry.integration;
//
//import io.github.nano.devilry.devilry.ModMain;
//import io.github.nano.devilry.devilry.block.ModBlocks;
//import io.github.nano.devilry.devilry.data.recipes.Altar.AltarRecipe;
//import io.github.nano.devilry.devilry.item.ModItems;
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
//public class AltarRecipeCategory implements IRecipeCategory<AltarRecipe>
//
//{
//    public final static ResourceLocation UID = new ResourceLocation(ModMain.MOD_ID, "summoning");
//    public final static ResourceLocation TEXTURE = new ResourceLocation(ModMain.MOD_ID, "textures/gui/demon_altar_jei.png");
//
//    private final IDrawable background;
//    private final IDrawable icon;
//
//    public AltarRecipeCategory(IGuiHelper helper) {
//        this.background = helper.createDrawable(TEXTURE, 0, 0, 114, 86);
//        this.icon = helper.createDrawableIngredient(new ItemStack(ModItems.DEMON_ALTAR.get()));
//    }
//
//    @Override
//    public ResourceLocation getUid() {
//        return UID;
//    }
//
//    @Override
//    public Class<? extends AltarRecipe> getRecipeClass() {
//        return AltarRecipe.class;
//    }
//
//    @Override
//    public Component getTitle() {
//        return ModBlocks.DEMON_ALTAR.get().getName();
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
//    public void setIngredients(AltarRecipe recipe, IIngredients ingredients) {
//        ingredients.setInputIngredients(recipe.getIngredients());
//        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
//    }
//
//    @Override
//    public void setRecipe(IRecipeLayout recipeLayout, AltarRecipe recipe, IIngredients ingredients) {
//        recipeLayout.getItemStacks().init(0, true, 49,9);
//        recipeLayout.getItemStacks().init(1, true, 16,9);
//        recipeLayout.getItemStacks().init(2, true, 3,31);
//        recipeLayout.getItemStacks().init(3, true, 16,53);
//        recipeLayout.getItemStacks().init(4, true, 80,9);
//        recipeLayout.getItemStacks().init(5, true, 93,31);
//        recipeLayout.getItemStacks().init(6, true, 80,53);
//        recipeLayout.getItemStacks().init(7, false, 48,45);
//        recipeLayout.getItemStacks().set(ingredients);
//        recipeLayout.setShapeless();
//    }
//}
