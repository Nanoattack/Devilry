package io.github.nano.devilry.blockentity;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
//todo

public class ModBlockEntities
{
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModMain.MOD_ID);

    //todo look at that null
    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<BlockEntityType<MortarBlockEntity>> MORTAR_ENTITY
            = BLOCK_ENTITIES.register("mortar_entity", ()-> BlockEntityType.Builder.of(
                    MortarBlockEntity::new, ModBlocks.MORTAR.get()).build(null));

    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<BlockEntityType<DemonicAltarBlockEntity>> DEMONIC_ALTAR_ENTITY
            = BLOCK_ENTITIES.register("demonic_altar_block_entity", () -> BlockEntityType.Builder.of(
                    DemonicAltarBlockEntity::new, ModBlocks.DEMONIC_ALTAR.get()).build(null));

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
