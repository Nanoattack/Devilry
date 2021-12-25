package com.nano.devilry.container;

import com.nano.devilry.ModMain;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers
{
    public static DeferredRegister<MenuType<?>> CONTAINERS
            = DeferredRegister.create(ForgeRegistries.CONTAINERS, ModMain.MOD_ID);

    public static final RegistryObject<MenuType<MortarContainer>> MORTAR_CONTAINER
            = CONTAINERS.register("mortar_container",
            ()-> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new MortarContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<MenuType<WittlingContainer>> WITTLING_CONTAINER
            = CONTAINERS.register("wittling_container",
            ()-> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new WittlingContainer(windowId, world, pos, inv, inv.player);
            })));

    public static void register(IEventBus eventBus)
    {
        CONTAINERS.register(eventBus);
    }
}
