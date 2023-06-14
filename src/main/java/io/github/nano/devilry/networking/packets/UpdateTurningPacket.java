package io.github.nano.devilry.networking.packets;

import io.github.nano.devilry.blockentity.MortarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateTurningPacket {
    BlockPos blockEntityPos;
    public UpdateTurningPacket(BlockPos blockPos) {
        blockEntityPos = blockPos;
    }

    public UpdateTurningPacket(FriendlyByteBuf buf) {
        blockEntityPos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(blockEntityPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player.level().getBlockEntity(blockEntityPos) instanceof MortarBlockEntity blockEntity) {
                blockEntity.isTurning = false;
            }
        });
        return true;
    }
}
