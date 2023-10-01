package io.github.nano.devilry.data.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.data.recipes.utility.CarveContainer;
import io.github.nano.devilry.util.tags.DevilryTags;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CarveRecipe implements Recipe<CarveContainer> {
    public enum CarvingMaterial implements StringRepresentable{
        WOOD(0, new ResourceLocation(ModMain.MOD_ID, "textures/gui/carving_wood.png"), DevilryTags.Blocks.CARVING_MATERIAL_WOOD),
        SOFT_STONE(1, new ResourceLocation(ModMain.MOD_ID, "textures/gui/carving_soft_stone.png"), DevilryTags.Blocks.CARVING_MATERIAL_SOFT_STONE),
        STONE(2, new ResourceLocation(ModMain.MOD_ID, "textures/gui/carving_stone.png"), DevilryTags.Blocks.CARVING_MATERIAL_STONE),
        OBSIDIAN(3, new ResourceLocation(ModMain.MOD_ID, "textures/gui/carving_obsidian.png"), DevilryTags.Blocks.CARVING_MATERIAL_OBSIDIAN);
        public final int knifeTier;
        public final ResourceLocation gui;
        public final TagKey<Block> tagKey;

        CarvingMaterial(int knifeTier, @NotNull ResourceLocation gui, TagKey<Block> tagKey) {
            this.knifeTier = knifeTier;
            this.gui = gui;
            this.tagKey = tagKey;
        }

        @Override
        public @NotNull String getSerializedName() {
            return switch (this) {
                case WOOD -> "wood";
                case SOFT_STONE -> "soft_stone";
                case STONE -> "stone";
                case OBSIDIAN -> "obsidian";
            };
        }

        public static CarvingMaterial getMaterial(BlockState state) {
            for (CarvingMaterial value : CarvingMaterial.values()) {
                if (state.is(value.tagKey)) {
                    return value;
                }
            }
            return null;
        }
    }

    private final ResourceLocation id;
    private final Boolean[][] pattern;
    private final BlockState input;
    private final int durability;
    private final BlockState output;
    private final CarvingMaterial material;

    public CarveRecipe(ResourceLocation id, Boolean[][] pattern, BlockState input, int durability, BlockState output, CarvingMaterial material) {
        this.id = id;
        this.pattern = pattern;
        this.input = input;
        this.durability = durability;
        this.output = output;
        this.material = material;
    }

    @Override
    public boolean matches(CarveContainer pContainer, Level pLevel) {
        if (pContainer.getCarvingMaterial().knifeTier < material.knifeTier || pContainer.getCarvingMaterial() != material) return false;
        if (pattern.length == pContainer.getPattern().length) {
            for (int i = 0; i < pattern.length; i++) {
                for (int j = 0; j < pattern[i].length; j++) {
                    if (pattern[i][j] != pContainer.getPattern()[j][i]) {
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
        holder.getOrCreateTag().put("value", NbtUtils.writeBlockState(this.output));
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

    public int getDurability() {
        return durability;
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

    public CarvingMaterial getMaterial() {
        return material;
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
            CarvingMaterial material = StringRepresentable.fromEnum(CarvingMaterial::values).decode(new Dynamic<>(JsonOps.INSTANCE, GsonHelper.getNonNull(json, "material"))).result().orElseThrow().getFirst();
            input = BlockState.CODEC.decode(new Dynamic<>(JsonOps.INSTANCE, GsonHelper.getNonNull(json, "input"))).result().orElseThrow().getFirst();
            output = BlockState.CODEC.decode(new Dynamic<>(JsonOps.INSTANCE, GsonHelper.getNonNull(json, "output"))).result().orElseThrow().getFirst();

            return new CarveRecipe(recipeId, pattern, input, durability, output, material);
        }

        @Nullable
        @Override
        public CarveRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Boolean[][] pattern = pBuffer.readList(buf -> buf.readList(FriendlyByteBuf::readBoolean).toArray(Boolean[]::new)).toArray(Boolean[][]::new);
            int durability = pBuffer.readInt();
            BlockState input = pBuffer.readJsonWithCodec(BlockState.CODEC);
            BlockState output = pBuffer.readJsonWithCodec(BlockState.CODEC);
            CarvingMaterial material = pBuffer.readEnum(CarvingMaterial.class);
            return new CarveRecipe(pRecipeId, pattern, input, durability, output, material);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CarveRecipe pRecipe) {
            pBuffer.writeCollection(Arrays.stream(pRecipe.pattern).collect(Collectors.toList()), (buf, element) -> buf.writeCollection(Arrays.stream(element).toList(), FriendlyByteBuf::writeBoolean));
            pBuffer.writeInt(pRecipe.durability);
            pBuffer.writeJsonWithCodec(BlockState.CODEC, pRecipe.input);
            pBuffer.writeJsonWithCodec(BlockState.CODEC, pRecipe.output);
            pBuffer.writeEnum(pRecipe.material);
        }
    }
}
