package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Plants
        basicItem(ModItems.BULLET_CHILI);
        basicItem(ModItems.BULLET_CHILI_SACK);

        // Weapons
        basicItem(ModItems.CHILI_ARROW);
        basicItem(ModItems.CHILI_BULLET);
        basicItem(ModItems.UPGRADE_GUN_BAYONET);
        basicItem(ModItems.UPGRADE_GUN_BARREL);
        basicItem(ModItems.UPGRADE_GUN_MECHANISM);
        registerGunModels();
    }

    private void registerGunModels() {
        final ModelFile parent = new ModelFile.UncheckedModelFile("item/generated");

        // Transform templates
        getBuilder("gun_short_closed").parent(parent).transforms()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(0.68F).rotation(0F, -90F, 25F).translation(3.0F, 3.2F, 1.13F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).scale(0.68F).rotation(0F, 90F, -25F).translation(3.0F, 3.2F, 1.13F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).scale(0.85F).rotation(-40F, -90F, 0F).translation(0F, 1F, -0.25F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).scale(0.85F).rotation(-40F, 90F, 0F).translation(0F, 1F, -0.25F).end()
                .end();
        getBuilder("gun_short_open").parent(parent).transforms()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(0.68F).rotation(0F, -90F, 25F).translation(1.13F, 3.2F, 1.13F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).scale(0.68F).rotation(0F, 90F, -25F).translation(1.13F, 3.2F, 1.13F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).scale(0.85F).rotation(-40F, -90F, 0F).translation(0F, 1F, -0.25F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).scale(0.85F).rotation(-40F, 90F, 0F).translation(0F, 1F, -0.25F).end()
                .end();
        getBuilder("gun_long_closed").parent(parent).transforms()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(0.68F).rotation(0F, -90F, 25F).translation(3.0F, 3.2F, -0.4F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).scale(0.68F).rotation(0F, 90F, -25F).translation(3.0F, 3.2F, -0.4F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).scale(0.85F).rotation(-40F, -90F, 0F).translation(0F, 0.5F, -2.2F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).scale(0.85F).rotation(-40F, 90F, 0F).translation(0F, 0.5F, -2.2F).end()
                .end();
        getBuilder("gun_long_open").parent(parent).transforms()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(0.68F).rotation(0F, -90F, 25F).translation(1.13F, 3.2F, -0.4F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).scale(0.68F).rotation(0F, 90F, -25F).translation(1.13F, 3.2F, -0.4F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).scale(0.85F).rotation(-40F, -90F, 0F).translation(0F, 0.5F, -2.2F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).scale(0.85F).rotation(-40F, 90F, 0F).translation(0F, 0.5F, -2.2F).end()
                .end();

        // Add gun models
        addGunModels(getBuilder("gun").parent(parent));

        // Add machine gun model
        getBuilder("machine_gun").parent(getModelFile("gun_long_closed")).texture("layer0", "item/" + "machine_gun");
    }

    private void addGunModels(ItemModelBuilder builder) {
        List<String> guns = List.of("pistol", "rifle", "volley_gun");

        for(int i = 0; i < 3 * 2 * 2; i++) {
            ItemModelBuilder.OverrideBuilder override = builder.override();
            String suffix = "";
            String action = "_closed";

            // isLoading
            if ((i & 1) != 0) {
                override.predicate(Constants.ItemProperties.PROPERTY_BAYONETED, 1.0F);
                suffix = "_bayoneted";
            }

            // isLoading
            if ((i & 2) != 0) {
                override.predicate(Constants.ItemProperties.PROPERTY_LOADING, 1.0F);
                suffix += "_loading";
                action = "_open";
            }

            int gunIndex = i >> 2;
            String modelName = guns.get(gunIndex) + suffix;

            // Register gun model
            switch (gunIndex) {
                case 0 -> {
                    // Pistol
                    override.model(getModelFile(modelName)).end();
                    getBuilder(modelName).parent(getModelFile("gun_short" + action)).texture("layer0", "item/" + modelName);
                }
                case 1 -> {
                    // Rifle
                    override.predicate(Constants.ItemProperties.PROPERTY_PIERCING, 1.0F)
                            .model(getModelFile(modelName)).end();
                    getBuilder(modelName).parent(getModelFile("gun_long" + action)).texture("layer0", "item/" + modelName);
                }
                case 2 -> {
                    // Volley gun
                    override.predicate(Constants.ItemProperties.PROPERTY_MULTISHOT, 1.0F)
                            .model(getModelFile(modelName)).end();
                    getBuilder(modelName).parent(getModelFile("gun_long" + action)).texture("layer0", "item/" + modelName);
                }
            }
        }
    }

    private ResourceLocation getModelLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(modid, "item/" + name);
    }

    private ModelFile getModelFile(String name) {
        return new ModelFile.UncheckedModelFile(getModelLocation(name));
    }

    private ResourceLocation getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
