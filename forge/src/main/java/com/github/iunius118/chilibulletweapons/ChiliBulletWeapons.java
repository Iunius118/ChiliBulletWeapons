package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.platform.ForgeChiliBulletWeaponsConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class ChiliBulletWeapons {

    public ChiliBulletWeapons(FMLJavaModLoadingContext context) {
        final IEventBus modEventBus = context.getModEventBus();

        // Register config handler
        context.registerConfig(ModConfig.Type.COMMON, ForgeChiliBulletWeaponsConfig.COMMON_SPEC, Constants.MOD_ID + ".toml");

        // Use Forge to bootstrap the Common mod.
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

    }
}
