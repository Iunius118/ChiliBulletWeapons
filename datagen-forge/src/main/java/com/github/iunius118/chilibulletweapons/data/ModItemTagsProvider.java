package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import com.github.iunius118.chilibulletweapons.tags.ModItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, blockTagsProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModItemTags.FORGE_CROPS).addTag(ModItemTags.FORGE_CROPS_CHILI);
        tag(ModItemTags.FORGE_CROPS_CHILI).add(ModItems.CURVED_CHILI);
        tag(ModItemTags.FORGE_SEEDS).addTag(ModItemTags.FORGE_SEEDS_CHILI);
        tag(ModItemTags.FORGE_SEEDS_CHILI).add(ModItems.CHILI_SEEDS);

        tag(ModItemTags.COMMON_CROPS).addTag(ModItemTags.COMMON_CROPS_CHILI);
        tag(ModItemTags.COMMON_CROPS_CHILI).add(ModItems.CURVED_CHILI);
        tag(ModItemTags.COMMON_SEEDS).addTag(ModItemTags.COMMON_SEEDS_CHILI);
        tag(ModItemTags.COMMON_SEEDS_CHILI).add(ModItems.CHILI_SEEDS);

        tag(ModItemTags.FOODS_RED_CHILI_PEPPER).add(ModItems.CURVED_CHILI, ModItems.DRIED_CURVED_CHILI);
        tag(ModItemTags.FOODS_GREEN_CHILI_PEPPER).add(ModItems.CURVED_GREEN_CHILI, ModItems.PICKLED_GREEN_CHILI);
        tag(ModItemTags.FOODS_CHILI_PEPPERS).addTag(ModItemTags.FOODS_RED_CHILI_PEPPER)
                .addTag(ModItemTags.FOODS_GREEN_CHILI_PEPPER);
        tag(ModItemTags.FOODS_CHILI_SEASONING).addTag(ModItemTags.FOODS_CHILI_PEPPERS)
                .add(ModItems.HOT_SAUCE, ModItems.GREEN_HOT_SAUCE);
        tag(ModItemTags.FOODS_COOKED_FISH).add(Items.COOKED_COD, Items.COOKED_SALMON);
        tag(ModItemTags.FOODS_COOKED_MEAT)
                .add(Items.COOKED_BEEF, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_RABBIT);
        tag(ModItemTags.CHILI_BIOMASS)
                .add(ModItems.CURVED_CHILI_STRING, ModItems.CURVED_CHILI_SACK, ModItems.BULLET_CHILI_SACK);
        tag(ModItemTags.NON_FLAMMABLE_PLANKS).add(Items.CRIMSON_PLANKS, Items.WARPED_PLANKS);

        tag(ItemTags.ARROWS).add(ModItems.CHILI_ARROW);
    }
}
