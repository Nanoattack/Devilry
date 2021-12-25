package com.nano.devilry.data.recipes;

import com.nano.devilry.ModMain;
import com.nano.devilry.data.recipes.Mortar.MortarRecipe;
import com.nano.devilry.data.recipes.Wittling.WittlingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeTypes
{
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ModMain.MOD_ID);

    public static final RegistryObject<MortarRecipe.Serializer> MORTAR_SERIALIZER
            = RECIPE_SERIALIZER.register("grinding", MortarRecipe.Serializer::new);

    public static RecipeType<MortarRecipe> MORTAR_RECIPE
            = new MortarRecipe.MortarRecipeType();

    public static final RegistryObject<WittlingRecipe.Serializer> WITTLING_SERIALIZER
            = RECIPE_SERIALIZER.register("wittling", WittlingRecipe.Serializer::new);

    public static RecipeType<WittlingRecipe> WITTLING_RECIPE
            = new WittlingRecipe.WittlingRecipeType();

    public static void register(IEventBus eventBus)
    {
        RECIPE_SERIALIZER.register(eventBus);
        Registry.register(Registry.RECIPE_TYPE, MortarRecipe.TYPE_ID, MORTAR_RECIPE);
        Registry.register(Registry.RECIPE_TYPE, WittlingRecipe.TYPE_ID, WITTLING_RECIPE);
    }
}
