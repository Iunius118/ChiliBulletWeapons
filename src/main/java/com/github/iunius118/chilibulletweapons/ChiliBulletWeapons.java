package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.registry.ModRegistries;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
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
import org.slf4j.Logger;

public class ChiliBulletWeapons implements ModInitializer {
    public static final String MOD_ID = "chilibulletweapons";
    public static final String MOD_NAME = "Chili Bullet Weapons";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        ModRegistries.registerGameObjects();
        registerCompostableItems();
        LootTableEvents.MODIFY.register(this::onLootTableLoad);
    }

    public static ResourceLocation makeId(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    private void registerCompostableItems() {
        ComposterBlock.COMPOSTABLES.put(ModItems.BULLET_CHILI, 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CURVED_CHILI, 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CHILI_SEEDS, 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModItems.CHILI_POTATO_SANDWICH, 0.85F);
        ComposterBlock.COMPOSTABLES.put(ModItems.HALF_CHILI_POTATO_SANDWICH, 0.43F);
        ComposterBlock.COMPOSTABLES.put(ModItems.FRIED_CHILI_PEPPER, 0.3F);
    }

    void onLootTableLoad(ResourceManager resourceManager, LootDataManager lootManager, ResourceLocation id, LootTable.Builder tableBuilder, LootTableSource source) {
        if (source.isBuiltin()) {
            if (id.equals(new ResourceLocation("blocks/grass")) || id.equals(new ResourceLocation("blocks/short_grass"))) {
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
