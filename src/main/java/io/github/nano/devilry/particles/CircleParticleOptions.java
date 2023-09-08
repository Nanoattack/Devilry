package io.github.nano.devilry.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;

public class CircleParticleOptions implements ParticleOptions {
    public final AABB bounds;
    private final ParticleType<?> type;

    public static Codec<CircleParticleOptions> Codec(ParticleType<?> type) {
        return RecordCodecBuilder.create((instance) ->
                instance.group(
                        ExtraCodecs.VECTOR3F.listOf().comapFlatMap(
                                        vector -> Util.fixedSize(vector, 2)
                                                .map(v -> new AABB(v.get(0).x, v.get(0).y, v.get(0).z, v.get(1).x, v.get(1).y, v.get(1).z)),
                                        from -> List.of(
                                                new Vector3f((float) from.minX, (float) from.minY, (float) from.minZ),
                                                new Vector3f((float) from.maxX, (float) from.maxY, (float) from.maxZ)))
                                .fieldOf("mask").forGetter(getter ->
                                        getter.bounds
                                )).apply(instance, aabb -> new CircleParticleOptions(type, aabb)));
    }

    public static final Deserializer<CircleParticleOptions> DESERIALIZER = new Deserializer<>() {
        public CircleParticleOptions fromCommand(ParticleType<CircleParticleOptions> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            double x1 = reader.readDouble();
            reader.expect(' ');
            double y1 = reader.readDouble();
            reader.expect(' ');
            double z1 = reader.readDouble();
            reader.expect(' ');
            double x2 = reader.readDouble();
            reader.expect(' ');
            double y2 = reader.readDouble();
            reader.expect(' ');
            double z2 = reader.readDouble();
            return new CircleParticleOptions(particleType, new AABB(x1, y1, z1, x2, y2, z2));
        }

        public CircleParticleOptions fromNetwork(ParticleType<CircleParticleOptions> type, FriendlyByteBuf buf) {
            return new CircleParticleOptions(type, new AABB(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble()));
        }
    };

    public CircleParticleOptions(ParticleType<?> type, AABB mask) {
        this.bounds = mask;
        this.type = type;
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return this.type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeDouble(this.bounds.minX);
        pBuffer.writeDouble(this.bounds.minY);
        pBuffer.writeDouble(this.bounds.minZ);
        pBuffer.writeDouble(this.bounds.maxX);
        pBuffer.writeDouble(this.bounds.maxY);
        pBuffer.writeDouble(this.bounds.maxZ);
    }

    @Override
    public String writeToString() {
        return "MaskedParticleOptions{" +
                "mask=" + bounds +
                '}';
    }
}
