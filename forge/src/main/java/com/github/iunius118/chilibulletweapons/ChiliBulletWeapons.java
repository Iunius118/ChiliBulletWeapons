package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.platform.ForgeChiliBulletWeaponsConfig;
import com.github.iunius118.chilibulletweapons.registry.ForgeModRegistries;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class ChiliBulletWeapons {

    public ChiliBulletWeapons(FMLJavaModLoadingContext context) {
        final IEventBus modEventBus = context.getModEventBus();
        // Use Forge to bootstrap the Common mod.
        //Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        // Register config handler
        context.registerConfig(ModConfig.Type.COMMON, ForgeChiliBulletWeaponsConfig.COMMON_SPEC, Constants.MOD_ID + ".toml");

        // Register mod event listeners
        ForgeModRegistries.registerGameObjects(modEventBus);
        modEventBus.addListener(this::onCommonSetup);

        if (FMLEnvironment.dist.isClient()) {
            // Init client
            ChiliBulletWeaponsClient.onInitializeClient(modEventBus);
        }
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        // Register compostable items
        ComposterBlock.COMPOSTABLES.putAll(ModItems.COMPOSTABLES);
    }
}
