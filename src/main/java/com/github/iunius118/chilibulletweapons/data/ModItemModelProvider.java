package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletGun;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, String modId, ExistingFileHelper existingFileHelper) {
        super(output, modId, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Plants
        basicItem(ModItems.BULLET_CHILI);
        basicItem(ModItems.CURVED_CHILI);
        basicItem(ModItems.CHILI_SEEDS);
        // Foods
        basicItem(ModItems.CHILI_CHICKEN_SANDWICH);
        basicItem(ModItems.CHILI_FISH_SANDWICH);
        basicItem(ModItems.CHILI_MEAT_SANDWICH);
        basicItem(ModItems.CHILI_POTATO_SANDWICH);
        // Weapons
        basicItem(ModItems.CHILI_BULLET);
        registerGunModels();
    }

    private void registerSimpleItemModel(Item item, ModelFile modelFile, String suffix) {
        ResourceLocation item_id = getItemId(item);

        if (item_id != null) {
            final String item_name = item_id.getPath();
            getBuilder(item_name).parent(modelFile).texture("layer0", "item/" + item_name + suffix);
        }
    }

    private void registerGunModels() {
        final ModelFile parent = new ModelFile.UncheckedModelFile("item/generated");
        final String pistol = "pistol";
        final String pistol_loading = "pistol_loading";
        final String rifle = "rifle";
        final String rifle_loading = "rifle_loading";
        final String shotgun = "shotgun";
        final String shotgun_loading = "shotgun_loading";

        getBuilder("gun").parent(parent)
                .override()
                    .model(new ModelFile.UncheckedModelFile(getModelLocation(pistol))).end()
                .override().predicate(ChiliBulletGun.PROPERTY_LOADING, 1F)
                    .model(new ModelFile.UncheckedModelFile(getModelLocation(pistol_loading))).end()
                .override().predicate(ChiliBulletGun.PROPERTY_PIERCING, 1F)
                    .model(new ModelFile.UncheckedModelFile(getModelLocation(rifle))).end()
                .override().predicate(ChiliBulletGun.PROPERTY_PIERCING, 1F).predicate(ChiliBulletGun.PROPERTY_LOADING, 1F)
                    .model(new ModelFile.UncheckedModelFile(getModelLocation(rifle_loading))).end()
                .override().predicate(ChiliBulletGun.PROPERTY_MULTISHOT, 1F)
                    .model(new ModelFile.UncheckedModelFile(getModelLocation(shotgun))).end()
                .override().predicate(ChiliBulletGun.PROPERTY_MULTISHOT, 1F).predicate(ChiliBulletGun.PROPERTY_LOADING, 1F)
                    .model(new ModelFile.UncheckedModelFile(getModelLocation(shotgun_loading))).end();

        getBuilder(pistol).parent(parent).texture("layer0", "item/" + pistol)
                .transforms()
                    .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(0.68F).rotation(0F, -90F, 25F).translation(3.0F, 3.2F, 1.13F).end()
                    .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).scale(0.68F).rotation(0F, 90F, -25F).translation(3.0F, 3.2F, 1.13F).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).scale(0.85F).rotation(-40F, -90F, 0F).translation(0F, 1F, -0.25F).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).scale(0.85F).rotation(-40F, 90F, 0F).translation(0F, 1F, -0.25F).end()
                    .end();
        getBuilder(pistol_loading).parent(parent).texture("layer0", "item/" + pistol_loading)
                .transforms()
                    .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(0.68F).rotation(0F, -90F, 25F).translation(1.13F, 3.2F, 1.13F).end()
                    .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).scale(0.68F).rotation(0F, 90F, -25F).translation(1.13F, 3.2F, 1.13F).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).scale(0.85F).rotation(-40F, -90F, 0F).translation(0F, 1F, -0.25F).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).scale(0.85F).rotation(-40F, 90F, 0F).translation(0F, 1F, -0.25F).end()
                    .end();
        getBuilder(rifle).parent(parent).texture("layer0", "item/" + rifle)
                .transforms()
                    .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(0.68F).rotation(0F, -90F, 25F).translation(3.0F, 3.2F, -0.4F).end()
                    .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).scale(0.68F).rotation(0F, 90F, -25F).translation(3.0F, 3.2F, -0.4F).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).scale(0.85F).rotation(-40F, -90F, 0F).translation(0F, 0.5F, -2.2F).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).scale(0.85F).rotation(-40F, 90F, 0F).translation(0F, 0.5F, -2.2F).end()
                    .end();
        getBuilder(rifle_loading).parent(parent).texture("layer0", "item/" + rifle_loading)
                .transforms()
                    .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(0.68F).rotation(0F, -90F, 25F).translation(1.13F, 3.2F, -0.4F).end()
                    .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).scale(0.68F).rotation(0F, 90F, -25F).translation(1.13F, 3.2F, -0.4F).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).scale(0.85F).rotation(-40F, -90F, 0F).translation(0F, 0.5F, -2.2F).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).scale(0.85F).rotation(-40F, 90F, 0F).translation(0F, 0.5F, -2.2F).end()
                    .end();
        getBuilder(shotgun).parent(new ModelFile.UncheckedModelFile(getModelLocation(rifle))).texture("layer0", "item/" + shotgun);
        getBuilder(shotgun_loading).parent(new ModelFile.UncheckedModelFile(getModelLocation(rifle_loading))).texture("layer0", "item/" + shotgun_loading);
    }

    private ResourceLocation getModelLocation(String name) {
        return new ResourceLocation(modid, "item/" + name);
    }

    private ResourceLocation getItemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }
}
