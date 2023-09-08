package io.github.nano.devilry.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraftforge.server.command.EnumArgument;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;

public class PathParticleOptions implements ParticleOptions {
    public enum Interpolation implements StringRepresentable{
        LINEAR,
        CATMULLROM;

        @Override
        public String getSerializedName() {
            return this == LINEAR ? "linear" : "catmullrom";
        }
    }

    public enum End implements StringRepresentable{
        LINGER,
        VANISH,
        REPEAT;

        @Override
        public String getSerializedName() {
            if (this == LINGER) {
                return "linger";
            } else if (this == VANISH) {
                return "vanish";
            } else {
                return "repeat";
            }
        }
    }
    public final Interpolation interpolation;
    public final End end;
    public final List<Vector3f> nodes;
    public final float speed;
    public final float frameSpeed;
    public final End spriteEnd;
    public final int lifeTime;
    private final ParticleType<?> type;

    public static Codec<PathParticleOptions> Codec(ParticleType<?> type) {
        return RecordCodecBuilder.create((instance) ->
                instance.group(StringRepresentable.fromEnum(Interpolation::values).fieldOf("interpolation").forGetter(options ->
                        options.interpolation
                ), Codec.list(ExtraCodecs.VECTOR3F).fieldOf("nodes").forGetter(options ->
                        options.nodes
                ), Codec.FLOAT.fieldOf("speed").forGetter(options ->
                        options.speed
                ), Codec.FLOAT.fieldOf("frameSpeed").forGetter(options ->
                        options.frameSpeed
                ), StringRepresentable.fromEnum(End::values).fieldOf("end").forGetter(options ->
                        options.end
                ), StringRepresentable.fromEnum(End::values).fieldOf("spriteEnd").forGetter(options ->
                        options.spriteEnd
                ), Codec.INT.fieldOf("lifeTime").forGetter(options ->
                        options.lifeTime
                )).apply(instance, (Interpolation interpolation, List<Vector3f> nodes, Float speed, Float frameSpeed, End end, End spriteEnd, Integer lifeTime) -> new PathParticleOptions(type, interpolation, nodes, speed, frameSpeed, end, spriteEnd, lifeTime)));
    }
    public static final ParticleOptions.Deserializer<PathParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public PathParticleOptions fromCommand(ParticleType<PathParticleOptions> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            Interpolation interpolation = EnumArgument.enumArgument(Interpolation.class).parse(reader);
            reader.expect(' ');
            List<Vector3f> nodes = CompoundTagArgument.compoundTag().parse(reader).getList("nodes", 10)
                    .stream().map(tag -> new Vector3f(((CompoundTag) tag).getFloat("x"), ((CompoundTag) tag).getFloat("y"), ((CompoundTag) tag).getFloat("z"))).toList();
            reader.expect(' ');
            float speed = reader.readFloat();
            reader.expect(' ');
            float frameSpeed = reader.readFloat();
            reader.expect(' ');
            End end = EnumArgument.enumArgument(End.class).parse(reader);
            reader.expect(' ');
            End spriteEnd = EnumArgument.enumArgument(End.class).parse(reader);
            reader.expect(' ');
            int lifeTime = reader.readInt();
            return new PathParticleOptions(particleType, interpolation, nodes, speed, frameSpeed, end, spriteEnd, lifeTime);
        }

        public PathParticleOptions fromNetwork(ParticleType<PathParticleOptions> type, FriendlyByteBuf buf) {
            return new PathParticleOptions(type, buf.readEnum(Interpolation.class), buf.readList(FriendlyByteBuf::readVector3f), buf.readFloat(), buf.readFloat(), buf.readEnum(End.class), buf.readEnum(End.class), buf.readInt());
        }
    };

    public PathParticleOptions(ParticleType<?> type, Interpolation interpolation, List<Vector3f> nodes, float speed, float frameSpeed, End end, End spriteEnd, int lifeTime) {
        this.interpolation = interpolation;
        this.nodes = nodes;
        this.speed = speed;
        this.frameSpeed = frameSpeed;
        this.end = end;
        this.spriteEnd = spriteEnd;
        this.lifeTime = lifeTime;
        this.type = type;
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return this.type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeEnum(interpolation);
        pBuffer.writeCollection(nodes, FriendlyByteBuf::writeVector3f);
        pBuffer.writeFloat(speed);
        pBuffer.writeFloat(frameSpeed);
        pBuffer.writeEnum(end);
        pBuffer.writeEnum(spriteEnd);
        pBuffer.writeInt(lifeTime);
    }

    @Override
    public @NotNull String writeToString() {
        return "PathParticleOptions{" +
                "interpolation=" + interpolation +
                ", nodes=" + nodes +
                ", speed=" + speed +
                ", frameSpeed=" + frameSpeed +
                ", end=" + end +
                ", spriteEnd=" + spriteEnd +
                ", lifeTime=" + lifeTime +
                '}';
    }
}
