package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.ARROWS).add(ModItems.CHILI_ARROW);
        tag(ItemTags.DURABILITY_ENCHANTABLE).add(ModItems.GUN, ModItems.MACHINE_GUN);

        tag(ModItemTags.CHILI_BIOMASS).add(ModItems.BULLET_CHILI_SACK);
        tag(ModItemTags.NON_FLAMMABLE_PLANKS).add(Items.CRIMSON_PLANKS, Items.WARPED_PLANKS);
    }
}
