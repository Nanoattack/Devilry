package io.github.nano.devilry.events;

import io.github.nano.devilry.devilry.ModMain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//TY Pale Imitations/Brad (Dev of Schools of Magic Mod) for the code!
//fixme
//todo

@Mod.EventBusSubscriber(modid = ModMain.MOD_ID)
public final class LootInjector {
    public static final class Tables {
        private static final Map<ResourceLocation, ResourceLocation> MAP = new HashMap<>();

        public static final ResourceLocation ENTITIES_ELDER_GUARDIAN = inject(new ResourceLocation("entities/owl"));

        private Tables() {}

        public static Collection<ResourceLocation> getValues() {
            return MAP.values();
        }

        public static Optional<ResourceLocation> get(ResourceLocation lootTable) {
            return Optional.ofNullable(MAP.get(lootTable));
        }

        private static ResourceLocation inject(ResourceLocation lootTable) {
            ResourceLocation ret = new ResourceLocation(ModMain.MOD_ID,"inject/" + lootTable.getPath());
            MAP.put(lootTable, ret);
            return ret;
        }
    }

    public LootInjector() {}

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        Tables.get(event.getName()).ifPresent(injectorName -> {
            event.getTable().addPool(
                    LootPool.lootPool()
                            .name("extract_injected")
                            .add(LootTableReference.lootTableReference(injectorName))
                            .build()
            );
        });
    }
}