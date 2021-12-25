package com.nano.devilry.data.recipes.Wittling;

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

public class WittlingRecipe implements IWittlingRecipe

{
    private int amount;
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    protected static final List<Boolean> itemMatchesSlot = new ArrayList<>();

    public WittlingRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, Integer amount) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.amount = amount;
            }
    @Override
    public boolean matches(SimpleContainer inv, Level plevel) {
        if(recipeItems.get(0).test(inv.getItem(0) )||
                recipeItems.get(0).test(inv.getItem(1)) ||
                    recipeItems.get(0).test(inv.getItem(2)) ||
                          recipeItems.get(0).test(inv.getItem(3)) ||
                             recipeItems.get(0).test(inv.getItem(4)) ||
                                  recipeItems.get(0).test(inv.getItem(5)) ||
                                       recipeItems.get(0).test(inv.getItem(6)) ||
                                           recipeItems.get(0).test(inv.getItem(7)) ||
                                                 recipeItems.get(0).test(inv.getItem(8)) ||
                                                      recipeItems.get(0).test(inv.getItem(9))
        )

            return true;

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

    public Integer getAmount() {
        return amount;
    }

    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.WITTLING_TABLE.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.WITTLING_SERIALIZER.get();
    }

    public static class WittlingRecipeType implements RecipeType<WittlingRecipe> {
        @Override
        public String toString() {
            return WittlingRecipe.TYPE_ID.toString();
        }
    }


    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>>
            implements RecipeSerializer<WittlingRecipe> {

        @Override
        public WittlingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            Integer amount = GsonHelper.getAsInt(json, "amount");

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(8, Ingredient.EMPTY);

            for (int i = 0; i < Math.min(inputs.size(), ingredients.size()); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new WittlingRecipe(recipeId, output,
                    inputs, amount);
        }
        @Nullable
        @Override
        public WittlingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new WittlingRecipe(pRecipeId, output,
                    inputs, null);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, WittlingRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());
            for (Ingredient ing : pRecipe.getIngredients()) {
                ing.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(), false);
            pBuffer.writeInt(pRecipe.amount);
        }
    }
}