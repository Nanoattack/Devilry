package com.nano.devilry.setup;

import com.nano.devilry.ModMain;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;

public class ModItems
{
    public static final RegistryObject<Item> TIN_INGOT = Registration.ITEMS.register("tin_ingot",()->
            new Item(new Item.Properties().tab(ModMain.DEVILRY_TAB)));
    public static final RegistryObject<Item> TIN_NUGGET = Registration.ITEMS.register("tin_nugget",()->
            new Item(new Item.Properties().tab(ModMain.DEVILRY_TAB)));
    public static final RegistryObject<Item> COPPER_INGOT = Registration.ITEMS.register("copper_ingot",()->
            new Item(new Item.Properties().tab(ModMain.DEVILRY_TAB)));
    public static final RegistryObject<Item> COPPER_NUGGET = Registration.ITEMS.register("copper_nugget",()->
            new Item(new Item.Properties().tab(ModMain.DEVILRY_TAB)));
    public static final RegistryObject<Item> BRONZE_INGOT = Registration.ITEMS.register("bronze_ingot",()->
            new Item(new Item.Properties().tab(ModMain.DEVILRY_TAB)));
    public static final RegistryObject<Item> BRONZE_NUGGET = Registration.ITEMS.register("bronze_nugget",()->
            new Item(new Item.Properties().tab(ModMain.DEVILRY_TAB)));
    public static final RegistryObject<Item> BRONZE_BLEND = Registration.ITEMS.register("bronze_dust",()->
            new Item(new Item.Properties().tab(ModMain.DEVILRY_TAB)));
    public static final RegistryObject<Item> BONE_ASH = Registration.ITEMS.register("bone_ash",()->
            new Item(new Item.Properties().tab(ModMain.DEVILRY_TAB)));
    public static final RegistryObject<Item> ALCHEMICAL_BLEND = Registration.ITEMS.register("alchemical_blend",()->
            new Item(new Item.Properties().tab(ModMain.DEVILRY_TAB)));
    public static final RegistryObject<Item> ELDRITCH_IDOL = Registration.ITEMS.register("eldritch_idol",()->
            new Item(new Item.Properties().tab(ModMain.DEVILRY_TAB)));
    public static final RegistryObject<Item> ELDRITCH_IDOL_DORMANT = Registration.ITEMS.register("eldritch_idol_dormant",()->
            new Item(new Item.Properties().tab(ModMain.DEVILRY_TAB)));
    public static final RegistryObject<BlockItem> DEVILS_SNARE_SEED =
            Registration.ITEMS.register("devils_snare_seed",
                    ()->new BlockItem(ModBlocks.DEVILS_SNARE_CROP.get(), new Item.Properties().tab(ModMain.DEVILRY_TAB)));

    // Armour

    public static final RegistryObject<Item> CORINTHIAN_BRONZE_HELMET =
            Registration.ITEMS.register("corinthian_bronze_helmet",
            ()-> new ArmorItem(ModArmorMaterial.CORINTHIAN_BRONZE, EquipmentSlotType.HEAD,
                    new Item.Properties().tab(ModMain.DEVILRY_TAB)));

    public static final RegistryObject<Item> CORINTHIAN_BRONZE_CHESTPLATE =
            Registration.ITEMS.register("corinthian_bronze_chestplate",
                    ()-> new ArmorItem(ModArmorMaterial.CORINTHIAN_BRONZE, EquipmentSlotType.CHEST,
                            new Item.Properties().tab(ModMain.DEVILRY_TAB)));

    public static final RegistryObject<Item> CORINTHIAN_BRONZE_LEGGINGS =
            Registration.ITEMS.register("corinthian_bronze_leggings",
                    ()-> new ArmorItem(ModArmorMaterial.CORINTHIAN_BRONZE, EquipmentSlotType.LEGS,
                            new Item.Properties().tab(ModMain.DEVILRY_TAB)));

    public static final RegistryObject<Item> CORINTHIAN_BRONZE_BOOTS =
            Registration.ITEMS.register("corinthian_bronze_boots",
                    ()-> new ArmorItem(ModArmorMaterial.CORINTHIAN_BRONZE, EquipmentSlotType.FEET,
                            new Item.Properties().tab(ModMain.DEVILRY_TAB)));

    static void register() {}

    public enum ModArmorMaterial implements IArmorMaterial
    {
        CORINTHIAN_BRONZE( 12, new int[] { 2, 4, 5, 2}, 22, SoundEvents.ARMOR_EQUIP_CHAIN, Ingredient.of(new ItemStack(ModItems.BRONZE_INGOT.get())), ModMain.MOD_ID + ":corinthian_bronze", 0, 0.1f);

        private static final int[] max_damage_array = new int[] {13, 15, 16, 11};
        private final int durability;
        private final int[] damageReductionAmountArray;
        private final int enchantability;
        private final SoundEvent soundEvent;
        private final Ingredient repairMaterial;
        private final String name;
        private final float toughness;
        private final float knockbackResistance;

        ModArmorMaterial(int durability, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, Ingredient repairMaterial, String name, float toughness, float knockbackResistance) {
            this.durability = durability;
            this.damageReductionAmountArray = damageReductionAmountArray;
            this.enchantability = enchantability;
            this.soundEvent = soundEvent;
            this.repairMaterial = repairMaterial;
            this.name = name;
            this.toughness = toughness;
            this.knockbackResistance = knockbackResistance;
        }

        @Override
        public int getDurabilityForSlot(EquipmentSlotType p_200896_1_)
        {
            return max_damage_array[p_200896_1_.getIndex()] * this.durability;
        }
        //haven't set this up yet just remembering it exists
        public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer)
        {
            return stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getMaterial() == ModArmorMaterial.CORINTHIAN_BRONZE;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlotType p_200902_1_) {
            return damageReductionAmountArray[p_200902_1_.getIndex()];
        }

        @Override
        public int getEnchantmentValue() {
            return enchantability;
        }

        @Override
        public SoundEvent getEquipSound() {
            return soundEvent;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return repairMaterial;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public float getToughness() {
            return toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return knockbackResistance;
        }
    }
}
