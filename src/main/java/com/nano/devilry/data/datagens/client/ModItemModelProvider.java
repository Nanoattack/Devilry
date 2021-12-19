package com.nano.devilry.data.datagens.client;

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

    withExistingParent("tin_block", modLoc("block/tin_block"));
    withExistingParent("tin_ore", modLoc("block/tin_ore"));
    withExistingParent("deepslate_tin_ore", modLoc("block/deepslate_tin_ore"));
    withExistingParent("raw_tin_block", modLoc("block/raw_tin_block"));
    withExistingParent("bronze_block", modLoc("block/bronze_block"));
    withExistingParent("limestone", modLoc("block/limestone"));
    withExistingParent("polished_limestone", modLoc("block/polished_limestone"));
    withExistingParent("limestone_stairs", modLoc("block/limestone_stairs"));
    withExistingParent("polished_limestone_stairs", modLoc("block/polished_limestone_stairs"));
    withExistingParent("limestone_slab", modLoc("block/limestone_slab"));
    withExistingParent("polished_limestone_slab", modLoc("block/polished_limestone_slab"));
    withExistingParent("calcite_stairs", modLoc("block/calcite_stairs"));
    withExistingParent("calcite_slab", modLoc("block/calcite_slab"));
    withExistingParent("tuff_stairs", modLoc("block/tuff_stairs"));
    withExistingParent("tuff_slab", modLoc("block/tuff_slab"));
    withExistingParent("dripstone_stairs", modLoc("block/dripstone_stairs"));
    withExistingParent("dripstone_slab", modLoc("block/dripstone_slab"));

    ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
    // General

    builder(itemGenerated, "tin_ingot");
    builder(itemGenerated, "raw_tin");
    builder(itemGenerated, "tin_nugget");
    builder(itemGenerated, "bronze_ingot");
    builder(itemGenerated, "bronze_nugget");
    builder(itemGenerated, "bronze_blend");
    builder(itemGenerated, "copper_nugget");
    builder(itemGenerated, "alchemical_essence");
    builder(itemGenerated, "bone_ash");
    builder(itemGenerated, "bronze_bars");
    builder(itemGenerated, "bronze_chain");
    builder(itemGenerated, "bronze_lantern");
    builder(itemGenerated, "mortar");
    builder(itemGenerated, "pestle");
    builder(itemGenerated, "netherite_pestle");
    builder(itemGenerated, "flint_knife");
    builder(itemGenerated, "bronze_knife");
    builder(itemGenerated, "sulphur_dust");
    builder(itemGenerated, "saltpetre");
    builder(itemGenerated, "bat_guano");
    builder(itemGenerated, "cured_flesh");
    builder(itemGenerated, "enchanted_forest_music_disc");
}
    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}
