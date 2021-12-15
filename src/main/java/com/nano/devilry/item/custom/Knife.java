package com.nano.devilry.item.custom;

import com.nano.devilry.events.ModSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;


public class Knife extends Item {
    public Knife(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {

            rightClickWithOffHandItem(level, player, stack);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
    private void rightClickWithOffHandItem(Level level, Player player, ItemStack stack) {

            if (ItemIsValidForSlicing(player)) {
                player.getOffhandItem().shrink(1);
                player.addItem(new ItemStack(Items.STRING));
                level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.KNIFE_SLASH.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                player.getCooldowns().addCooldown(this, 20);
                stack.hurtAndBreak(1, player, player1 -> player1.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            }
    }

    private boolean ItemIsValidForSlicing(Player player)
    {
        return player.getOffhandItem().getItem() == Items.WHITE_WOOL;
    }
}
