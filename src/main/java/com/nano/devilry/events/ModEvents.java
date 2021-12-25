package com.nano.devilry.events;

import com.nano.devilry.ModMain;
import com.nano.devilry.block.ModBlocks;
import com.nano.devilry.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = ModMain.MOD_ID)
public class ModEvents
{
    @SubscribeEvent
    public static void FeedBatGetGuano(PlayerInteractEvent.EntityInteract event) {

        Random random = new Random();
        int guanoTime = random.nextInt(6000) + 6000;

        if (!event.getWorld().isClientSide) {
            if (event.getEntity().getType() == EntityType.BAT) {
                Player player = event.getPlayer();
                if (player.getMainHandItem().getItem() == Items.APPLE) {
                    player.getMainHandItem().shrink(1);
                    player.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                    event.getEntity().spawnAtLocation(ModItems.BAT_GUANO.get());
                    guanoTime = random.nextInt(6000) + 6000;
                }
            }
        }
    }
}
