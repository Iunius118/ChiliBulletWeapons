package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Consumer;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(final BlockModelGenerators modelGen) {
        modelGen.createCropBlock(ModBlocks.CHILI_PEPPER, BlockStateProperties.AGE_7, 0, 1, 2, 3, 4, 5, 6, 7);
        modelGen.createTrivialBlock(ModBlocks.HOT_SAUCE_BARREL, TexturedModel.CUBE_TOP_BOTTOM);
        modelGen.createCrossBlock(ModBlocks.CURVED_CHILI_STRING, BlockModelGenerators.TintState.NOT_TINTED);
    }

    @Override
    public void generateItemModels(final ItemModelGenerators modelGen) {
        Consumer<Item> basicItem = item -> modelGen.generateFlatItem(item, ModelTemplates.FLAT_ITEM);

        // Plants
        // Item model of chili seeds is generated during block state model generation of chili pepper crop
        basicItem.accept(ModItems.BULLET_CHILI);
        basicItem.accept(ModItems.CURVED_CHILI);
        basicItem.accept(ModItems.CURVED_CHILI_STRING);
        basicItem.accept(ModItems.DRIED_CURVED_CHILI);
        basicItem.accept(ModItems.BULLET_CHILI_SACK);
        basicItem.accept(ModItems.CURVED_CHILI_SACK);

        // Foods
        basicItem.accept(ModItems.HOT_SAUCE);
        // Item model of hot sauce barrel is generated during block state model generation
        basicItem.accept(ModItems.CHILI_CHICKEN_SANDWICH);
        basicItem.accept(ModItems.CHILI_FISH_SANDWICH);
        basicItem.accept(ModItems.CHILI_MEAT_SANDWICH);
        basicItem.accept(ModItems.CHILI_POTATO_SANDWICH);
        basicItem.accept(ModItems.HALF_CHILI_CHICKEN_SANDWICH);
        basicItem.accept(ModItems.HALF_CHILI_FISH_SANDWICH);
        basicItem.accept(ModItems.HALF_CHILI_MEAT_SANDWICH);
        basicItem.accept(ModItems.HALF_CHILI_POTATO_SANDWICH);
        basicItem.accept(ModItems.PASTA_OIL_AND_CHILI);
        basicItem.accept(ModItems.FRIED_CHILI_PEPPER);

        // Weapons
        basicItem.accept(ModItems.CHILI_ARROW);
        basicItem.accept(ModItems.CHILI_BULLET);
        basicItem.accept(ModItems.UPGRADE_GUN_BAYONET);
        basicItem.accept(ModItems.UPGRADE_GUN_BARREL);
        basicItem.accept(ModItems.UPGRADE_GUN_MECHANISM);

        /* Other complex models are copied from the Forge version as an interim measure
         * - Variants of chili bullet gun
         * - Variants of chili bullet gun with bayonet
         * - Chili bullet machine gun
         */
    }
}
