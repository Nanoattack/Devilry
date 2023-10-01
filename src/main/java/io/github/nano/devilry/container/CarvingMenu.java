package io.github.nano.devilry.container;

import io.github.nano.devilry.data.recipes.CarveRecipe;
import io.github.nano.devilry.data.recipes.ModRecipeTypes;
import io.github.nano.devilry.data.recipes.utility.CarveContainer;
import io.github.nano.devilry.item.custom.Knife;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class CarvingMenu extends AbstractContainerMenu {
    private final BlockState target;
    private final BlockPos pos;
    private final Boolean[][] pattern;
    public final CarveRecipe.CarvingMaterial material;
    public final ItemStack tool;
    public final Inventory inv;
    public final int x;
    private boolean dontDestroy = false;
    public final int y;

    public CarvingMenu(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(windowId, playerInv, extraData.readBlockPos(), extraData.readInt(), extraData.readInt(), extraData.readEnum(CarveRecipe.CarvingMaterial.class), extraData.readItem());
    }

    public CarvingMenu(int windowId, Inventory playerInv, BlockPos pos, int x, int y, CarveRecipe.CarvingMaterial material, ItemStack tool) {
        super(ModContainers.CARVING_CONTAINER.get(), windowId);
        this.target = playerInv.player.level().getBlockState(pos);
        this.pos = pos;
        pattern = new Boolean[x][y];
        for (Boolean[] booleans : pattern) {
            Arrays.fill(booleans, true);
        }
        this.material = material;
        this.x = x;
        this.y = y;
        this.tool = tool;
        inv = playerInv;
    }

    public void chip(int x, int y, Level level) {
        if (!pattern[x][y]) {
            return;
        }
        pattern[x][y] = false;
        if (Arrays.stream(pattern).flatMap(Arrays::stream).noneMatch(bool -> bool)) {
            level.destroyBlock(pos, false);
            level.sendBlockUpdated(pos, target, Blocks.AIR.defaultBlockState(), 3);
            tool.hurtAndBreak(16, inv.player, player -> player.broadcastBreakEvent(inv.player.getUsedItemHand()));
            dontDestroy = true;
            inv.player.closeContainer();
        }
        testRecipe(level);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        if (!dontDestroy || pPlayer.level().isClientSide()) return;
        pPlayer.level().destroyBlock(pos, false);
        pPlayer.level().sendBlockUpdated(pos, target, Blocks.AIR.defaultBlockState(), 3);
        tool.hurtAndBreak(16, inv.player, player -> player.broadcastBreakEvent(inv.player.getUsedItemHand()));
    }

    public void testRecipe(Level level) {
        RecipeManager recipeManager = level.getRecipeManager();
        CarveContainer carveContainer = new CarveContainer(target, level, pattern, material);
        var recipe = recipeManager.getRecipeFor(ModRecipeTypes.CARVING_RECIPE.get(), carveContainer, level);
        if (recipe.isPresent()) {
            BlockState result = NbtUtils.readBlockState(level.holderLookup(ForgeRegistries.BLOCKS.getRegistryKey()),recipe.get().assemble(carveContainer, level.registryAccess()).getOrCreateTag().getCompound("value"));
            level.setBlock(pos, result, 3);
            level.addDestroyBlockEffect(pos, target);
            level.sendBlockUpdated(pos, target, result, 3);
            tool.hurtAndBreak(recipe.get().getDurability(), inv.player, player -> player.broadcastBreakEvent(inv.player.getUsedItemHand()));
            dontDestroy = true;
            inv.player.closeContainer();
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(pPlayer.level()),
                pos), pPlayer, target.getBlock());
    }

    @Override
    public void slotsChanged(@NotNull Container pContainer) {
        super.slotsChanged(pContainer);
    }
}
