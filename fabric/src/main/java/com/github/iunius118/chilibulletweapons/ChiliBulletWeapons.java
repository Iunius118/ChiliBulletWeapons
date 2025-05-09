package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.platform.FabricChiliBulletWeaponsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class ChiliBulletWeapons implements ModInitializer {

    @Override
    public void onInitialize() {
        // Register TOML type config
        AutoConfig.register(FabricChiliBulletWeaponsConfig.class, Toml4jConfigSerializer::new);

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
    }
}
