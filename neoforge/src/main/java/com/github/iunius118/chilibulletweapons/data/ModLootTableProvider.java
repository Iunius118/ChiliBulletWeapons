package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaLootTableProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
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
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Set.of(), VanillaLootTableProvider.create(packOutput, lookupProvider).getTables(), lookupProvider);
    }

    @Override
    public List<SubProviderEntry> getTables() {
        return ImmutableList.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)
        );
    }

    private static class ModBlockLootTables extends BlockLootSubProvider {
        private final Block chiliPepper;
        private final Item chiliSeeds;
        private final Item curvedGreenChili;
        private final Item curvedChili;

        public ModBlockLootTables(HolderLookup.Provider provider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);

            chiliPepper = getBlock(Constants.Blocks.CHILI_PEPPER);
            chiliSeeds = getItem(Constants.Items.CHILI_SEEDS);
            curvedGreenChili = getItem(Constants.Items.CURVED_GREEN_CHILI);
            curvedChili = getItem(Constants.Items.CURVED_CHILI);
        }

        @Override
        protected void generate() {
            add(chiliPepper, createChiliPepperCropDrops());
        }

        private LootTable.Builder createChiliPepperCropDrops() {
            // Hack to use RangedMatcher to specify block state of ChiliPepperCrop.AGE
            var chiliSeedCondition = new LootItemBlockStatePropertyCondition.Builder(chiliPepper) {
                @Override
                public LootItemCondition build() {
                    var key = ResourceKey.create(Registries.BLOCK, BuiltInRegistries.BLOCK.getKey(chiliPepper));
                    var blockHolder = BuiltInRegistries.BLOCK.getHolderOrThrow(key);
                    return new LootItemBlockStatePropertyCondition(blockHolder, getChiliPepperOutOfHarvestAgePredicate());
                }
            };
            var greenChiliCondition = new LootItemBlockStatePropertyCondition.Builder(chiliPepper)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, CropBlock.MAX_AGE - 1));
            var curvedChiliCondition = new LootItemBlockStatePropertyCondition.Builder(chiliPepper)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, CropBlock.MAX_AGE));

            Holder.Reference<Enchantment> fortune = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);

            // Add drops for each age of chili pepper crop
            var lootTableBuilder = LootTable.lootTable()
                    .withPool(LootPool.lootPool().when(chiliSeedCondition).add(LootItem.lootTableItem(chiliSeeds)))
                    .withPool(LootPool.lootPool().when(greenChiliCondition).add(LootItem.lootTableItem(curvedGreenChili)))
                    .withPool(LootPool.lootPool().when(curvedChiliCondition).add(LootItem.lootTableItem(curvedChili)))
                    .withPool(LootPool.lootPool().when(curvedChiliCondition).add(LootItem.lootTableItem(ModItems.BULLET_CHILI)))
                    // Add bonus for fortune enchantment
                    .withPool(LootPool.lootPool().when(greenChiliCondition).add(LootItem.lootTableItem(curvedGreenChili)
                            .apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune, 0.5714286F, 2))))
                    .withPool(LootPool.lootPool().when(curvedChiliCondition).add(LootItem.lootTableItem(curvedChili)
                            .apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune, 0.5714286F, 2))))
                    .withPool(LootPool.lootPool().when(curvedChiliCondition).add(LootItem.lootTableItem(ModItems.BULLET_CHILI)
                            .apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune, 0.5714286F, 2))));
            return this.applyExplosionDecay(chiliPepper, lootTableBuilder);
        }

        private Optional<StatePropertiesPredicate> getChiliPepperOutOfHarvestAgePredicate() {
            RegistryOps<JsonElement> serializationContext = registries.createSerializationContext(JsonOps.INSTANCE);

            var rangeJson = new JsonObject();
            rangeJson.addProperty("min", "0");
            rangeJson.addProperty("max", String.valueOf(CropBlock.MAX_AGE - 2));

            var ageJson = new JsonObject();
            ageJson.add(CropBlock.AGE.getName(), rangeJson);

            return StatePropertiesPredicate.CODEC.parse(serializationContext, ageJson).result();
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return List.of(chiliPepper);
        }

        private Block getBlock(ResourceLocation id) {
            return BuiltInRegistries.BLOCK.get(id);
        }

        private Item getItem(ResourceLocation id) {
            return BuiltInRegistries.ITEM.get(id);
        }
    }
}
