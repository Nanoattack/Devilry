package com.nano.devilry.item.custom;

import com.nano.devilry.block.ModBlocks;
import com.nano.devilry.events.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;


public class Guano extends Item
{

    public Guano(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level level = context.getLevel();
        if(!level.isClientSide())
        {
            Player player = Objects.requireNonNull(context.getPlayer());
            BlockState clickedBlock = level.getBlockState(context.getClickedPos());

            CreateFesteringLimestone(clickedBlock, context, player,level);
            if(!player.isCreative()) {
                stack.shrink(1);
            }
        }
    return super.onItemUseFirst(stack, context);
    }

    private void CreateFesteringLimestone(BlockState blockState, UseOnContext context, Player player, Level level)
    {
        if(blockState.getBlock() == ModBlocks.LIMESTONE.get())
        {
            BlockPos pos = context.getClickedPos();
            level.removeBlock(pos, false);
            level.setBlock(pos, ModBlocks.FESTERING_LIMESTONE.get().defaultBlockState(), 3);
            level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.FESTER.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        }
    }
}
