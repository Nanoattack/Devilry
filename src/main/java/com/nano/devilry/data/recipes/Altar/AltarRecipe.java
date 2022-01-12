package com.nano.devilry.data.recipes.Altar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nano.devilry.block.ModBlocks;
import com.nano.devilry.data.recipes.ModRecipeTypes;
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
import java.util.ArrayList;
import java.util.List;

public class AltarRecipe implements IAltarRecipe

{
    private int amount;
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    protected static final List<Boolean> itemMatchesSlot = new ArrayList<>();

    public AltarRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, Integer amount) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.amount = amount;

        for (int i = 0; i < 6; i++) {
            itemMatchesSlot.add(false);
        }
    }
    @Override
    public boolean matches(SimpleContainer inv, Level plevel) {

        for(int i = 0; i < 6; i++)
            itemMatchesSlot.set(i+1, false);

        // the flag is to break out early in case nothing matches for that slot
        boolean flag = false;

        // cycle through each recipe slot
        for(int j = 0; j < 6; j++) {
            //cycle through each slot for each recipe slot
            for (int i = 0; i < 6; i++) {
                //if the pestle is present
                if(recipeItems.get(0).test(inv.getItem(0))) {
                    //if the recipe matches a slot
                    if (recipeItems.get(j + 1).test(inv.getItem(i + 1))) {
                        // if the slot is not taken up
                        if (!itemMatchesSlot.get(i + 1)) {
                            //mark the slot as taken up
                            itemMatchesSlot.set(i + 1, true);
                            flag = true;
                            break;
                        }
                    }
                } else
                    return false;
            }
            //this is where it breaks out early to stop the craft
            if(!flag)
                break;
            //reset the flag for the next iteration
            flag = false;
        }
        // checks if a slot is not taken up, if its not taken up then itll not craft
        for(int i = 0; i < 6; i++) {
            if (!itemMatchesSlot.get(i+1))
                return false;
        }
        //if it reaches here that means it has completed the shapeless craft and should craft it
        return true;
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

    public Integer getAmount() {
        return amount;
    }

    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.DEMON_ALTAR.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.ALTAR_SERIALIZER.get();
    }

    public static class AltarRecipeType implements RecipeType<AltarRecipe> {
        @Override
        public String toString() {
            return AltarRecipe.TYPE_ID.toString();
        }
    }


    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>>
            implements RecipeSerializer<AltarRecipe> {

        @Override
        public AltarRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            Integer amount = GsonHelper.getAsInt(json, "amount");

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(8, Ingredient.EMPTY);

            for (int i = 0; i < Math.min(inputs.size(), ingredients.size()); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new AltarRecipe(recipeId, output,
                    inputs, amount);
        }
        @Nullable
        @Override
        public AltarRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new AltarRecipe(pRecipeId, output,
                    inputs, null);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, AltarRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());
            for (Ingredient ing : pRecipe.getIngredients()) {
                ing.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(), false);
            pBuffer.writeInt(pRecipe.amount);
        }
    }
}