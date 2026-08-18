package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.block.ChiliPepperCrop;
import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.mixin.LootItemBlockStatePropertyConditionBuilderAccessor;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaLootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

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
            add(ModBlocks.CURVED_CHILI_STRING, this.createSingleItemTableWithSilkTouch(ModBlocks.CURVED_CHILI_STRING, ModItems.DRIED_CURVED_CHILI, ConstantValue.exactly(9.0F)));
            add(ModBlocks.HOT_SAUCE_BARREL, this.createSingleItemTable(ModBlocks.HOT_SAUCE_BARREL));
        }

        private LootTable.Builder createChiliPepperCropDrops() {
            // Hack to use RangedMatcher to specify block state of ChiliPepperCrop.AGE
            var chiliSeedCondition = new LootItemBlockStatePropertyCondition.Builder(ModBlocks.CHILI_PEPPER);
            // Use mixin to set properties of LootItemBlockStatePropertyCondition.Builder for chili seeds condition
            var builderAccessor = (LootItemBlockStatePropertyConditionBuilderAccessor) chiliSeedCondition;
            builderAccessor.setProperties(getChiliPepperOutOfHarvestAgePredicate());

            var greenChiliCondition = new LootItemBlockStatePropertyCondition.Builder(ModBlocks.CHILI_PEPPER)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ChiliPepperCrop.AGE,
                            ChiliPepperCrop.GREEN_CHILI_AGE));
            var matureChiliCondition = new LootItemBlockStatePropertyCondition.Builder(ModBlocks.CHILI_PEPPER)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ChiliPepperCrop.AGE,
                            ChiliPepperCrop.MAX_AGE));

            // Add drops for each age of chili pepper crop
            var lootTableBuilder = LootTable.lootTable()
                    .withPool(LootPool.lootPool().when(chiliSeedCondition)
                            .add(LootItem.lootTableItem(ModItems.CHILI_SEEDS)))
                    .withPool(LootPool.lootPool().when(greenChiliCondition)
                            .add(LootItem.lootTableItem(ModItems.CURVED_GREEN_CHILI)))
                    .withPool(LootPool.lootPool().when(matureChiliCondition)
                            .add(LootItem.lootTableItem(ModItems.CURVED_CHILI)))
                    .withPool(LootPool.lootPool().when(matureChiliCondition)
                            .add(LootItem.lootTableItem(ModItems.BULLET_CHILI)))
                    // Add bonus for fortune enchantment
                    .withPool(LootPool.lootPool().when(greenChiliCondition)
                            .add(LootItem.lootTableItem(ModItems.CURVED_GREEN_CHILI)
                                    .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2))))
                    .withPool(LootPool.lootPool().when(matureChiliCondition)
                            .add(LootItem.lootTableItem(ModItems.CURVED_CHILI)
                                    .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2))))
                    .withPool(LootPool.lootPool().when(matureChiliCondition)
                            .add(LootItem.lootTableItem(ModItems.BULLET_CHILI)
                                    .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2))));
            return this.applyExplosionDecay(ModBlocks.CHILI_PEPPER, lootTableBuilder);
        }

        private StatePropertiesPredicate getChiliPepperOutOfHarvestAgePredicate() {
            var rangeJson = new JsonObject();
            rangeJson.addProperty("min", "0");
            rangeJson.addProperty("max", String.valueOf(ChiliPepperCrop.GREEN_CHILI_AGE - 1));

            var ageJson = new JsonObject();
            ageJson.add(ChiliPepperCrop.AGE.getName(), rangeJson);

            return StatePropertiesPredicate.fromJson(ageJson);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return List.of(ModBlocks.CHILI_PEPPER, ModBlocks.CURVED_CHILI_STRING, ModBlocks.HOT_SAUCE_BARREL);
        }
    }
}
