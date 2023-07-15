package io.github.nano.devilry.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.NbtTagArgument;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.server.command.EnumArgument;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;

public class PathParticleOptions implements ParticleOptions {
    public enum Interpolation {
        LINEAR,
        CATMULLROM
    }
    public final Interpolation interpolation;
    public final List<Vector3f> nodes;
    public final float speed;

    public static final ParticleOptions.Deserializer<PathParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public PathParticleOptions fromCommand(ParticleType<PathParticleOptions> particleType, StringReader reader) throws CommandSyntaxException {
            Interpolation interpolation = EnumArgument.enumArgument(Interpolation.class).parse(reader);
            reader.expect(' ');
            List<Vector3f> nodes = CompoundTagArgument.compoundTag().parse(reader).getList("nodes", 10)
                    .stream().map(tag -> new Vector3f(((CompoundTag) tag).getFloat("x"), ((CompoundTag) tag).getFloat("y"), ((CompoundTag) tag).getFloat("z"))).toList();
            float speed = reader.readFloat();
            return new PathParticleOptions(interpolation, nodes, speed);
        }

        public PathParticleOptions fromNetwork(ParticleType<PathParticleOptions> type, FriendlyByteBuf buf) {
            return new PathParticleOptions(buf.readEnum(Interpolation.class), buf.readList(FriendlyByteBuf::readVector3f), buf.readFloat());
        }
    };

    public PathParticleOptions(Interpolation interpolation, List<Vector3f> nodes, float speed) {
        this.interpolation = interpolation;
        this.nodes = nodes;
        this.speed = speed;
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return ModParticles.UNDEAD_SOUL.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeEnum(interpolation);
        pBuffer.writeCollection(nodes, FriendlyByteBuf::writeVector3f);
        pBuffer.writeFloat(speed);
    }

    @Override
    public @NotNull String writeToString() {
        return "PathParticleOptions{" +
                "interpolation=" + interpolation +
                ", nodes=" + nodes +
                ", speed=" + speed +
                '}';
    }

    public class Deserializer implements ParticleOptions.Deserializer<PathParticleOptions> {

        @Override
        public PathParticleOptions fromCommand(ParticleType<PathParticleOptions> pParticleType, StringReader pReader) throws CommandSyntaxException {
            return null;
        }

        @Override
        public PathParticleOptions fromNetwork(ParticleType<PathParticleOptions> pParticleType, FriendlyByteBuf pBuffer) {
            return null;
        }
    }
}
