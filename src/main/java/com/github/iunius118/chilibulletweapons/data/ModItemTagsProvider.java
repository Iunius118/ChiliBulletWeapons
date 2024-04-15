package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, blockTagProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModItemTags.FORGE_CROPS).addTag(ModItemTags.FORGE_CROPS_CHILI);
        tag(ModItemTags.FORGE_CROPS_CHILI).add(ModItems.CURVED_CHILI);
        tag(ModItemTags.FORGE_SEEDS).addTag(ModItemTags.FORGE_SEEDS_CHILI);
        tag(ModItemTags.FORGE_SEEDS_CHILI).add(ModItems.CHILI_SEEDS);

        tag(ModItemTags.FOODS_COOKED_FISH).add(Items.COOKED_COD, Items.COOKED_SALMON);
        tag(ModItemTags.FOODS_COOKED_MEAT).add(Items.COOKED_BEEF, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_RABBIT);

        tag(ItemTags.ARROWS).add(ModItems.CHILI_ARROW);
    }
}
