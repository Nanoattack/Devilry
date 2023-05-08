package io.github.nano.devilry.entity;

import io.github.nano.devilry.devilry.ModMain;
import io.github.nano.devilry.devilry.entity.custom.OwlEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
//fixme
//todo

public class ModEntityTypes
{
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITIES, ModMain.MOD_ID);

    public static final RegistryObject<EntityType<OwlEntity>> OWL =
            ENTITY_TYPES.register("owl", ()->EntityType.Builder.of(OwlEntity::new,
                         MobCategory.CREATURE).sized(0.5f, 1.5f)
                        .build(new ResourceLocation(ModMain.MOD_ID, "owl").toString()));

    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }
}
