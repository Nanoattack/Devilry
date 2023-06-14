package io.github.nano.devilry.container.entity;

import io.github.nano.devilry.ModMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
//todo

public class ModEntityTypes
{
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ModMain.MOD_ID);

    public static final RegistryObject<EntityType<OwlEntity>> OWL =
            ENTITY_TYPES.register("owl", ()->EntityType.Builder.<OwlEntity>of(OwlEntity::new,
                         MobCategory.CREATURE).sized(0.5f, 1.5f)
                        .build(new ResourceLocation(ModMain.MOD_ID, "owl").toString()));

    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }
}
