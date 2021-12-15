package com.nano.devilry.events;

import com.nano.devilry.ModMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModMain.MOD_ID);

    public static final RegistryObject<SoundEvent> ENCHANTED_FOREST =
            registerSoundEvent("enchanted_forest");

    public static final RegistryObject<SoundEvent> KNIFE_SLASH =
            registerSoundEvent("knife_slash");

    public static final RegistryObject<SoundEvent> OWL_FLAP =
            registerSoundEvent("owl_flap");

    public static final RegistryObject<SoundEvent> OWL_AMBIENT =
            registerSoundEvent("owl_ambient");

    public static final RegistryObject<SoundEvent> OWL_HURT =
            registerSoundEvent("owl_hurt");

    public static final RegistryObject<SoundEvent> OWL_DEATH =
            registerSoundEvent("owl_death");


    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(ModMain.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}