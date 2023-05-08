package io.github.nano.devilry.data.client;

import io.github.nano.devilry.ModMain;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
//todo

@SuppressWarnings({"UnusedReturnValue", "unused", "SpellCheckingInspection"})
public class ModItemModelProvider extends ItemModelProvider
{

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ModMain.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void registerModels() {

    withExistingParent("tin_block", modLoc("block/tin_block"));
    withExistingParent("tin_ore", modLoc("block/tin_ore"));
    withExistingParent("deepslate_tin_ore", modLoc("block/deepslate_tin_ore"));
    withExistingParent("raw_tin_block", modLoc("block/raw_tin_block"));
    withExistingParent("bronze_block", modLoc("block/bronze_block"));

    ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
    // General
        //todo look into duplicates
    builder(itemGenerated, "tin_ingot");
    builder(itemGenerated, "raw_tin");
    builder(itemGenerated, "tin_nugget");
    builder(itemGenerated, "bronze_ingot");
    builder(itemGenerated, "bronze_blend");
    builder(itemGenerated, "copper_nugget");
    builder(itemGenerated, "alchemical_essence");
    builder(itemGenerated, "bone_ash");
    builder(itemGenerated, "bronze_bars");
    builder(itemGenerated, "bronze_chain");
    builder(itemGenerated, "bronze_lantern");
    builder(itemGenerated, "mortar");
    builder(itemGenerated, "pestle");
}
    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}
