package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.advancements.ModCriteriaTriggers;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.platform.FabricChiliBulletWeaponsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

public class ChiliBulletWeapons implements ModInitializer {
    @Override
    public void onInitialize() {
        // Register config handlers
        registerConfig();

        // Use Fabric to bootstrap the Common mod.
        //Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        registerCriterionTriggers();
        registerCompostableItems();
        registerFuelItems();
        LootTableEvents.MODIFY.register(this::onLootTableLoad);
    }

    private void registerConfig() {
        // Register TOML type config
        AutoConfig.register(FabricChiliBulletWeaponsConfig.class, Toml4jConfigSerializer::new);
    }

    private void registerCriterionTriggers() {
        ModCriteriaTriggers.CRITERION_TRIGGERS.forEach(CriteriaTriggers::register);
    }

    private void registerCompostableItems() {
        ComposterBlock.COMPOSTABLES.putAll(ModItems.COMPOSTABLES);
    }

    private void registerFuelItems() {
        ModItems.FURNACE_FUELS.forEach(FuelRegistry.INSTANCE::add);
    }

    // 1.20.2 or earlier
    private static final ResourceLocation OLD_GRASS_LOOT_PATH = new ResourceLocation("blocks/grass");
    // 1.20.3 or later
    private static final ResourceLocation NEW_GRASS_LOOT_PATH = new ResourceLocation("blocks/short_grass");

    void onLootTableLoad(ResourceManager resourceManager, LootDataManager lootManager, ResourceLocation id,
                         LootTable.Builder tableBuilder, LootTableSource source) {
        if (source.isBuiltin()) {
            if (OLD_GRASS_LOOT_PATH.equals(id) || NEW_GRASS_LOOT_PATH.equals(id)) {
                // Is grass (-1.20.2) || short_grass (1.20.3-)
                // Add chili pepper loot pool to short grass
                var lootPool = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.CURVED_CHILI)
                                .when(LootItemRandomChanceCondition.randomChance(0.125F))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 1)))
                        .build();
                tableBuilder.pool(lootPool);
            }
        }
    }
}
