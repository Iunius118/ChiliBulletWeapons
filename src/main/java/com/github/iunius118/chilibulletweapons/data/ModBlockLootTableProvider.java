package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    protected ModBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        add(ModBlocks.CHILI_PEPPER, createChiliPepperCropDrops());
        add(ModBlocks.HOT_SAUCE_BARREL, this.createSingleItemTable(ModBlocks.HOT_SAUCE_BARREL));
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
}
