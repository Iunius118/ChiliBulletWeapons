package com.github.iunius118.chilibulletweapons;


import com.github.iunius118.chilibulletweapons.client.ChiliBulletWeaponsClient;
import com.github.iunius118.chilibulletweapons.data.*;
import com.github.iunius118.chilibulletweapons.platform.NeoForgeChiliBulletWeaponsConfig;
import com.github.iunius118.chilibulletweapons.registry.NeoForgeModRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(Constants.MOD_ID)
public class ChiliBulletWeapons {

    public ChiliBulletWeapons(IEventBus modEventBus, ModContainer modContainer) {
        // Use NeoForge to bootstrap the Common mod.
        //Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        // Register config handler
        modContainer.registerConfig(ModConfig.Type.COMMON, NeoForgeChiliBulletWeaponsConfig.COMMON_SPEC, Constants.MOD_ID + ".toml");

        // Register mod event listeners
        NeoForgeModRegistries.registerGameObjects(modEventBus);
        modEventBus.addListener(this::gatherData);

        if (FMLEnvironment.dist.isClient()) {
            // Init client
            ChiliBulletWeaponsClient.onInitializeClient(modEventBus);
        }
    }

    private void gatherData(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = dataGenerator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();
        var blockTagsProvider = new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        // Server
        final boolean includesServer = event.includeServer();
        dataGenerator.addProvider(includesServer, blockTagsProvider);
        dataGenerator.addProvider(includesServer, new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        dataGenerator.addProvider(includesServer, new ModLootTableProvider(packOutput, lookupProvider));
        dataGenerator.addProvider(includesServer, new ModRecipeProvider(packOutput, lookupProvider));

        // Client
        final boolean includesClient = event.includeClient();
        dataGenerator.addProvider(includesClient, new ModLanguageProvider(packOutput));
        dataGenerator.addProvider(includesClient, new ModItemModelProvider(packOutput, existingFileHelper));
    }
}
