package io.github.nano.devilry.particles;

import com.mojang.serialization.Codec;
import io.github.nano.devilry.ModMain;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ModMain.MOD_ID);

    public static final RegistryObject<ParticleType<PathParticleOptions>> UNDEAD_SOUL = PARTICLE_TYPES.register("undead_soul", () -> new ParticleType<PathParticleOptions>(
            false,
            PathParticleOptions.DESERIALIZER
    ) {
        @Override
        public @NotNull Codec<PathParticleOptions> codec() {
            return PathParticleOptions.CODEC;
        }
    });
}
