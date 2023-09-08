package io.github.nano.devilry.particles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CircleParticle extends Particle {
    public static ParticleRenderType CIRCLE_RENDER_TYPE = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder pBuilder, TextureManager pTextureManager) {
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            pBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        }

        @Override
        public void end(Tesselator pTesselator) {
            pTesselator.end();
        }

        public String toString() {
            return "DEVILRY_CIRCLE_RENDER_TYPE";
        }
    };

    private final AABB bounds;
    public int radius;
    private static final int MAX_SIZE = 100;
    protected CircleParticle(ClientLevel pLevel, double pX, double pY, double pZ, AABB bounds) {
        super(pLevel, pX, pY, pZ);
        this.bounds = bounds;
        lifetime = 300;
        radius = 0;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return CIRCLE_RENDER_TYPE;
    }

    public void tick() {
        super.tick();
        radius = age / (100 / MAX_SIZE);
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        Vec3 player = pRenderInfo.getPosition();

        List<Vector2i> circle = generateCircle(0, 0, radius);
        bounce(circle);
        for (Vector2i pixel : circle) {
            float x = (float) (this.x + (pixel.x * 0.0625) - player.x);
            float y = (float) (this.y - player.y);
            float z = (float) (this.z + (pixel.y * 0.0625) - player.z);

            pBuffer.vertex(x, y, z).color(120, 30, 35, 255).endVertex();
            pBuffer.vertex(x, y, z + 0.0625).color(120, 30, 35, 255).endVertex();
            pBuffer.vertex(x + 0.0625, y, z + 0.0625).color(120, 30, 35, 255).endVertex();
            pBuffer.vertex(x + 0.0625, y, z).color(120, 30, 35, 255).endVertex();
        }
    }

    private void bounce(List<Vector2i> circle) {
        outer:
        while (true) {
            for (Vector2i pixel : circle) {
                if (pixel.x * 0.0625 + this.x < bounds.minX) {
                    pixel.x += 2 * (bounds.minX - (0.0625 * pixel.x + this.x)) / 0.0625;
                } else if (pixel.x * 0.0625 + this.x > bounds.maxX) {
                    pixel.x -= 2 * (0.0625 * pixel.x + this.x - bounds.maxX) / 0.0625;
                }
                if (pixel.y * 0.0625 + this.z < bounds.minZ) {
                    pixel.y += 2 * (bounds.minZ - (0.0625 * pixel.y + this.z)) / 0.0625;
                } else if (pixel.y * 0.0625 + this.z > bounds.maxZ) {
                    pixel.y -= 2 * (0.0625 * pixel.y + this.z - bounds.maxZ) / 0.0625;
                }
            }
            for (Vector2i pixel : circle) {
                if (pixel.x * 0.0625 + this.x < bounds.minX ||
                        pixel.x * 0.0625 + this.x > bounds.maxX ||
                        pixel.y * 0.0625 + this.z < bounds.minZ ||
                        pixel.y * 0.0625 + this.z > bounds.maxZ) {
                    continue outer;
                }
            }
            break;
        }
    }

    private List<Vector2i> generateCircle(int xOffset, int yOffset, int radius) {
        List<Vector2i> octant = new ArrayList<>();
        Set<Vector2i> list = new HashSet<>();
        int x = 0, y = radius;
        int d = 3 - 2 * radius;
        octant.add(new Vector2i(x, y));
        if (radius != 0) {
            octant.add(new Vector2i(x + 1, y));
        }
        while (y >= x) {
            if (d > 0) {
                d += 4 * (++x - --y) + 10;
            } else {
                d += 4 * ++x + 6;
            }
            octant.add(new Vector2i(x + 1, y));
        }
        for (int i = 0, octantSize = octant.size(); i < octantSize; i++) {
            Vector2i vector2i = octant.get(i);
            list.add(new Vector2i(-vector2i.x() + xOffset, vector2i.y() + yOffset));
            list.add(new Vector2i(-vector2i.x() + xOffset, -vector2i.y() + yOffset));
            list.add(new Vector2i(vector2i.x() + xOffset, vector2i.y() + yOffset));
            list.add(new Vector2i(vector2i.x() + xOffset, -vector2i.y() + yOffset));

            if (i >= octantSize - 2) {
                break;
            }

            list.add(new Vector2i(vector2i.y() + xOffset, -vector2i.x() + yOffset));
            list.add(new Vector2i(-vector2i.y() + xOffset, -vector2i.x() + yOffset));
            list.add(new Vector2i(vector2i.y() + xOffset, vector2i.x() + yOffset));
            list.add(new Vector2i(-vector2i.y() + xOffset, vector2i.x() + yOffset));
        }
        return new ArrayList<>(list);
    }

    public static class Provider implements ParticleProvider<CircleParticleOptions> {
        public Particle createParticle(CircleParticleOptions particleType, ClientLevel level, double pX, double pY, double pZ,
                                       double pXSpeed, double pYSpeed, double pZSpeed) {

            return new CircleParticle(level, pX, pY, pZ, particleType.bounds);
        }
    }
}
