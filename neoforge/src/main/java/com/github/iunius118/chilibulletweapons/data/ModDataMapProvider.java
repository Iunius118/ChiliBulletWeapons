package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {

    public ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        // Register compostable items to NeoForge's data maps
        ModItems.COMPOSTABLES.forEach((item, chance) ->
                BuiltInRegistries.ITEM.getResourceKey(item).ifPresent(key ->
                        builder(NeoForgeDataMaps.COMPOSTABLES).add(key, new Compostable(chance), false))
        );
    }
}
