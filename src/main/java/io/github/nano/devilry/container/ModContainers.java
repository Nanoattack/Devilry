package io.github.nano.devilry.container;

import io.github.nano.devilry.ModMain;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
//todo

public class ModContainers
{
    public static DeferredRegister<MenuType<?>> CONTAINERS
            = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ModMain.MOD_ID);

    public static final RegistryObject<MenuType<MortarMenu>> MORTAR_CONTAINER
            = CONTAINERS.register("mortar_container",
            ()-> IForgeMenuType.create(MortarMenu::new));

    public static void register(IEventBus eventBus)
    {
        CONTAINERS.register(eventBus);
    }
}
