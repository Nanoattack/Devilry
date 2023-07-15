package io.github.nano.devilry.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.List;

public class UndeadSoulParticle extends RisingParticle {
    private final SpriteSet sprites;
    public final List<Vector3f> nodes;
    protected UndeadSoulParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed, List<Vector3f> nodes, float speed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);

        this.friction = 0.8f;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.sprites = spriteSet;

        this.quadSize *= 0.75f;
        this.lifetime = 20;
        this.setSpriteFromAge(spriteSet);

        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;

        this.nodes = nodes;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        return 0x00ff00;
    }

    public static class Provider implements ParticleProvider<PathParticleOptions> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(PathParticleOptions particleType, ClientLevel level, double pX, double pY, double pZ,
                                       double pXSpeed, double pYSpeed, double pZSpeed) {

            return new UndeadSoulParticle(level, pX, pY, pZ, this.spriteSet, pXSpeed, pYSpeed, pZSpeed, particleType.nodes, particleType.speed);
        }
    }
}
