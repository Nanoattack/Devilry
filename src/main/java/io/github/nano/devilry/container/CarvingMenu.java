package io.github.nano.devilry.container;

import io.github.nano.devilry.data.recipes.CarveRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CarvingMenu extends AbstractContainerMenu {
    private final BlockState target;
    private final BlockPos pos;
    private final Boolean[][] pattern;
    public final CarveRecipe.CarvingMaterial material;

    public CarvingMenu(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(windowId, playerInv, extraData.readBlockPos(), extraData.readInt(), extraData.readInt(), extraData.readEnum(CarveRecipe.CarvingMaterial.class));
    }

    public CarvingMenu(int windowId, Inventory playerInv, BlockPos pos, int x, int y, CarveRecipe.CarvingMaterial material) {
        super(ModContainers.CARVING_CONTAINER.get(), windowId);
        this.target = playerInv.player.level().getBlockState(pos);
        this.pos = pos;
        addPlayerInventory(playerInv);
        addPlayerHotBar(playerInv);
        pattern = new Boolean[x][y];
        this.material = material;
    }

    public boolean chip(int x, int y) {
        if (pattern[x][y]) {
            return false;
        }
        pattern[x][y] = true;
        return true;
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



    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotBar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }

    @Override
    public void slotsChanged(@NotNull Container pContainer) {
        super.slotsChanged(pContainer);
    }
}
