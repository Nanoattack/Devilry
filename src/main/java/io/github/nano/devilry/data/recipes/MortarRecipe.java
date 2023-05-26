package io.github.nano.devilry.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.nano.devilry.block.ModBlocks;
import io.github.nano.devilry.container.CacheItem;
import io.github.nano.devilry.util.Utils;
import io.github.nano.devilry.util.tags.DevilryTags;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
//fixme
//todo

public class MortarRecipe extends HashedRecipe<Container> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final boolean isShaped;
    private final int durabilityCost;
    private final int neededCrushes;

    public MortarRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, boolean isShaped, int durabilityCost, int crushes) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.isShaped = isShaped;
        this.durabilityCost = durabilityCost;
        this.neededCrushes = crushes;
    }
    @Override
    public boolean matches(@NotNull Container inv, @NotNull Level level) {
        return recipeItems.stream().allMatch(ingredient -> {
            int slot = recipeItems.indexOf(ingredient) + 1;
            for (int i = isShaped() ? slot : 1; i <= (isShaped() ? slot : 6); i++) {
                if ((ingredient.isEmpty() || ingredient.test(inv.getItem(i))) && inv.getItem(0).is(DevilryTags.Items.PESTLE_IN_MORTAR)) {
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Container inv, @NotNull RegistryAccess registries) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width == 2 && height == 3;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registries) {
        return output.copy();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.MORTAR.get());
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
       return ModRecipeTypes.MORTAR_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.MORTAR_RECIPE.get();
    }

    public boolean isShaped() {
        return isShaped;
    }

    public int getDurabilityCost() {
        return durabilityCost;
    }

    public int getNeededCrushes() {
        return neededCrushes;
    }

    @Override
    public boolean isPossible(List<? extends CacheItem> itemStackList, boolean isShaped) {
        if (isShaped) {
            for (int i = 0; i < itemStackList.size(); i++) {
                if (itemStackList.get(i).getItemStack().isEmpty()) {
                    continue;
                }
                if (!this.recipeItems.get(i).test(itemStackList.get(i).getItemStack())) {
                    return false;
                }
            }
        } else {
            for (CacheItem itemStack : itemStackList) {
                if (itemStack.getItemStack().isEmpty()) {
                    continue;
                }
                if (this.recipeItems.stream().noneMatch(ingredient -> ingredient.test(itemStack.getItemStack()))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int toInt(List<? extends CacheItem> itemStackList, boolean isShaped) {
        int j = 0;
        if (isShaped) {
            for (int i = 0; i < itemStackList.size(); i++) {
                if (itemStackList.get(i).getItemStack().isEmpty()) {
                    continue;
                }
                if (this.recipeItems.get(i).test(itemStackList.get(i).getItemStack())) {
                    j++;
                }
            }
        } else {
            for (CacheItem itemStack : itemStackList) {
                if (itemStack.getItemStack().isEmpty()) {
                    continue;
                }
                if (this.recipeItems.stream().anyMatch(ingredient -> ingredient.test(itemStack.getItemStack()))) {
                    j++;
                }
            }
        }
        return j;
    }

    public static class Serializer implements RecipeSerializer<MortarRecipe> {
        
        @Override
        public @NotNull MortarRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
            boolean isShaped = GsonHelper.getAsBoolean(json, "isShaped", false);
            Ingredient topLeft, centerLeft, bottomLeft, topRight, centerRight, bottomRight;
            if (isShaped) {
                topLeft = Utils.useNonNullOrElse(Ingredient::fromJson, getJsonElement(json, "topLeft"), Ingredient.EMPTY);
                centerLeft = Utils.useNonNullOrElse(Ingredient::fromJson, getJsonElement(json, "centerLeft"), Ingredient.EMPTY);
                bottomLeft = Utils.useNonNullOrElse(Ingredient::fromJson, getJsonElement(json, "bottomLeft"), Ingredient.EMPTY);
                topRight = Utils.useNonNullOrElse(Ingredient::fromJson, getJsonElement(json, "topRight"), Ingredient.EMPTY);
                centerRight = Utils.useNonNullOrElse(Ingredient::fromJson, getJsonElement(json, "centerRight"), Ingredient.EMPTY);
                bottomRight = Utils.useNonNullOrElse(Ingredient::fromJson, getJsonElement(json, "bottomRight"), Ingredient.EMPTY);
            } else {
                JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
                topLeft = Utils.useNonNullOrElse(Ingredient::fromJson, ingredients.get(0), Ingredient.EMPTY);
                centerLeft = Utils.useNonNullOrElse(Ingredient::fromJson, ingredients.get(1), Ingredient.EMPTY);
                bottomLeft = Utils.useNonNullOrElse(Ingredient::fromJson, ingredients.get(2), Ingredient.EMPTY);
                topRight = Utils.useNonNullOrElse(Ingredient::fromJson, ingredients.get(3), Ingredient.EMPTY);
                centerRight = Utils.useNonNullOrElse(Ingredient::fromJson, ingredients.get(4), Ingredient.EMPTY);
                bottomRight = Utils.useNonNullOrElse(Ingredient::fromJson, ingredients.get(5), Ingredient.EMPTY);
            }
            NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, topLeft, centerLeft, bottomLeft, topRight, centerRight, bottomRight);
            int durabilityCost = GsonHelper.getAsInt(json, "durabilityCost", 1);
            int crushes = GsonHelper.getAsInt(json, "crushes", 4);
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            return new MortarRecipe(recipeId, output, inputs, isShaped, durabilityCost, crushes);
        }

        private static JsonElement getJsonElement(JsonObject json, String name) {
            return GsonHelper.isArrayNode(json, name) ? GsonHelper.getAsJsonArray(json, name, null) : GsonHelper.getAsJsonObject(json, name, null);
        }

        @Nullable
        @Override
        public MortarRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            boolean isShaped = pBuffer.readBoolean();

            NonNullList<Ingredient> inputs = pBuffer.readCollection(NonNullList::createWithCapacity, buf ->
                    Ingredient.of(buf.readCollection(NonNullList::createWithCapacity, FriendlyByteBuf::readItem).stream()));

            int durabilityCost = pBuffer.readInt();
            int crushes = pBuffer.readInt();

            ItemStack output = pBuffer.readItem();

            return new MortarRecipe(pRecipeId, output, inputs, isShaped, durabilityCost, crushes);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, MortarRecipe pRecipe) {
            pBuffer.writeBoolean(pRecipe.isShaped());
            pBuffer.writeCollection(pRecipe.getIngredients(), (buf, ingredient) ->
                    buf.writeCollection(Arrays.asList(ingredient.getItems()), FriendlyByteBuf::writeItem));
            pBuffer.writeInt(pRecipe.getDurabilityCost());
            pBuffer.writeInt(pRecipe.getNeededCrushes());
            pBuffer.writeItem(pRecipe.output);
        }
    }
}
