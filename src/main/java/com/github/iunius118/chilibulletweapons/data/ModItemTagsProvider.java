package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.tags.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(ModItemTags.COMMON_CROPS).addTag(ModItemTags.COMMON_CROPS_CHILI);
        getOrCreateTagBuilder(ModItemTags.COMMON_CROPS_CHILI).add(ModItems.CURVED_CHILI);
        getOrCreateTagBuilder(ModItemTags.COMMON_SEEDS).addTag(ModItemTags.COMMON_SEEDS_CHILI);
        getOrCreateTagBuilder(ModItemTags.COMMON_SEEDS_CHILI).add(ModItems.CHILI_SEEDS);

        getOrCreateTagBuilder(ModItemTags.FOODS_COOKED_FISH).add(Items.COOKED_COD, Items.COOKED_SALMON);
        getOrCreateTagBuilder(ModItemTags.FOODS_COOKED_MEAT).add(Items.COOKED_BEEF, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_RABBIT);
    }
}
