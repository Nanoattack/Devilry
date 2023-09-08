package io.github.nano.devilry.data.recipes.utility;

import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class CarveContainer implements Container {
    private @NotNull BlockState blockState;
    private final ItemStack blockStateHolder = new ItemStack(Items.STONE);
    private final Boolean[][] pattern;
    private final Level level;

    public CarveContainer(@NotNull BlockState blockState, Level level, Boolean[][] pattern) {
        this.blockState = blockState;
        this.level = level;
        this.pattern = pattern;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    public Boolean[][] getPattern() {
        return pattern;
    }

    @Override
    public boolean isEmpty() {
        return blockState.is(Blocks.AIR);
    }

    @Override
    public @NotNull ItemStack getItem(int pSlot) {
        blockStateHolder.setTag(NbtUtils.writeBlockState(blockState));
        return blockStateHolder;
    }

    public @NotNull BlockState getBlockState() {
        return blockState;
    }

    @Override
    public @NotNull ItemStack removeItem(int pSlot, int pAmount) {
        blockState = Blocks.AIR.defaultBlockState();
        blockStateHolder.setTag(NbtUtils.writeBlockState(blockState));
        return blockStateHolder;
    }

    public void removeBlockState() {
        blockState = Blocks.AIR.defaultBlockState();
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int pSlot) {
        blockState = Blocks.AIR.defaultBlockState();
        blockStateHolder.setTag(NbtUtils.writeBlockState(blockState));
        return blockStateHolder;
    }

    @Override
    public void setItem(int pSlot, @NotNull ItemStack pStack) {
        setItem(pStack, this.level);
    }

    public void setBlockState(BlockState blockState) {
        this.blockState = blockState;
    }

    public void setItem(ItemStack pStack, Level level) {
        if (pStack.getTag() == null) return;
        blockState = NbtUtils.readBlockState(level.holderLookup(ForgeRegistries.BLOCKS.getRegistryKey()), pStack.getTag());
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {
        blockState = Blocks.AIR.defaultBlockState();
    }
}
