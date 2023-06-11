package io.github.nano.devilry;

import io.github.nano.devilry.block.ModBlocks;
import io.github.nano.devilry.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabRegistry {
    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModMain.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DEVILRY_MATERIALS = CREATIVE_MODE_TABS.register("devilry_materials",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ALCHEMICAL_ESSENCE.get()))
                    .title(Component.translatableWithFallback("devilry.tab.devilry_materials", "Devilry Materials"))
                    .displayItems((feature, output) -> ModItems.DEVILRY_MATERIALS.stream()
                            .map(RegistryObject::get).forEach(output::accept)).build());

    public static final RegistryObject<CreativeModeTab> DEVILRY_BLOCKS = CREATIVE_MODE_TABS.register("devilry_blocks",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.POLISHED_LIMESTONE.get()))
                    .title(Component.translatableWithFallback("devilry.tab.devilry_blocks", "Devilry Blocks"))
                    .displayItems((params, output) -> ModItems.DEVILRY_BLOCKS.stream()
                            .map(RegistryObject::get).forEach(output::accept)).withTabsBefore(CreativeModeTabs.SPAWN_EGGS).build());

    public static final RegistryObject<CreativeModeTab> DEVILRY_MISC = CREATIVE_MODE_TABS.register("devilry_misc",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.PESTLE.get()))
                    .title(Component.translatableWithFallback("devilry.tab.devilry_misc", "Devilry Misc"))
                    .displayItems((params, output) -> ModItems.DEVILRY_MISC.stream()
                            .map(RegistryObject::get).forEach(output::accept)).withTabsBefore(CreativeModeTabs.SPAWN_EGGS).build());
}
