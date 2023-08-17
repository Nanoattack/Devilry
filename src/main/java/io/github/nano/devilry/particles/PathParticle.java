package io.github.nano.devilry.particles;

import io.github.nano.devilry.particles.PathParticleOptions.End;
import io.github.nano.devilry.particles.PathParticleOptions.Interpolation;
import io.github.nano.devilry.util.Utils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine.MutableSpriteSet;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import org.joml.Math;
import org.joml.Vector3f;
import org.lwjgl.system.MathUtil;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public abstract class PathParticle extends TextureSheetParticle {
    public Interpolation interpolation;
    public End end;
    public List<Vector3f> nodes;
    public float speed;
    public float time = 0;
    public SpriteSet spriteSet;
    public float frameSpeed;
    public End spriteEnd;
    public float spriteTime = 1;


    protected PathParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, List<Vector3f> nodes, float speed, float frameSpeed, SpriteSet spriteSet, Interpolation interpolation, End end, End spriteEnd) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.nodes = nodes;
        this.speed = speed;
        this.spriteSet = spriteSet;
        this.frameSpeed = frameSpeed;
        this.interpolation = interpolation;
        this.end = end;
        this.spriteEnd = spriteEnd;
    }

    abstract int spriteCount();

    @Override
    public void tick() {
        super.tick();
        spriteTime = Utils.round(spriteTime + frameSpeed, 1);


        this.setSprite(spriteSet.get(spriteEnd == End.REPEAT ? (int)spriteTime % spriteCount() + 2 : (int) Math.min(spriteCount(), spriteTime), spriteCount()));
        if (spriteEnd == End.VANISH && spriteTime > spriteCount() + 1) {
            this.remove();
        }
        Vector3f interpolatedPos = new Vector3f((float) this.x, (float) this.y, (float) this.z);

        if (interpolation == Interpolation.CATMULLROM ? nodes.size() >= 4 : nodes.size() >= 2) {
            if (interpolation == Interpolation.CATMULLROM) {
                interpolatedPos = getCatmullRomPos(end == End.REPEAT ? time % (nodes.size() - 3) : ((int)time > nodes.size() -4) ? (nodes.size() - 4) + 0.999999f : time);
            } else {
                interpolatedPos = getLinearPos(end == End.REPEAT ? time % (nodes.size() - 1) : ((int)time > nodes.size() -2) ? (nodes.size() - 2) + 0.999999f : time);
            }
        }
        if (end == End.VANISH && interpolation == Interpolation.LINEAR ? ((int)time > nodes.size() - 2) : ((int)time > nodes.size() - 4)) {
            this.remove();
        }
        this.setPos(interpolatedPos.x, interpolatedPos.y, interpolatedPos.z);
        time += speed;
    }

    public Vector3f getCatmullRomPos(float time) {
        int segment = Mth.floor(time);
        float delta = time - segment;

        float[] x = new float[4];
        float[] y = new float[4];
        float[] z = new float[4];

        for (int i = 0; i < 4; i++) {
            x[i] = nodes.get(segment + i).x;
            y[i] = nodes.get(segment + i).y;
            z[i] = nodes.get(segment + i).z;
        }

        float catX = Mth.catmullrom(delta, x[0], x[1], x[2], x[3]);
        float catY = Mth.catmullrom(delta, y[0], y[1], y[2], y[3]);
        float catZ = Mth.catmullrom(delta, z[0], z[1], z[2], z[3]);

        return new Vector3f(catX, catY, catZ);
    }

    public Vector3f getLinearPos(float time) {
        int segment = Mth.floor(time);
        float delta = time - segment;

        float[] x = new float[2];
        float[] y = new float[2];
        float[] z = new float[2];
        for (int i = 0; i < 2; i++) {
            x[i] = nodes.get(segment + i).x;
            y[i] = nodes.get(segment + i).y;
            z[i] = nodes.get(segment + i).z;
        }

        float catX = Mth.lerp(delta, x[0], x[1]);
        float catY = Mth.lerp(delta, y[0], y[1]);
        float catZ = Mth.lerp(delta, z[0], z[1]);

        return new Vector3f(catX, catY, catZ);
    }
}
