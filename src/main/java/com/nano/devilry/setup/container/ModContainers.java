package com.nano.devilry.setup.container;

import com.nano.devilry.setup.Registration;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

public class ModContainers
{
    public static final RegistryObject<ContainerType<AlchemyStationContainer>> ALCHEMY_STATION_CONTAINER
            = Registration.CONTAINERS.register("alchemy_station_container",
            ()-> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getCommandSenderWorld();
                return new AlchemyStationContainer(windowId, world, pos, inv, inv.player);
            })));

    public static void register() {}
}
