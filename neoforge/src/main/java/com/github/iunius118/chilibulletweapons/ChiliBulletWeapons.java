package com.github.iunius118.chilibulletweapons;


import com.github.iunius118.chilibulletweapons.platform.NeoForgeChiliBulletWeaponsConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class ChiliBulletWeapons {

    public ChiliBulletWeapons(IEventBus modEventBus, ModContainer modContainer) {
        // Register config handler
        modContainer.registerConfig(ModConfig.Type.COMMON, NeoForgeChiliBulletWeaponsConfig.COMMON_SPEC, Constants.MOD_ID + ".toml");

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();
    }
}
