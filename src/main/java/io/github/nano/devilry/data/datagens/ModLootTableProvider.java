package io.github.nano.devilry.data.datagens;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ForgeLootTableProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

//TY Pale Imitations/Brad (Dev of Schools of Magic Mod) for the code!
//todo

public class ModLootTableProvider extends LootTableProvider{

    public ModLootTableProvider(PackOutput packOutput) {
        super(packOutput, BuiltInLootTables.all(), List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)));
    }

    @Override
    public @NotNull List<SubProviderEntry> getTables() {
        return super.getTables();
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {

    }
}
