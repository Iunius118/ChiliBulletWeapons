package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(BlockTags.CROPS).add(ModBlocks.CHILI_PEPPER);
        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(ModBlocks.CHILI_PEPPER);
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.CHILI_PEPPER);
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.HOT_SAUCE_BARREL);
    }
}
