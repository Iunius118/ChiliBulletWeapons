package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.client.ChiliBulletWeaponsClient;
import com.github.iunius118.chilibulletweapons.data.*;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.registry.ModRegistries;
import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
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
        modEventBus.addListener(this::registerCompostableItems);

        // Init client
        if (FMLLoader.getDist().isClient()) {
            ChiliBulletWeaponsClient.onInitializeClient(modEventBus);
        }
    }

    private void registerCompostableItems(final FMLCommonSetupEvent event) {
        ComposterBlock.COMPOSTABLES.put(ModItems.BULLET_CHILI, 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CURVED_CHILI, 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CHILI_SEEDS, 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CHILI_POTATO_SANDWICH, 0.85F);
    }

    private void gatherData(final GatherDataEvent event) {
        var dataGenerator = event.getGenerator();
        var packOutput = dataGenerator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();
        var blockTagsProvider = new ModBlockTagsProvider(packOutput, lookupProvider, ChiliBulletWeapons.MOD_ID, existingFileHelper);

        // Server
        final boolean includesServer = event.includeServer();
        dataGenerator.addProvider(includesServer, blockTagsProvider);
        dataGenerator.addProvider(includesServer, new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), ChiliBulletWeapons.MOD_ID, existingFileHelper));
        dataGenerator.addProvider(includesServer, new ModLootTableProvider(packOutput));
        dataGenerator.addProvider(includesServer, new ModRecipeProvider(packOutput));

        // Client
        final boolean includesClient = event.includeClient();
        ModLanguageProvider.addProviders(includesClient, ChiliBulletWeapons.MOD_ID, dataGenerator);
        dataGenerator.addProvider(includesClient, new ModBlockStateProvider(packOutput, ChiliBulletWeapons.MOD_ID, existingFileHelper));
        dataGenerator.addProvider(includesClient, new ModItemModelProvider(packOutput, ChiliBulletWeapons.MOD_ID, existingFileHelper));
        dataGenerator.addProvider(includesClient, new ModSoundDefinitionsProvider(packOutput, ChiliBulletWeapons.MOD_ID, existingFileHelper));
    }
}
