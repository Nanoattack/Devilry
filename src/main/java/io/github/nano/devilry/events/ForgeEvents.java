package io.github.nano.devilry.events;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.blockentity.DemonicAltarBlockEntity;
import io.github.nano.devilry.particles.ModParticles;
import io.github.nano.devilry.particles.PathParticleOptions;
import io.github.nano.devilry.particles.UndeadSoulParticle;
import io.github.nano.devilry.util.ModPOI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = ModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void onDeathEvent(LivingDeathEvent event) {
        Level level = event.getEntity().level();

        if (!level.isClientSide()) {
            var pos = ((ServerLevel) level).getPoiManager().findClosest((Holder<PoiType> predicate) -> predicate.get() == ModPOI.DEMONIC_ALTAR.get(), event.getEntity().blockPosition(), 4, PoiManager.Occupancy.ANY);
            pos.ifPresent(blockPos -> ((DemonicAltarBlockEntity) Objects.requireNonNull(level.getBlockEntity(blockPos))).setSacrifice(event.getEntity()));
        }
        if (level.isClientSide()) {
            var particle = Minecraft.getInstance().particleEngine.createParticle(new PathParticleOptions(ModParticles.UNDEAD_SOUL.get(),PathParticleOptions.Interpolation.LINEAR, List.of(), 20, 20, PathParticleOptions.End.LINGER, PathParticleOptions.End.REPEAT, 20), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), 0, 0, 0);
        }
    }
}
