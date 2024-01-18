package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
        ModelFile generatedModel = new ModelFile.UncheckedModelFile("item/generated");

        registerSimpleItemModel(ModItems.BULLET_CHILI, generatedModel);
        registerSimpleItemModel(ModItems.CURVED_CHILI, generatedModel);
        registerSimpleItemModel(ModItems.CHILI_SEEDS, generatedModel);
        registerSimpleItemModel(ModItems.CHILI_BULLET, generatedModel);
    }

    private void registerSimpleItemModel(Item item, ModelFile modelFile) {
        ResourceLocation item_id = getItemId(item);

        if (item_id != null) {
            final String item_name = item_id.getPath();
            getBuilder(item_name).parent(modelFile).texture("layer0", "item/" + item_name);
        }
    }

    private ResourceLocation getItemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }
}
