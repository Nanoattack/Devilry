package io.github.nano.devilry.devilry.data.datagens.client;

import io.github.nano.devilry.devilry.ModMain;
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
    withExistingParent("festering_limestone", modLoc("block/festering_limestone"));
    withExistingParent("saltpetre_cluster", modLoc("block/saltpetre_cluster"));
    withExistingParent("large_saltpetre_bud", modLoc("block/large_saltpetre_bud"));
    withExistingParent("medium_saltpetre_bud", modLoc("block/medium_saltpetre_bud"));
    withExistingParent("small_saltpetre_bud", modLoc("block/small_saltpetre_bud"));
    withExistingParent("polished_limestone", modLoc("block/polished_limestone"));
    withExistingParent("limestone_stairs", modLoc("block/limestone_stairs"));
    withExistingParent("limestone_wall", modLoc("block/limestone_wall_inventory"));
    withExistingParent("polished_limestone_stairs", modLoc("block/polished_limestone_stairs"));
    withExistingParent("limestone_slab", modLoc("block/limestone_slab"));
    withExistingParent("polished_limestone_slab", modLoc("block/polished_limestone_slab"));
    withExistingParent("calcite_stairs", modLoc("block/calcite_stairs"));
    withExistingParent("calcite_slab", modLoc("block/calcite_slab"));
    withExistingParent("calcite_wall", modLoc("block/calcite_wall_inventory"));
    withExistingParent("tuff_stairs", modLoc("block/tuff_stairs"));
    withExistingParent("tuff_slab", modLoc("block/tuff_slab"));
    withExistingParent("tuff_wall", modLoc("block/tuff_wall_inventory"));
    withExistingParent("dripstone_stairs", modLoc("block/dripstone_stairs"));
    withExistingParent("dripstone_slab", modLoc("block/dripstone_slab"));
    withExistingParent("dripstone_wall", modLoc("block/dripstone_wall_inventory"));
    withExistingParent("stolas_effigy", modLoc("block/stolas_effigy"));
    withExistingParent("wittling_table", modLoc("block/wittling_table"));

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
    builder(itemGenerated, "owl_feather");
    builder(itemGenerated, "stolas_effigy");
    builder(itemGenerated, "demon_altar");
}
    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}
