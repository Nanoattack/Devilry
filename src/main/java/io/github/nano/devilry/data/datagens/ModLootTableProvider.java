package io.github.nano.devilry.data.datagens;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;

//TY Pale Imitations/Brad (Dev of Schools of Magic Mod) for the code!
//fixme
//todo

public class ModLootTableProvider extends LootTableProvider{

    public ModLootTableProvider(PackOutput packOutput) {
        super(packOutput, BuiltInLootTables.all(), List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)));
    }

    @Override
    public @NotNull List<SubProviderEntry> getTables() {
        return super.getTables();
    }
}
