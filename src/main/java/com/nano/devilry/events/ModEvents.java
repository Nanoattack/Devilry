package com.nano.devilry.events;

import com.nano.devilry.ModMain;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
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
            Player player = event.getPlayer();
            if (player.getMainHandItem().getItem() == Items.APPLE) {
                player.getMainHandItem().shrink(1);
                player.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                event.getEntity().spawnAtLocation(Items.EGG);
                guanoTime = random.nextInt(6000) + 6000;
            }
        }
    }
}
