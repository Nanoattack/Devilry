package io.github.nano.devilry.devilry.blockentity;

import io.github.nano.devilry.devilry.block.ModBlocks;
import io.github.nano.devilry.devilry.ModMain;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities
{
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ModMain.MOD_ID);

    public static final RegistryObject<BlockEntityType<MortarEntity>> MORTAR_ENTITY
            = BLOCK_ENTITIES.register("mortar_entity", ()-> BlockEntityType.Builder.of(
                    MortarEntity::new, ModBlocks.MORTAR.get()).build(null));

    public static final RegistryObject<BlockEntityType<WittlingTableEntity>> WITTLING_ENTITY
            = BLOCK_ENTITIES.register("wittling_entity", ()-> BlockEntityType.Builder.of(
            WittlingTableEntity::new, ModBlocks.WITTLING_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<AltarEntity>> ALTAR_ENTITY
            = BLOCK_ENTITIES.register("altar_entity", ()-> BlockEntityType.Builder.of(
            AltarEntity::new, ModBlocks.DEMON_ALTAR.get()).build(null));


    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
