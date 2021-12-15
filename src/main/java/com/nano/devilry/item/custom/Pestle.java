package com.nano.devilry.item.custom;

import com.nano.devilry.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

import java.util.Objects;
import java.util.Random;

public class Pestle extends Item
{
    public Pestle(Properties p_41383_) {
        super(p_41383_);
    }
    protected static final Random random = new Random();
    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level level = context.getLevel();

        if(!level.isClientSide)
        {
            Player player = Objects.requireNonNull(context.getPlayer());
            BlockState clickedBlock = level.getBlockState(context.getClickedPos());

            rightClickOnCertainBlockState(clickedBlock, context, player, stack);
        }

        return super.onItemUseFirst(stack, context);
    }

    private void rightClickOnCertainBlockState(BlockState clickedBlock, UseOnContext context, Player player, ItemStack stack) {
        if (player.isCrouching()) {
            stack.hurtAndBreak(1, player, player1 -> player1.broadcastBreakEvent(context.getHand()));
            if (random.nextFloat() < 0.33f) {
                if (blockIsValidForFlint(clickedBlock)) {
                    destroyBlockGiveFlint(player, context.getLevel(), context.getClickedPos());
                } else if (blockIsValidForGlowStone(clickedBlock)) {
                    destroyBlockGiveGlowstone(player, context.getLevel(), context.getClickedPos());
                } else if (blockIsValidForAmethyst(clickedBlock)) {
                    destroyBlockGiveAmethyst(player, context.getLevel(), context.getClickedPos());
                }else {
                    context.getLevel().playSound((Player) null, context.getClickedPos(), SoundEvents.POLISHED_DEEPSLATE_PLACE, SoundSource.NEUTRAL, 1.0F, 0.8F + context.getLevel().random.nextFloat() * 0.4F);
                }
            }else {
                context.getLevel().playSound((Player) null, context.getClickedPos(), SoundEvents.POLISHED_DEEPSLATE_PLACE, SoundSource.NEUTRAL, 1.0F, 0.8F + context.getLevel().random.nextFloat() * 0.4F);
            }
        }
    }

    private boolean blockIsValidForFlint(BlockState clickedBlock) {
        return clickedBlock.getBlock() == Blocks.GRAVEL
                ||clickedBlock.getBlock() == Blocks.SAND
                ||clickedBlock.getBlock() == Blocks.COBBLESTONE
                ||clickedBlock.getBlock() == Blocks.BLACKSTONE
                ||clickedBlock.getBlock() == Blocks.COBBLED_DEEPSLATE
                ||clickedBlock.getBlock() == Blocks.MOSSY_COBBLESTONE
                ||clickedBlock.getBlock() == Blocks.CRACKED_DEEPSLATE_BRICKS
                ||clickedBlock.getBlock() == Blocks.CRACKED_DEEPSLATE_TILES
                ||clickedBlock.getBlock() == Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS
                ||clickedBlock.getBlock() == Blocks.CRACKED_STONE_BRICKS;
    }

    private void destroyBlockGiveFlint(Player player, Level level, BlockPos blockPos)
    {
        player.addItem(new ItemStack(Items.FLINT));
        level.playSound((Player) null, blockPos, SoundEvents.GRINDSTONE_USE, SoundSource.PLAYERS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
        level.destroyBlock(blockPos, false);
    }

    private boolean blockIsValidForGlowStone(BlockState clickedBlock) {
        return clickedBlock.getBlock() == Blocks.GLOWSTONE;
    }

    private void destroyBlockGiveGlowstone(Player player, Level level, BlockPos blockPos)
    {
        player.addItem(new ItemStack(Items.GLOWSTONE_DUST, 4));
        level.playSound((Player) null, blockPos, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
        level.destroyBlock(blockPos, false);
    }

    private boolean blockIsValidForAmethyst(BlockState clickedBlock) {
        return clickedBlock.getBlock() == Blocks.AMETHYST_BLOCK
                |clickedBlock.getBlock() == Blocks.BUDDING_AMETHYST;
    }

    private void destroyBlockGiveAmethyst(Player player, Level level, BlockPos blockPos)
    {
        player.addItem(new ItemStack(Items.AMETHYST_SHARD, 4));
        level.playSound((Player) null, blockPos, SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
        level.destroyBlock(blockPos, false);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack container = itemStack.copy();
        if(container.hurt(1, random, null)) {
            return ItemStack.EMPTY;
        }else {
            return container;
        }
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }
}
