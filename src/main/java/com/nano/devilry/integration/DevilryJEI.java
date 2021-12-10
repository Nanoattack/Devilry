package com.nano.devilry.integration;

import com.nano.devilry.ModMain;
import com.nano.devilry.data.recipes.ModRecipeTypes;
import com.nano.devilry.data.recipes.MortarRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Objects;
import java.util.stream.Collectors;

@JeiPlugin
public class DevilryJEI implements IModPlugin
{
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ModMain.MOD_ID, "jei_plugin");
    }

    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new MortarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(rm.getAllRecipesFor(ModRecipeTypes.MORTAR_RECIPE).stream()
                        .filter(r -> r instanceof MortarRecipe).collect(Collectors.toList()),
                MortarRecipeCategory.UID);
    }
}
