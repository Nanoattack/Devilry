package com.nano.devilry.item;

import com.nano.devilry.ModMain;
import com.nano.devilry.block.ModBlocks;
import com.nano.devilry.entity.ModEntityTypes;
import com.nano.devilry.events.ModSoundEvents;
import com.nano.devilry.item.custom.Guano;
import com.nano.devilry.item.custom.Knife;
import com.nano.devilry.item.custom.Pestle;
import com.nano.devilry.item.custom.SupplierSpawnEggItem;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MOD_ID);

    //TIN
    public static final RegistryObject<Item> TIN_INGOT = ITEMS.register("tin_ingot", ()-> new Item(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));

    public static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget", ()-> new Item(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));

    public static final RegistryObject<Item> RAW_TIN = ITEMS.register("raw_tin", ()-> new Item(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));

    //COPPER
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", ()-> new Item(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));

    //BRONZE
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", ()-> new Item(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));

    public static final RegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget", ()-> new Item(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));

    public static final RegistryObject<Item> BRONZE_BLEND = ITEMS.register("bronze_blend", ()-> new Item(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));

    public static final RegistryObject<Item> BRONZE_BARS = ITEMS.register("bronze_bars",
            () -> new BlockItem(ModBlocks.BRONZE_BARS.get(), new Item.Properties().tab(ModItemGroups.MOD_BLOCK_GROUP)));

    public static final RegistryObject<Item> BRONZE_CHAIN = ITEMS.register("bronze_chain",
            () -> new BlockItem(ModBlocks.BRONZE_CHAIN.get(), new Item.Properties().tab(ModItemGroups.MOD_BLOCK_GROUP)));

    public static final RegistryObject<Item> BRONZE_LANTERN = ITEMS.register("bronze_lantern",
            () -> new BlockItem(ModBlocks.BRONZE_LANTERN.get(), new Item.Properties().tab(ModItemGroups.MOD_BLOCK_GROUP)));

    //GENERAL

    public static final RegistryObject<Item> OWL_FEATHER = ITEMS.register("owl_feather", ()-> new Item(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));

    public static final RegistryObject<Item> OWL_SPAWN_EGG = ITEMS.register("owl_spawn_egg", ()-> new SupplierSpawnEggItem(ModEntityTypes.OWL::get, 0xf5e9ce, 0x6a4402,
            new Item.Properties().tab(ModItemGroups.MOD_MISC_GROUP)));

    public static final RegistryObject<Item> ALCHEMICAL_ESSENCE  = ITEMS.register("alchemical_essence", ()-> new Item(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP).stacksTo(16)));

    public static final RegistryObject<Item> BONE_ASH = ITEMS.register("bone_ash", ()-> new Item(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));

    public static final RegistryObject<Item> SALTPETRE = ITEMS.register("saltpetre", ()-> new Item(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));

    public static final RegistryObject<Item> BAT_GUANO = ITEMS.register("bat_guano", ()-> new Guano(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));

    public static final RegistryObject<Item> CURED_FLESH = ITEMS.register("cured_flesh", ()-> new Item(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));

    public static final RegistryObject<Item> MORTAR = ITEMS.register("mortar",
            () -> new BlockItem(ModBlocks.MORTAR.get(), new Item.Properties().tab(ModItemGroups.MOD_BLOCK_GROUP)));

    public static final RegistryObject<Item> DEMON_ALTAR = ITEMS.register("demon_altar",
            () -> new BlockItem(ModBlocks.DEMON_ALTAR.get(), new Item.Properties().tab(ModItemGroups.MOD_BLOCK_GROUP)));

    public static final RegistryObject<Item> DEMON_ALTAR_SIDE_TEST = ITEMS.register("demon_altar_side_test",
            () -> new BlockItem(ModBlocks.DEMON_ALTAR_SIDE.get(), new Item.Properties().tab(ModItemGroups.MOD_BLOCK_GROUP)));

    public static final RegistryObject<Item> STOLAS_EFFIGY = ITEMS.register("stolas_effigy",
            () -> new BlockItem(ModBlocks.STOLAS_EFFIGY.get(), new Item.Properties().tab(ModItemGroups.MOD_BLOCK_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> FLINT_KNIFE  = ITEMS.register("flint_knife", ()-> new Knife(new Item.Properties().tab(ModItemGroups.MOD_MISC_GROUP).stacksTo(1).durability(32)));

    public static final RegistryObject<Item> BRONZE_KNIFE  = ITEMS.register("bronze_knife", ()-> new Knife(new Item.Properties().tab(ModItemGroups.MOD_MISC_GROUP).stacksTo(1).durability(418)));

    public static final RegistryObject<Item> PESTLE  = ITEMS.register("pestle", ()-> new Pestle(new Item.Properties().tab(ModItemGroups.MOD_MISC_GROUP).stacksTo(1).durability(131)));

    public static final RegistryObject<Item> NETHERITE_PESTLE  = ITEMS.register("netherite_pestle", ()-> new Pestle(new Item.Properties().tab(ModItemGroups.MOD_MISC_GROUP).stacksTo(1).durability(2031)));

    public static final RegistryObject<Item> ENCHANTED_FOREST_MUSIC_DISC  = ITEMS.register("enchanted_forest_music_disc",
            ()-> new RecordItem(1, () -> ModSoundEvents.ENCHANTED_FOREST.get(),
                    new Item.Properties().tab(ModItemGroups.MOD_MISC_GROUP).stacksTo(1).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> SULPHUR_DUST = ITEMS.register("sulphur_dust", ()-> new Item(new Item.Properties().tab(ModItemGroups.MOD_MATERIAL_GROUP)));


    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
