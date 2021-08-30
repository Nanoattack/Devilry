package com.nano.devilry.data.client;

import com.nano.devilry.ModMain;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider
{
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ModMain.MOD_ID, existingFileHelper);
    }
@Override
    protected void registerModels() {
    withExistingParent("tin_ore", modLoc("block/tin_ore"));
    withExistingParent("tin_block", modLoc("block/tin_block"));
    withExistingParent("copper_ore", modLoc("block/copper_ore"));
    withExistingParent("copper_block", modLoc("block/copper_block"));
    withExistingParent("bronze_block", modLoc("block/bronze_block"));


    ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
    // General

    builder(itemGenerated, "eldritch_idol");
    builder(itemGenerated, "eldritch_idol_dormant");
    builder(itemGenerated, "devils_snare_seed");
    builder(itemGenerated, "bone_ash");
    builder(itemGenerated, "alchemical_blend");

    // Ore Related

    builder(itemGenerated, "tin_ingot");
    builder(itemGenerated, "tin_nugget");
    builder(itemGenerated, "copper_ingot");
    builder(itemGenerated, "copper_nugget");
    builder(itemGenerated, "bronze_ingot");
    builder(itemGenerated, "bronze_nugget");
    builder(itemGenerated, "bronze_dust");

    // Armor Related

    builder(itemGenerated, "corinthian_bronze_helmet");
    builder(itemGenerated, "corinthian_bronze_chestplate");
    builder(itemGenerated, "corinthian_bronze_leggings");
    builder(itemGenerated, "corinthian_bronze_boots");
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}
