package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.data.ModItemModelProvider;
import com.github.iunius118.chilibulletweapons.data.ModLanguageProvider;
import com.github.iunius118.chilibulletweapons.registry.ModRegistries;
import com.mojang.logging.LogUtils;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ChiliBulletWeapons.MOD_ID)
public class ChiliBulletWeapons {
    public static final String MOD_ID = "chilibulletweapons";
    public static final String MOD_NAME = "Chili Bullet Weapons";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ChiliBulletWeapons() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModRegistries.registerGameObjects(modEventBus);
        modEventBus.addListener(this::gatherData);
    }

    private void gatherData(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = dataGenerator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();

        // Server
        final boolean includesServer = event.includeServer();

        // Client
        final boolean includesClient = event.includeClient();
        dataGenerator.addProvider(includesClient, new ModItemModelProvider(packOutput, ChiliBulletWeapons.MOD_ID, existingFileHelper));
        ModLanguageProvider.addProviders(includesClient, ChiliBulletWeapons.MOD_ID, dataGenerator);
    }
}
