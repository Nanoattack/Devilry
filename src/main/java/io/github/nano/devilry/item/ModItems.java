package io.github.nano.devilry.item;

import io.github.nano.devilry.ModMain;
import io.github.nano.devilry.block.ModBlocks;
import io.github.nano.devilry.entity.ModEntityTypes;
import io.github.nano.devilry.events.ModSoundEvents;
import io.github.nano.devilry.item.custom.Guano;
import io.github.nano.devilry.item.custom.Knife;
import io.github.nano.devilry.item.custom.Pestle;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
//todo

@SuppressWarnings("unused")
public class ModItems
{
    public static final List<RegistryObject<? extends Item>> DEVILRY_MATERIALS = new ArrayList<>();
    public static final List<RegistryObject<? extends Item>> DEVILRY_BLOCKS = new ArrayList<>();
    public static final List<RegistryObject<? extends Item>> DEVILRY_MISC = new ArrayList<>();

    private enum Tab {
        DEVILRY_MATERIALS,
        DEVILRY_BLOCKS,
        DEVILRY_MISC
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MOD_ID);

    //TIN
    public static final RegistryObject<Item> TIN_INGOT = register("tin_ingot", () -> new Item(new Item.Properties()), Tab.DEVILRY_MATERIALS);

    public static final RegistryObject<Item> TIN_NUGGET = register("tin_nugget", () -> new Item(new Item.Properties()), Tab.DEVILRY_MATERIALS);

    public static final RegistryObject<Item> RAW_TIN = register("raw_tin", () -> new Item(new Item.Properties()), Tab.DEVILRY_MATERIALS);

    //COPPER
    public static final RegistryObject<Item> COPPER_NUGGET = register("copper_nugget", () -> new Item(new Item.Properties()), Tab.DEVILRY_MATERIALS);

    //BRONZE
    public static final RegistryObject<Item> BRONZE_INGOT = register("bronze_ingot", () -> new Item(new Item.Properties()), Tab.DEVILRY_MATERIALS);

    public static final RegistryObject<Item> BRONZE_NUGGET = register("bronze_nugget", () -> new Item(new Item.Properties()), Tab.DEVILRY_MATERIALS);

    public static final RegistryObject<Item> BRONZE_BLEND = register("bronze_blend", () -> new Item(new Item.Properties()), Tab.DEVILRY_MATERIALS);

    public static final RegistryObject<Item> BRONZE_BARS = register("bronze_bars", () -> new BlockItem(ModBlocks.BRONZE_BARS.get(), new Item.Properties()), Tab.DEVILRY_BLOCKS);

    public static final RegistryObject<Item> BRONZE_CHAIN = register("bronze_chain", () -> new BlockItem(ModBlocks.BRONZE_CHAIN.get(), new Item.Properties()), Tab.DEVILRY_BLOCKS);

    public static final RegistryObject<Item> BRONZE_LANTERN = register("bronze_lantern", () -> new BlockItem(ModBlocks.BRONZE_LANTERN.get(), new Item.Properties()), Tab.DEVILRY_BLOCKS);

    //GENERAL

    public static final RegistryObject<Item> OWL_FEATHER = register("owl_feather", () -> new Item(new Item.Properties()), Tab.DEVILRY_MATERIALS);

    public static final RegistryObject<ForgeSpawnEggItem> OWL_SPAWN_EGG = register("owl_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.OWL, 0xf5e9ce, 0x6a4402, new Item.Properties()), Tab.DEVILRY_MISC);

    public static final RegistryObject<Item> ALCHEMICAL_ESSENCE = register("alchemical_essence", () -> new Item(new Item.Properties().stacksTo(16)), Tab.DEVILRY_MATERIALS);

    public static final RegistryObject<Item> BONE_ASH = register("bone_ash", () -> new Item(new Item.Properties()), Tab.DEVILRY_MATERIALS);

    public static final RegistryObject<Item> SALTPETRE = register("saltpetre", () -> new Item(new Item.Properties()), Tab.DEVILRY_MATERIALS);

    public static final RegistryObject<Item> BAT_GUANO = register("bat_guano", () -> new Guano(new Item.Properties()), Tab.DEVILRY_MATERIALS);

    public static final RegistryObject<Item> CURED_FLESH = register("cured_flesh", () -> new Item(new Item.Properties()), Tab.DEVILRY_MATERIALS);

    public static final RegistryObject<Item> MORTAR = register("mortar", () -> new BlockItem(ModBlocks.MORTAR.get(), new Item.Properties()), Tab.DEVILRY_BLOCKS);

    public static final RegistryObject<Item> FLINT_KNIFE  = register("flint_knife", () -> new Knife(new Item.Properties().stacksTo(1).durability(32)), Tab.DEVILRY_MISC);

    public static final RegistryObject<Item> BRONZE_KNIFE  = register("bronze_knife", () -> new Knife(new Item.Properties().stacksTo(1).durability(418)), Tab.DEVILRY_MISC);

    public static final RegistryObject<Item> PESTLE  = register("pestle", () -> new Pestle(new Item.Properties().stacksTo(1).durability(131)), Tab.DEVILRY_MISC);

    @SuppressWarnings("SpellCheckingInspection")
    public static final RegistryObject<Item> NETHERITE_PESTLE  = register("netherite_pestle", () -> new Pestle(new Item.Properties().stacksTo(1).durability(2031)), Tab.DEVILRY_MISC);

    public static final RegistryObject<Item> ENCHANTED_FOREST_MUSIC_DISC  = register("enchanted_forest_music_disc", () -> new RecordItem(1, ModSoundEvents.ENCHANTED_FOREST, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1720), Tab.DEVILRY_MISC);

    public static final RegistryObject<Item> SULPHUR_DUST = register("sulphur_dust", ()-> new Item(new Item.Properties()), Tab.DEVILRY_MATERIALS);

    public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item, Tab tab) {
        var registryObject = ITEMS.register(name, item);
        switch (tab) {
            case DEVILRY_MATERIALS -> DEVILRY_MATERIALS.add(registryObject);
            case DEVILRY_BLOCKS -> DEVILRY_BLOCKS.add(registryObject);
            case DEVILRY_MISC -> DEVILRY_MISC.add(registryObject);
        }
        return registryObject;
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
