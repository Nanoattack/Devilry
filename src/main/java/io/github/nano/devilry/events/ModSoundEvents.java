package io.github.nano.devilry.events;

import io.github.nano.devilry.ModMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
//todo

@SuppressWarnings("unused")
public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModMain.MOD_ID);

    public static final RegistryObject<SoundEvent> ENCHANTED_FOREST =
            registerSoundEvent("enchanted_forest");

    public static final RegistryObject<SoundEvent> KNIFE_SLASH_METAL =
            registerSoundEvent("knife_slash");

    public static final RegistryObject<SoundEvent> CLOTH_RIP =
            registerSoundEvent("cloth_rip");

    public static final RegistryObject<SoundEvent> MORTAR_GRIND =
            registerSoundEvent("mortar_grind");

    public static final RegistryObject<SoundEvent> FESTER =
            registerSoundEvent("fester");

    public static final RegistryObject<SoundEvent> OWL_FLAP =
            registerSoundEvent("owl_flap");

    public static final RegistryObject<SoundEvent> OWL_AMBIENT =
            registerSoundEvent("owl_ambience");

    public static final RegistryObject<SoundEvent> OWL_HURT =
            registerSoundEvent("owl_hurt");

    public static final RegistryObject<SoundEvent> OWL_DEATH =
            registerSoundEvent("owl_death");


    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ModMain.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}