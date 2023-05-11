package io.github.nano.devilry.entity.ai;

import io.github.nano.devilry.ModMain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class MemoryRegistry {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORIES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, ModMain.MOD_ID);

    public static final RegistryObject<MemoryModuleType<WalkTarget>> FLY_TARGET = MEMORIES.register("fly_target", () -> new MemoryModuleType<>(Optional.empty()));

    public static void register(IEventBus bus) {
        MEMORIES.register(bus);
    }
}
