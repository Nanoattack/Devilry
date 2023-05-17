package io.github.nano.devilry.container;

import io.github.nano.devilry.block.ModBlocks;
import io.github.nano.devilry.blockentity.MortarBlockEntity;
import io.github.nano.devilry.screen.slot.ResultSlotItemHandler;
import io.github.nano.devilry.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
//todo

public class MortarMenu extends AbstractContainerMenu {
    private final MortarBlockEntity blockEntity;
    private final Level level;
    private final ContainerData containerData;

    public MortarMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public MortarMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModContainers.MORTAR_CONTAINER.get(), id);
        this.blockEntity = (MortarBlockEntity) entity;
        this.level = inv.player.getLevel();
        this.containerData = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        if (blockEntity != null) {
            blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                addSlot(new SlotItemHandler(h, 0, 80, 10)); //pestle slot
                addSlot(new SlotItemHandler(h, 1, 48, 10)); //top left
                addSlot(new SlotItemHandler(h, 2, 35, 32)); //center left
                addSlot(new SlotItemHandler(h, 3, 48, 54)); //bottom left
                addSlot(new SlotItemHandler(h, 4, 112, 10)); //top right
                addSlot(new SlotItemHandler(h, 5, 125, 32)); //center right
                addSlot(new SlotItemHandler(h, 6, 112, 54)); //bottom center
                addSlot(new ResultSlotItemHandler(h, 7, 80, 46)); //result (center)
            });
        }

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return containerData.get(0) > 0;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(blockEntity.getLevel()),
                blockEntity.getBlockPos()), pPlayer, ModBlocks.MORTAR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }

    @Override
    public void slotsChanged(Container pContainer) {
        super.slotsChanged(pContainer);
    }

    // CREDIT GOES TO: Diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots and the player inventory slots and the hot bar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hot bar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    @SuppressWarnings("SpellCheckingInspection")
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 8;  // must match TileEntityInventoryBasic.NUMBER_OF_SLOTS

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);

        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;//EMPTY_ITEM

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < 36) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }

        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            //todo
//            if (!Utils.smartQuickMove()) {
//                return ItemStack.EMPTY;
//            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }

        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }



}
