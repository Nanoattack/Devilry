package io.github.nano.devilry.devilry.item.custom;

import io.github.nano.devilry.devilry.events.ModSoundEvents;
import io.github.nano.devilry.devilry.item.ModItems;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;


public class Knife extends Item {

    public int min = 1;
    public int max;
    public int soundCase;

    public Knife(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {

            SliceCraft(level, player, stack);

        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private void SliceCraft(Level level, Player player, ItemStack stack) {

        if (ItemIsValidForSlicing(player)) {

            SliceItem(player, stack);

            GiveOutputItem(level, player);
        }
    }

    private boolean ItemIsValidForSlicing(Player player) {
        return player.getOffhandItem().getItem() == Items.WHITE_WOOL ||
                player.getOffhandItem().getItem() == Items.ROTTEN_FLESH ||
                player.getOffhandItem().getItem() == Items.PAINTING;
    }

    private void SliceItem(Player player, ItemStack stack) {
        player.getOffhandItem().shrink(1);
        CooldownSet(player);
        stack.hurtAndBreak(1, player, player1 -> player1.broadcastBreakEvent(InteractionHand.MAIN_HAND));
    }

    private void PlayMetalNoise(Level level, Player player) {
        level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.KNIFE_SLASH_METAL.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    private void PlayClothNoise(Level level, Player player) {
        level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), ModSoundEvents.CLOTH_RIP.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    private void GiveOutputItem(Level level, Player player) {

        if (player.getOffhandItem().getItem() == Items.WHITE_WOOL ||
                player.getOffhandItem().getItem() == Items.PAINTING) {

            max = 4;
            int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
            player.addItem(new ItemStack(Items.STRING, random_int));
            PlayClothNoise(level, player);

        } else if (player.getOffhandItem().getItem() == Items.ROTTEN_FLESH) {

            max = 1;
            int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
            player.addItem(new ItemStack(Items.LEATHER, random_int));
            PlayMetalNoise(level, player);
        }
    }

    private void CooldownSet(Player player) {
        if (player.getMainHandItem().getItem() == ModItems.FLINT_KNIFE.get()) {
            player.getCooldowns().addCooldown(this, 60);
        }

        else if (player.getMainHandItem().getItem() == ModItems.BRONZE_KNIFE.get()) {
            player.getCooldowns().addCooldown(this, 30);
        }
    }
}