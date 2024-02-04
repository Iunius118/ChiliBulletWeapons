package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaLootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.Set;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(PackOutput packOutput) {
        super(packOutput, Set.of(), VanillaLootTableProvider.create(packOutput).getTables());
    }

    @Override
    public List<SubProviderEntry> getTables() {
        return ImmutableList.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)
        );
    }

    private static class ModBlockLootTables extends BlockLootSubProvider {
        protected ModBlockLootTables() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            add(ModBlocks.CHILI_PEPPER, createChiliPepperCropDrops());
        }

        private LootTable.Builder createChiliPepperCropDrops() {
            Block block = ModBlocks.CHILI_PEPPER;
            Item bulletChili = ModItems.BULLET_CHILI;
            Item curvedChili = ModItems.CURVED_CHILI;
            Item chiliSeeds = ModItems.CHILI_SEEDS;
            LootItemCondition.Builder conditionBuilder = LootItemBlockStatePropertyCondition
                    .hasBlockStateProperties(ModBlocks.CHILI_PEPPER)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
            LootTable.Builder lootTableBuilder = LootTable.lootTable()
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(bulletChili).when(conditionBuilder)))
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(curvedChili).when(conditionBuilder).otherwise(LootItem.lootTableItem(chiliSeeds))))
                    .withPool(LootPool.lootPool().when(conditionBuilder).add(LootItem.lootTableItem(bulletChili).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2))))
                    .withPool(LootPool.lootPool().when(conditionBuilder).add(LootItem.lootTableItem(curvedChili).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2))));
            return this.applyExplosionDecay(block, lootTableBuilder);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return List.of(ModBlocks.CHILI_PEPPER);
        }
    }
}
