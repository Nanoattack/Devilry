package io.github.nano.devilry.container;

import io.github.nano.devilry.ModMain;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
//todo

public class ModContainers
{
    public static DeferredRegister<MenuType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ModMain.MOD_ID);

    public static final RegistryObject<MenuType<MortarMenu>> MORTAR_CONTAINER =
            CONTAINERS.register("mortar_container",
            ()-> IForgeMenuType.create(MortarMenu::new));

    public static final RegistryObject<MenuType<DemonicAltarMenu>> DEMONIC_ALTAR_CONTAINER =
            CONTAINERS.register("demonic_altar_container",
            ()-> IForgeMenuType.create(DemonicAltarMenu::new));

    public static final RegistryObject<MenuType<CarvingMenu>> CARVING_CONTAINER =
            CONTAINERS.register("carving_container", () -> IForgeMenuType.create(CarvingMenu::new));

    public static void register(IEventBus eventBus)
    {
        CONTAINERS.register(eventBus);
    }
}
