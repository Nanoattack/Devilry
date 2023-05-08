package io.github.nano.devilry.devilry.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.nano.devilry.devilry.block.ModBlocks;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class MortarRecipe implements IMortarRecipe

{
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public MortarRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems)
    {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }
        @Override
    public boolean matches(SimpleContainer inv, Level plevel) {
                if(recipeItems.get(0).test(inv.getItem(0)) &&
                recipeItems.get(2).test(inv.getItem(2)) &&
                recipeItems.get(3).test(inv.getItem(3)) &&
                recipeItems.get(4).test(inv.getItem(4)) &&
                recipeItems.get(5).test(inv.getItem(5)) &&
                recipeItems.get(6).test(inv.getItem(6)) &&
                recipeItems.get(7).test(inv.getItem(7)))
        {
            return true;
        }
        return false;

    }

    @Override
    public ItemStack assemble(SimpleContainer p_44001_) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.MORTAR.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
       return ModRecipeTypes.MORTAR_SERIALIZER.get();
    }

    public static class MortarRecipeType implements RecipeType<MortarRecipe> {
        @Override
        public String toString() {
            return MortarRecipe.TYPE_ID.toString();
        }
    }


    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>>
            implements RecipeSerializer<MortarRecipe> {
        
        @Override
        public MortarRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(8, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new MortarRecipe(recipeId, output,
                    inputs);
        }
        @Nullable
        @Override
        public MortarRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new MortarRecipe(pRecipeId, output,
                    inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, MortarRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());
            for (Ingredient ing : pRecipe.getIngredients()) {
                ing.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(), false);
        }
    }
}
