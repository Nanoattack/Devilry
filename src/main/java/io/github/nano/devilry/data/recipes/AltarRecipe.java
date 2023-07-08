package io.github.nano.devilry.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import io.github.nano.devilry.block.ModBlocks;
import io.github.nano.devilry.container.cache.CacheItem;
import io.github.nano.devilry.data.recipes.utility.HashedRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
//fixme
//todo

public class AltarRecipe extends HashedRecipe<Container> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final List<Either<EntityType<?>,MobType>> sacrifice;
    private final Ingredient focus;

    public AltarRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, List<Either<EntityType<?>, MobType>> sacrifice, Ingredient focus) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.sacrifice = sacrifice;
        this.focus = focus;
    }
    @Override
    public boolean matches(@NotNull Container inv, @NotNull Level level) {
        if (focus.test(inv.getItem(5))) {
            List<ItemStack> list = new ArrayList<>();
            for (int i = 0; i <= 4; i++) {
                list.add(inv.getItem(i));
            }
            List<Ingredient> ingredients = new ArrayList<>(recipeItems);
            for (ItemStack itemStack : list) {
                if (ingredients.stream().noneMatch(ingr -> ingr.test(itemStack))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Container inv, @NotNull RegistryAccess registries) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
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
        return new ItemStack(ModBlocks.DEMONIC_ALTAR.get());
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
       return ModRecipeTypes.DEMON_ALTAR_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.DEMON_ALTAR_RECIPE.get();
    }

    public Ingredient getFocus() {
        return focus;
    }

    public List<Either<EntityType<?>, MobType>> getSacrifice() {
        return sacrifice;
    }

    @Override
    public boolean isPossible(List<? extends CacheItem> itemStackList, boolean isShaped) {
        for (CacheItem itemStack : itemStackList) {
            if (itemStack.getItemStack().isEmpty()) {
                continue;
            }
            if (this.recipeItems.stream().noneMatch(ingredient -> ingredient.test(itemStack.getItemStack()))) {
                return false;
            }
        }
        return this.getFocus().test(itemStackList.get(5).getItemStack());
    }

    @Override
    public int toInt(List<? extends CacheItem> itemStackList, boolean isShaped) {
        int j = 0;
        for (CacheItem itemStack : itemStackList) {
            if (itemStack.getItemStack().isEmpty()) {
                continue;
            }
            if (this.recipeItems.stream().anyMatch(ingredient -> ingredient.test(itemStack.getItemStack()))) {
                j++;
            }
        }
        return j;
    }

    @Override
    public boolean isShaped() {
        return false;
    }

    public static class Serializer implements RecipeSerializer<AltarRecipe> {
        
        @Override
        public @NotNull AltarRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");

            NonNullList<Ingredient> inputs = NonNullList.withSize(5, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            Ingredient focus = Ingredient.fromJson(json.get("focus"));

            JsonArray sacrifices = GsonHelper.getAsJsonArray(json, "sacrifices");

            List<Either<EntityType<?>, MobType>> sacrifice = new ArrayList<>();

            for (int i = 0; i < sacrifices.size(); i++) {
                if (ingredients.get(i).getAsString().equals("ALL")) {
                    sacrifice.clear();
                    sacrifice.add(Either.right(null));
                    break;
                } else {
                    switch (ingredients.get(i).getAsString()) {
                        case "WATER" -> sacrifice.set(i, Either.right(MobType.WATER));
                        case "ARTHROPOD" -> sacrifice.set(i, Either.right(MobType.ARTHROPOD));
                        case "ILLAGER" -> sacrifice.set(i, Either.right(MobType.ILLAGER));
                        case "UNDEAD" -> sacrifice.set(i, Either.right(MobType.UNDEAD));
                        case "UNDEFINED" -> sacrifice.set(i, Either.right(MobType.UNDEFINED));
                        default -> sacrifice.set(i, Either.left(ForgeRegistries.ENTITY_TYPES.getValue(ResourceLocation.tryParse(ingredients.get(i).getAsString()))));
                    }
                }
            }
            return new AltarRecipe(recipeId, output, inputs, sacrifice, focus);
        }

        @Nullable
        @Override
        public AltarRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            ItemStack output = pBuffer.readItem();
            var ingredients = pBuffer.readCollection(value -> NonNullList.withSize(value, Ingredient.EMPTY), Ingredient::fromNetwork);
            var sacrifice = pBuffer.<Either<EntityType<?>, MobType>>readList(buf -> buf.readEither(buf1 -> ForgeRegistries.ENTITY_TYPES.getValue(ResourceLocation.tryParse(buf1.readUtf())), buf1 -> switch (buf1.readInt()){
                case 0 -> null;
                case 1 -> MobType.WATER;
                case 2 -> MobType.ARTHROPOD;
                case 3 -> MobType.ILLAGER;
                case 4 -> MobType.UNDEAD;
                default -> MobType.UNDEFINED;
            }));
            var focus = Ingredient.fromNetwork(pBuffer);

            return new AltarRecipe(pRecipeId, output, ingredients, sacrifice, focus);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, AltarRecipe pRecipe) {
            pBuffer.writeItem(pRecipe.output);
            pBuffer.writeCollection(pRecipe.recipeItems, ((buf, ingredient) -> ingredient.toNetwork(buf)));
            pBuffer.writeCollection(pRecipe.sacrifice, ((buf, entityTypeMobTypeEither) -> buf.writeEither(entityTypeMobTypeEither, (buf1, entityType) -> buf1.writeUtf(entityType.toString()), ((buf1, mobType) -> {
                int mob;
                if (mobType == null) {
                    mob = 0;
                } else if (mobType == MobType.WATER) {
                    mob = 1;
                } else if (mobType == MobType.ARTHROPOD) {
                    mob = 2;
                } else if (mobType == MobType.ILLAGER) {
                    mob = 3;
                } else if (mobType == MobType.UNDEAD) {
                    mob = 4;
                } else {
                    mob = 5;
                }
                buf1.writeInt(mob);
            }))));
            pRecipe.focus.toNetwork(pBuffer);
        }
    }
}
