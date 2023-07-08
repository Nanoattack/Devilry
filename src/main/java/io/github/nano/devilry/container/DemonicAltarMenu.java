package io.github.nano.devilry.container;

import io.github.nano.devilry.block.ModBlocks;
import io.github.nano.devilry.blockentity.DemonicAltarBlockEntity;
import io.github.nano.devilry.container.cache.basicItem;
import io.github.nano.devilry.data.recipes.AltarRecipe;
import io.github.nano.devilry.screen.slot.ResultSlotItemHandler;
import io.github.nano.devilry.util.Utils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class DemonicAltarMenu extends AbstractContainerMenu {
    private final DemonicAltarBlockEntity blockEntity;
    private final Level level;
    public DemonicAltarMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(0));
    }

    public DemonicAltarMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModContainers.DEMONIC_ALTAR_CONTAINER.get(), id);
        this.blockEntity = (DemonicAltarBlockEntity) entity;
        this.level = inv.player.level();

        addPlayerInventory(inv);
        addPlayerHotBar(inv);

        blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
            addSlot(new SlotItemHandler(h, 0, 97, 11));
            addSlot(new SlotItemHandler(h, 1, 124, 30));
            addSlot(new SlotItemHandler(h, 2, 117, 61));
            addSlot(new SlotItemHandler(h, 3, 77, 61));
            addSlot(new SlotItemHandler(h, 4, 70, 30));
            addSlot(new SlotItemHandler(h, 5, 97, 39));
            addSlot(new ResultSlotItemHandler(h, 6, 176, 32));
        });

        addDataSlots(data);
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(blockEntity.getLevel()),
                blockEntity.getBlockPos()), pPlayer, ModBlocks.DEMONIC_ALTAR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 30 + l * 18, 85 + i * 18));
            }
        }
    }

    private void addPlayerHotBar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 30 + i * 18, 143));
        }
    }

    @Override
    public void slotsChanged(@NotNull Container pContainer) {
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
        level.getProfiler().push("quickMoveStack");
        Slot sourceSlot = slots.get(index);

        if (!sourceSlot.hasItem()) {
            level.getProfiler().pop();
            return ItemStack.EMPTY;//EMPTY_ITEM
        }

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < 36) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            try {
                if (!Utils.smartQuickMove(blockEntity.cache.get(), sourceStack, false, this, 6, (AltarRecipe recipe) -> {
                    var copy = new ArrayList<>(recipe.getIngredients());
                    NonNullList<ItemStack> items = NonNullList.withSize(6, ItemStack.EMPTY);
                    List<ItemStack> subList = this.getItems().subList(37, 43);
                    for (int i = 0; i < subList.size(); i++) {
                        ItemStack itemStack = subList.get(i);
                        items.set(i, itemStack);
                    }

                    outer:
                        for (int i = 0; i < copy.size(); i++)  {
                            for (int j = 0; j < items.size(); j++) {
                                ItemStack item = items.get(j);
                                if (copy.get(i).test(item)) {
                                    items.set(j, ItemStack.EMPTY);
                                    copy.set(i, Ingredient.EMPTY);
                                    continue outer;
                                }
                            }
                        }

                    IntList ints = new IntArrayList();

                    for (Ingredient ingredient : copy) {
                        if (ingredient.isEmpty()) {
                            continue;
                        }
                        if (ingredient.test(sourceStack)) {
                            for (int j = 0; j < items.size(); j++) {
                                if (items.get(j).isEmpty() || items.get(j).is(sourceStack.getItem())) {
                                    ints.add(j);
                                }
                            }
                        }
                    }
                    level.getProfiler().pop();
                    return ints.intStream();

                }, itemStack -> new basicItem(itemStack.getItem()))) {
                    level.getProfiler().pop();
                    return ItemStack.EMPTY;  // EMPTY_ITEM
                }
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                level.getProfiler().pop();
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            level.getProfiler().pop();
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        level.getProfiler().pop();
        return copyOfSourceStack;
    }
}
