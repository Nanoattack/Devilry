package io.github.nano.devilry.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.List;

public abstract class PathParticle extends TextureSheetParticle {
    public List<Vector3f> nodes;
    public float speed;
    public float time = 0;
    public ParticleEngine.MutableSpriteSet spriteSet;
    public float frameSpeed;
    public float frameTime;


    protected PathParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, List<Vector3f> nodes, float speed, float frameSpeed, ParticleEngine.MutableSpriteSet spriteSet) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.nodes = nodes;
        this.speed = speed;
        this.spriteSet = spriteSet;
        this.frameSpeed = frameSpeed;
    }

    @Override
    public void tick() {
        super.tick();
        time += speed;
        frameTime++;

        this.setSprite(spriteSet.sprites.get((int) (spriteSet.sprites.size() / (frameTime / frameSpeed))));

    }

    public Vector3f getCatmullromPos(float time) {
        int segment = Mth.floor(time);
        float delta = time - segment;

        float[] x,y,z = y = x = new float[4];
        for (int i = 0; i < 4; i++) {
            x[i] = nodes.get(4 * segment + i).x;
            y[i] = nodes.get(4 * segment + i).y;
            z[i] = nodes.get(4 * segment + i).z;
        }

        float catX = Mth.catmullrom(delta, x[0], x[1], x[2], x[3]);
        float catY = Mth.catmullrom(delta, y[0], y[1], y[2], y[3]);
        float catZ = Mth.catmullrom(delta, z[0], z[1], z[2], z[3]);

        return new Vector3f(catX, catY, catZ);
    }
}
