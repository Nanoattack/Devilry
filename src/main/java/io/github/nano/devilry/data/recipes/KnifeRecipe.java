package io.github.nano.devilry.data.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import io.github.nano.devilry.data.recipes.utility.BlockStateContainer;
import io.github.nano.devilry.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class KnifeRecipe implements Recipe<Inventory> {
    private final ResourceLocation id;
    private final int durability;
    private final Ingredient ingredient;
    private final Either<ResourceLocation, List<ItemStack>> result;

    public KnifeRecipe(ResourceLocation id, int durability, Ingredient ingredient, Either<ResourceLocation, List<ItemStack>> result) {
        this.id = id;
        this.durability = durability;
        this.ingredient = ingredient;
        this.result = result;
    }
    @Override
    public boolean matches(Inventory pContainer, @NotNull Level pLevel) {
        return ingredient.test(pContainer.getItem(Inventory.SLOT_OFFHAND)) || ingredient.test(pContainer.getSelected());
    }

    @Override
    @Deprecated
    public @NotNull ItemStack assemble(@NotNull Inventory inventory, @NotNull RegistryAccess access) {
        return ItemStack.EMPTY;
    }

    public void doCut(ServerLevel level, Player player, ItemStack tool) {
        result.ifLeft(rl -> {
            var table = level.getServer().getLootData().getLootTable(rl);
            var items = table.getRandomItems( new LootParams.Builder(level)
                    .withLuck(player.getLuck())
                    .withParameter(LootContextParams.TOOL, tool)
                    .withParameter(LootContextParams.THIS_ENTITY, player)
                    .withParameter(LootContextParams.ORIGIN, player.position())
                    .create(LootContextParamSet.builder()
                            .required(LootContextParams.TOOL)
                            .required(LootContextParams.THIS_ENTITY)
                            .required(LootContextParams.ORIGIN).build()));

            items.forEach(item -> {
                level.addFreshEntity(new ItemEntity(level, player.position().x(), player.position().y(), player.position().z(), item));
            });
        });
        result.ifRight(items -> {
            items.forEach(item -> {
                level.addFreshEntity(new ItemEntity(level, player.position().x(), player.position().y(), player.position().z(), item));
            });
        });
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width == 2 && height == 3;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registries) {
        return ItemStack.EMPTY;
    }

    public int getDurability() {
        return durability;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.ingredient);
    }

    public ItemStack getIcon() {
        return new ItemStack(ModItems.BRONZE_KNIFE.get());
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
       return ModRecipeTypes.KNIFE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.KNIFE_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<KnifeRecipe> {
        @Override
        public @NotNull KnifeRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
            int durability = GsonHelper.getAsInt(json, "durability");
            Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
            Either<ResourceLocation, List<ItemStack>> result;
            if (json.get("result").isJsonArray()) {
                result = Either.right(GsonHelper.getAsJsonArray(json, "result").asList().stream().map(element -> ShapedRecipe.itemStackFromJson(element.getAsJsonObject())).toList());
            } else {
                result = Either.left(ResourceLocation.tryParse(json.getAsJsonPrimitive("result").getAsString()));
            }

            return new KnifeRecipe(recipeId, durability, ingredient, result);
        }

        @Nullable
        @Override
        public KnifeRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            int durability = pBuffer.readInt();
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            Either<ResourceLocation, List<ItemStack>> result = pBuffer.readEither(FriendlyByteBuf::readResourceLocation, buf -> buf.readList(FriendlyByteBuf::readItem));
            return new KnifeRecipe(pRecipeId, durability, ingredient, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, KnifeRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.durability);
            pRecipe.ingredient.toNetwork(pBuffer);
            pBuffer.writeEither(pRecipe.result, FriendlyByteBuf::writeResourceLocation, (buf, value) -> buf.writeCollection(value, (buffer, val) -> buffer.writeItemStack(val, false)));
        }
    }
}
