package com.github.iunius118.chilibulletweapons;

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
        modContainer.registerConfig(ModConfig.Type.COMMON, NeoForgeChiliBulletWeaponsConfig.COMMON_SPEC,
                Constants.MOD_ID + ".toml");

        // Register mod event listeners
        NeoForgeModRegistries.registerGameObjects(modEventBus);
        modEventBus.addListener(this::gatherData);

        if (FMLEnvironment.getDist().isClient()) {
            // Init client
            ChiliBulletWeaponsClient.onInitializeClient(modEventBus);
        }
    }

    private void gatherData(final GatherDataEvent.Client event) {
        // Data
        event.createProvider(ModItemTagsProvider::new);
        event.createProvider(ModEntityTypeTagsProvider::new);
        event.createProvider(ModLootTableProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(ModAdvancementProvider::new);
        event.createProvider(ModDataMapProvider::new);

        // Assets
        event.createProvider(ModLanguageProvider::new);
        event.createProvider(ModModelProvider::new);
    }

    /**
     * A helper class for accessing this mod's constants from outside its package.
     */
    public static final class Constants extends com.github.iunius118.chilibulletweapons.Constants {
    }
}
