package io.github.nano.devilry.networking;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.networking.packets.BreakParticlePacket;
import io.github.nano.devilry.networking.packets.UpdateTurningPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ModMain.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(UpdateTurningPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UpdateTurningPacket::new)
                .encoder(UpdateTurningPacket::toBytes)
                .consumerMainThread(UpdateTurningPacket::handle)
                .add();

        net.messageBuilder(BreakParticlePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(BreakParticlePacket::new)
                .encoder(BreakParticlePacket::toBytes)
                .consumerMainThread(BreakParticlePacket::handle)
                .add();;
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
