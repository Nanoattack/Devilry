package io.github.nano.devilry.networking.packets;

import io.github.nano.devilry.blockentity.MortarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BreakParticlePacket {
    BlockPos blockEntityPos;

    public BreakParticlePacket(BlockPos blockPos) {
        blockEntityPos = blockPos;
    }

    public BreakParticlePacket(FriendlyByteBuf buf) {
        blockEntityPos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(blockEntityPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Minecraft.getInstance().level.addDestroyBlockEffect(blockEntityPos, Minecraft.getInstance().level.getBlockState(blockEntityPos));
        });
        return true;
    }
}
