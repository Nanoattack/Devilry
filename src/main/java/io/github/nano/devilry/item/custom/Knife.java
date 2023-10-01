package io.github.nano.devilry.item.custom;

import io.github.nano.devilry.container.CarvingMenu;
import io.github.nano.devilry.data.recipes.CarveRecipe;
import io.github.nano.devilry.data.recipes.ModRecipeTypes;
import io.github.nano.devilry.data.recipes.utility.CarveContainer;
import io.github.nano.devilry.events.ModSoundEvents;
import io.github.nano.devilry.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//fixme
//todo

@SuppressWarnings("unused")
public class Knife extends Item {
    private final int tier;

    public Knife(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        CarveRecipe.CarvingMaterial material = CarveRecipe.CarvingMaterial.getMaterial(pContext.getLevel().getBlockState(pContext.getClickedPos()));
        if (material == null || pContext.getPlayer().isShiftKeyDown()) {
            return InteractionResult.PASS;
        } else if (pContext.getLevel().isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        int x = 7;
        int y = 7;

        if (material.knifeTier > tier) return InteractionResult.FAIL;
        NetworkHooks.openScreen(((ServerPlayer) pContext.getPlayer()), new MenuProvider() {
            @Override
            public @NotNull Component getDisplayName() {
                return Component.translatableWithFallback("screen.devilry.carve", "Carving");
            }

            @Override
            public @NotNull AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
                return new CarvingMenu(pContainerId, pPlayerInventory, pContext.getClickedPos(), x, y, material, pContext.getItemInHand());
            }
        }, buffer -> {
            buffer.writeBlockPos(pContext.getClickedPos());
            buffer.writeInt(x);
            buffer.writeInt(y);
            buffer.writeEnum(material);
            buffer.writeItemStack(pContext.getItemInHand(), false);
        });
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemInHand = pPlayer.getItemInHand(pUsedHand);
        if (pLevel.isClientSide()) return InteractionResultHolder.pass(itemInHand);
        RecipeManager recipeManager = pLevel.getRecipeManager();
        var recipe = recipeManager.getRecipeFor(ModRecipeTypes.KNIFE_RECIPE.get(), pPlayer.getInventory(), pLevel);
        if (recipe.isPresent() && !pLevel.isClientSide()) {
            recipe.get().doCut((ServerLevel) pLevel, pPlayer, itemInHand);
            if (pUsedHand == InteractionHand.MAIN_HAND) {
                pPlayer.getItemInHand(InteractionHand.OFF_HAND).shrink(1);
            } else {
                pPlayer.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
            }
            return InteractionResultHolder.success(itemInHand);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}