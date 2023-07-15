package io.github.nano.devilry.util;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.block.ModBlocks;
import io.github.nano.devilry.block.custom.DemonicAltarSide;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModPOI {
    public static final DeferredRegister<PoiType> POI = DeferredRegister.create(ForgeRegistries.POI_TYPES, ModMain.MOD_ID);

    public static final RegistryObject<PoiType> DEMONIC_ALTAR = POI.register("demonic_altar", () -> new PoiType(Set.of(
            ModBlocks.DEMONIC_ALTAR.get().defaultBlockState().setValue(DemonicAltarSide.FACING, Direction.NORTH),
            ModBlocks.DEMONIC_ALTAR.get().defaultBlockState().setValue(DemonicAltarSide.FACING, Direction.EAST),
            ModBlocks.DEMONIC_ALTAR.get().defaultBlockState().setValue(DemonicAltarSide.FACING, Direction.SOUTH),
            ModBlocks.DEMONIC_ALTAR.get().defaultBlockState().setValue(DemonicAltarSide.FACING, Direction.WEST)
    ), 0, 8));
}
