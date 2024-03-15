package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.client.ChiliBulletWeaponsClient;
import com.github.iunius118.chilibulletweapons.data.*;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.registry.ModRegistries;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import org.slf4j.Logger;

@Mod(ChiliBulletWeapons.MOD_ID)
public class ChiliBulletWeapons {
    public static final String MOD_ID = "chilibulletweapons";
    public static final String MOD_NAME = "Chili Bullet Weapons";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ChiliBulletWeapons(IEventBus modEventBus, Dist dist) {
        // Register mod event listeners
        ModRegistries.registerGameObjects(modEventBus);
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(this::onCommonSetup);

        if (FMLLoader.getDist().isClient()) {
            // Init client
            ChiliBulletWeaponsClient.onInitializeClient(modEventBus);
        }

        // Register forge event listeners
        NeoForge.EVENT_BUS.addListener(this::onLootTableLoad);
    }

    public static ResourceLocation makeId(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        registerCompostableItems();
    }

    private void registerCompostableItems() {
        ComposterBlock.COMPOSTABLES.put(ModItems.BULLET_CHILI, 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CURVED_CHILI, 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CHILI_SEEDS, 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CHILI_POTATO_SANDWICH, 0.85F);
        ComposterBlock.COMPOSTABLES.put(ModItems.HALF_CHILI_POTATO_SANDWICH, 0.43F);
        ComposterBlock.COMPOSTABLES.put(ModItems.FRIED_CHILI_PEPPER, 0.3F);
    }

    private void onLootTableLoad(final LootTableLoadEvent event) {
        ResourceLocation name = event.getName();

        // Add chili pepper loot pool to short grass
        if (name != null && name.equals(new ResourceLocation("blocks/short_grass"))) {
            LootPool pool = LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.CURVED_CHILI)
                            .when(LootItemRandomChanceCondition.randomChance(0.125F))
                            .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1)))
                    .build();
            event.getTable().addPool(pool);
        }
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
        dataGenerator.addProvider(includesServer, new ModRecipeProvider(packOutput, lookupProvider));

        // Client
        final boolean includesClient = event.includeClient();
        ModLanguageProvider.addProviders(includesClient, ChiliBulletWeapons.MOD_ID, dataGenerator);
        dataGenerator.addProvider(includesClient, new ModBlockStateProvider(packOutput, ChiliBulletWeapons.MOD_ID, existingFileHelper));
        dataGenerator.addProvider(includesClient, new ModItemModelProvider(packOutput, ChiliBulletWeapons.MOD_ID, existingFileHelper));
        dataGenerator.addProvider(includesClient, new ModSoundDefinitionsProvider(packOutput, ChiliBulletWeapons.MOD_ID, existingFileHelper));
    }
}
