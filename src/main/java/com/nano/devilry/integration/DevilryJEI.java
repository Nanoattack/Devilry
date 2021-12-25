package com.nano.devilry.integration;

import com.nano.devilry.ModMain;
import com.nano.devilry.block.ModBlocks;
import com.nano.devilry.container.MortarContainer;
import com.nano.devilry.container.WittlingContainer;
import com.nano.devilry.data.recipes.ModRecipeTypes;
import com.nano.devilry.data.recipes.Mortar.MortarRecipe;
import com.nano.devilry.data.recipes.Wittling.WittlingRecipe;
import com.nano.devilry.item.ModItems;
import com.nano.devilry.screen.MortarScreen;
import com.nano.devilry.screen.WittlingScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Objects;
import java.util.stream.Collectors;

@JeiPlugin
public class DevilryJEI implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ModMain.MOD_ID, "jei_plugin");
    }

    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new MortarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(
                new WittlingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(rm.getAllRecipesFor(ModRecipeTypes.MORTAR_RECIPE).stream()
                        .filter(r -> r instanceof MortarRecipe).collect(Collectors.toList()),
                MortarRecipeCategory.UID);
        registration.addRecipes(rm.getAllRecipesFor(ModRecipeTypes.WITTLING_RECIPE).stream()
                        .filter(r -> r instanceof WittlingRecipe).collect(Collectors.toList()),
                MortarRecipeCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        IModPlugin.super.registerRecipeCatalysts(registration);

        ItemStack stack = new ItemStack(ModItems.MORTAR.get(), 1);
        registration.addRecipeCatalyst(stack, MortarRecipeCategory.UID);

        ItemStack wittlingStack = new ItemStack(ModBlocks.WITTLING_TABLE.get(), 1);
        registration.addRecipeCatalyst(wittlingStack, WittlingRecipeCategory.UID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(MortarScreen.class, 81, 28, 14, 15, MortarRecipeCategory.UID);
        registration.addRecipeClickArea(WittlingScreen.class, 109, 37, 130, 51,WittlingRecipeCategory.UID);
    }

 /*   @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(MortarContainer.class, MortarRecipeCategory.UID, 1, 6, 8, 36);
        registration.addRecipeTransferHandler(WittlingContainer.class, WittlingRecipeCategory.UID, 1, 6, 8, 36);
    }
*/}