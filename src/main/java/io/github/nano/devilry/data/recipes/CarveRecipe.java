package io.github.nano.devilry.data.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import io.github.nano.devilry.data.recipes.utility.CarveContainer;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CarveRecipe implements Recipe<CarveContainer> {
    private final ResourceLocation id;
    private final Boolean[][] pattern;
    private final BlockState input;
    private final int durability;
    private final BlockState output;

    public CarveRecipe(ResourceLocation id, Boolean[][] pattern, BlockState input, int durability, BlockState output) {
        this.id = id;
        this.pattern = pattern;
        this.input = input;
        this.durability = durability;
        this.output = output;
    }

    @Override
    public boolean matches(CarveContainer pContainer, Level pLevel) {
        if (pattern.length == pContainer.getPattern().length) {
            for (int i = 0; i < pattern.length; i++) {
                for (int j = 0; j < pattern[i].length; j++) {
                    if (pattern[i][j] != pContainer.getPattern()[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ItemStack assemble(CarveContainer container, RegistryAccess registryAccess) {
        ItemStack holder = new ItemStack(Items.STONE);
        holder.getOrCreateTag().put("value", NbtUtils.writeBlockState(container.getBlockState()));
        return holder;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width == 7 && height == 7;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registries) {
        return new ItemStack(output.getBlock().asItem());
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.create();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
       return ModRecipeTypes.CARVING_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.CARVING_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<CarveRecipe> {
        @Override
        public @NotNull CarveRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
            Boolean[][] pattern;
            int durability;
            BlockState input, output;

            pattern = GsonHelper.getAsJsonArray(json, "pattern").asList().stream()
                    .map(element -> element.getAsJsonArray().asList().stream().map(JsonElement::getAsBoolean).toArray(Boolean[]::new))
                    .toArray(Boolean[][]::new);

            durability = GsonHelper.getAsInt(json, "durability");
            input = BlockState.CODEC.decode(new Dynamic<>(JsonOps.INSTANCE, GsonHelper.getNonNull(json, "input"))).result().orElseThrow().getFirst();
            output = BlockState.CODEC.decode(new Dynamic<>(JsonOps.INSTANCE, GsonHelper.getNonNull(json, "output"))).result().orElseThrow().getFirst();

            return new CarveRecipe(recipeId, pattern, input, durability, output);
        }

        @Nullable
        @Override
        public CarveRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Boolean[][] pattern = pBuffer.readList(buf -> buf.readList(FriendlyByteBuf::readBoolean).toArray(Boolean[]::new)).toArray(Boolean[][]::new);
            int durability = pBuffer.readInt();
            BlockState input = pBuffer.readJsonWithCodec(BlockState.CODEC);
            BlockState output = pBuffer.readJsonWithCodec(BlockState.CODEC);
            return new CarveRecipe(pRecipeId, pattern, input, durability, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CarveRecipe pRecipe) {
            pBuffer.writeCollection(Arrays.stream(pRecipe.pattern).collect(Collectors.toList()), (buf, element) -> buf.writeCollection(Arrays.stream(element).toList(), FriendlyByteBuf::writeBoolean));
            pBuffer.writeInt(pRecipe.durability);
            pBuffer.writeJsonWithCodec(BlockState.CODEC, pRecipe.input);
            pBuffer.writeJsonWithCodec(BlockState.CODEC, pRecipe.output);
        }
    }
}
