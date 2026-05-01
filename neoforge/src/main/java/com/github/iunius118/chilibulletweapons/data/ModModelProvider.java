package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.Constants;
import com.github.iunius118.chilibulletweapons.client.DyedGunItemTintSource;
import com.github.iunius118.chilibulletweapons.client.GunItemModelProperty;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        generateBlockModels(blockModels);
        generateItemModels(itemModels);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        // No blocks in this mod
        return Stream.empty();
    }

    private void generateBlockModels(BlockModelGenerators blockModels) {
        // No blocks in this mod
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.empty();
    }

    private void generateItemModels(ItemModelGenerators itemModels) {
        var itemModelOutput = itemModels.itemModelOutput;
        var modelOutput = itemModels.modelOutput;

        // Plants
        itemModels.generateFlatItem(ModItems.BULLET_CHILI, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BULLET_CHILI_SACK, ModelTemplates.FLAT_ITEM);

        // Weapons
        itemModels.generateFlatItem(ModItems.CHILI_ARROW, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHILI_BULLET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UPGRADE_GUN_BAYONET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UPGRADE_GUN_BARREL, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.UPGRADE_GUN_MECHANISM, ModelTemplates.FLAT_ITEM);
        itemModelOutput.accept(ModItems.GUN, generateGunModel(modelOutput));
        itemModelOutput.accept(ModItems.MACHINE_GUN, generateMachineGunModel(modelOutput));
    }

    private ItemModel.Unbaked generateGunModel(BiConsumer<Identifier, ModelInstance> output) {
        List<String> guns = List.of("pistol", "rifle", "volley_gun");
        List<RangeSelectItemModel.Entry> entries = new ArrayList<>();

        // Variable `i` is model type flag (0-31):
        //     [isDyed].b [isLoading].b [hasBayonet].b [isVolleyGun].b [isRifle].b
        // If isVolleyGun and isRifle are both true, volley gun models will be used.
        for(int i = 0; i < 2 * 2 * 2 * 4; i++) {
            int gunIndex = i & 3;

            if (gunIndex == 3) {
                // Skip, as gun index 3 does not exist (only 3 types of guns exist)
                continue;
            }

            // Determine the property value of the gun model based on the bits of `i`
            boolean isDyed = (i & 16) != 0;
            boolean isLoading = (i & 8) != 0;
            boolean hasBayonet = (i & 4) != 0;
            boolean isMultiShot = gunIndex == 2;
            boolean isPiercing = gunIndex == 1;
            float property = GunItemModelProperty.getValue(isDyed, isLoading, hasBayonet, isMultiShot, isPiercing);

            // Determine the model and texture names based on the gun type
            String prefix = isDyed ? "dyed_gun/dyed_" : "";
            String suffix = (hasBayonet ? "_bayoneted" : "") + (isLoading ? "_loading" : "");
            String action = isLoading ? "_open" : "_closed";
            String parentName = switch (gunIndex) {
                case 0 -> "gun_short" + action; // Pistol
                case 1 -> "gun_long" + action;  // Rifle
                case 2 -> "gun_long" + action;  // Volley gun
                default -> throw new IllegalStateException("Unexpected gun index: " + gunIndex);
            };
            String modelName = prefix + guns.get(gunIndex) + suffix;
            //Constants.LOG.info("Gun model: name={}, property={}, parent={}", modelName, property, parentName);

            // Create gun model
			Identifier parent = getItemLocation(parentName);
            Identifier gunModel = getItemLocation(modelName);
            ItemModel.Unbaked gunItemModel;

            if (isDyed) {
                if (hasBayonet) {
                    gunItemModel = ItemModelUtils.tintedModel(
                            new ModelTemplate(Optional.of(parent), Optional.empty(),
                                TextureSlot.LAYER0, TextureSlot.LAYER1, TextureSlot.LAYER2)
                                .create(gunModel, TextureMapping.layered(
                                        getMaterial(modelName + "_0"),
                                        getMaterial(modelName + "_1"),
                                        getMaterial(modelName + "_2")), output),
                                new DyedGunItemTintSource(0),
                                new DyedGunItemTintSource(1),
                                new DyedGunItemTintSource(2));
                } else {
                    gunItemModel = ItemModelUtils.tintedModel(
                            new ModelTemplate(Optional.of(parent), Optional.empty(),
                                    TextureSlot.LAYER0, TextureSlot.LAYER1)
                                    .create(gunModel, TextureMapping.layered(
                                            getMaterial(modelName + "_0"),
                                            getMaterial(modelName + "_1")), output),
                                    new DyedGunItemTintSource(0),
                                    new DyedGunItemTintSource(1));
                }
            } else {
                gunItemModel = ItemModelUtils.plainModel(
                        new ModelTemplate(Optional.of(parent), Optional.empty(),
                                TextureSlot.LAYER0)
                                .create(gunModel, TextureMapping.layer0(getMaterial(modelName)), output));
            }

            // Add the gun model entry to the list of entries for the RangeSelectItemModel
            entries.add(new RangeSelectItemModel.Entry(property, gunItemModel));
        }

        return ItemModelUtils.rangeSelect(new GunItemModelProperty(), entries);
    }

    private ItemModel.Unbaked generateMachineGunModel(BiConsumer<Identifier, ModelInstance> output) {
        Identifier model = getItemLocation(getItemId(ModItems.MACHINE_GUN).getPath());
        return ItemModelUtils.plainModel(
                new ModelTemplate(Optional.of(getItemLocation("gun_long_closed")), Optional.empty(),
                        TextureSlot.LAYER0)
                        .create(model, TextureMapping.layer0(new Material(model)), output));
    }

    private Identifier getItemLocation(String name) {
        return Identifier.fromNamespaceAndPath(this.modId, "item/" + name);
    }

    private Material getMaterial(String name) {
        return new Material(getItemLocation(name));
    }

    private Identifier getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
