package io.github.nano.devilry.data.recipes;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.item.custom.Pestle;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
//todo

public class ModRecipeTypes
{
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ModMain.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ModMain.MOD_ID);

    public static final RegistryObject<MortarRecipe.Serializer> MORTAR_SERIALIZER
            = RECIPE_SERIALIZER.register("grinding", MortarRecipe.Serializer::new);

    public static final RegistryObject<PestleRecipe.Serializer> PESTLE_SERIALIZER
            = RECIPE_SERIALIZER.register("crushing", PestleRecipe.Serializer::new);

    public static final RegistryObject<AltarRecipe.Serializer> DEMON_ALTAR_SERIALIZER
            = RECIPE_SERIALIZER.register("altar_crafting", AltarRecipe.Serializer::new);

    public static final RegistryObject<CarveRecipe.Serializer> CARVING_SERIALIZER
            = RECIPE_SERIALIZER.register("carving", CarveRecipe.Serializer::new);

    public static RegistryObject<RecipeType<MortarRecipe>> MORTAR_RECIPE =
            RECIPE_TYPE.register( "grinding", () -> createType("grinding"));

    public static RegistryObject<RecipeType<PestleRecipe>> PESTLE_RECIPE =
            RECIPE_TYPE.register("crushing", () -> createType("crushing"));

    public static RegistryObject<RecipeType<AltarRecipe>> DEMON_ALTAR_RECIPE =
            RECIPE_TYPE.register("altar_crafting", () -> createType("altar_crafting"));

    public static RegistryObject<RecipeType<CarveRecipe>> CARVING_RECIPE =
            RECIPE_TYPE.register("carving", () -> createType("carving"));

    private static <T extends Recipe<?>> RecipeType<T> createType(String identifier){
        return new RecipeType<>() {
            @Override
            public String toString() {
                return ModMain.MOD_ID + ":" + identifier;
            }
        };
    }

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZER.register(eventBus);
        RECIPE_TYPE.register(eventBus);
    }
}
