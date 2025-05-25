package com.github.iunius118.chilibulletweapons;


import com.github.iunius118.chilibulletweapons.client.ChiliBulletWeaponsClient;
import com.github.iunius118.chilibulletweapons.platform.NeoForgeChiliBulletWeaponsConfig;
import com.github.iunius118.chilibulletweapons.registry.NeoForgeModRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;

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
}
