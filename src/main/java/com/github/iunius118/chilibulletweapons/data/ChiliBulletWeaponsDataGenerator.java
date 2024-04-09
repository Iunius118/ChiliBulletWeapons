package com.github.iunius118.chilibulletweapons.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ChiliBulletWeaponsDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModBlockTagsProvider::new);
        pack.addProvider(ModItemTagsProvider::new);
        pack.addProvider(ModBlockLootTableProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModModelProvider::new);
        ModLanguageProvider.addProviders(pack);
    }
}
