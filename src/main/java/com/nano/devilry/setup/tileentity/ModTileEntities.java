package com.nano.devilry.setup.tileentity;

import com.nano.devilry.setup.ModBlocks;
import com.nano.devilry.setup.Registration;
import com.nano.devilry.setup.tileentity.AlchemyStationTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class ModTileEntities
{
    public static final RegistryObject<TileEntityType<AlchemyStationTile>> ALCHEMY_STATION_TILE_ENTITY
            = Registration.TILE_ENTITY_TYPES.register("alchemy_station_tile_entity", ()-> TileEntityType.Builder.of(
            ()-> new AlchemyStationTile(), ModBlocks.ALCHEMY_STATION.get()).build(null));


    public static void register() {}
}
