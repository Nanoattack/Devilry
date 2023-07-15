package io.github.nano.devilry.particles;

import io.github.nano.devilry.ModMain;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ModMain.MOD_ID);

    public static final RegistryObject<SimpleParticleType> UNDEAD_SOUL = PARTICLE_TYPES.register("undead_soul", () -> new SimpleParticleType(true));
}
