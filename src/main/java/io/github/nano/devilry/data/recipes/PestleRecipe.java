package io.github.nano.devilry.data.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import io.github.nano.devilry.item.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PestleRecipe implements Recipe<BlockStateContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final List<BlockState> blockStates;
    private final double chance;

    public PestleRecipe(ResourceLocation id, ItemStack output, List<BlockState> blockStates, double chance) {
        this.id = id;
        this.output = output;
        this.blockStates = blockStates;
        this.chance = chance;
    }
    @Override
    public boolean matches(@NotNull BlockStateContainer inv, @NotNull Level level) {
        return blockStates.contains(inv.getBlockState());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull BlockStateContainer inv, @NotNull RegistryAccess registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width == 2 && height == 3;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registries) {
        return output.copy();
    }

    public double getChance() {
        return chance;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.create();
    }

    public ItemStack getIcon() {
        return new ItemStack(ModItems.PESTLE.get());
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
       return ModRecipeTypes.PESTLE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.PESTLE_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<PestleRecipe> {
        @Override
        public @NotNull PestleRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
            double chance = GsonHelper.getAsDouble(json, "chance");
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            var inputs = GsonHelper.getAsJsonArray(json, "blockStates");
            List<BlockState> list = new ArrayList<>();
            for (JsonElement input : inputs) {
               BlockState.CODEC.decode(new Dynamic<>(JsonOps.INSTANCE, input)).result().ifPresent(action -> list.add(action.getFirst()));
            }

            return new PestleRecipe(recipeId, output, list, chance);
        }

        @Nullable
        @Override
        public PestleRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            double chance = pBuffer.readDouble();
            ItemStack output = pBuffer.readItem();
            List<BlockState> list = pBuffer.readList(r -> pBuffer.readJsonWithCodec(BlockState.CODEC));
            return new PestleRecipe(pRecipeId, output, list, chance);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, PestleRecipe pRecipe) {
            pBuffer.writeDouble(pRecipe.getChance());
            pBuffer.writeItemStack(pRecipe.output, false);
            pBuffer.writeCollection(pRecipe.blockStates, (buff, bs) -> buff.writeJsonWithCodec(BlockState.CODEC, bs));
        }
    }
}
