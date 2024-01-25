package com.github.iunius118.chilibulletweapons.data;

import com.github.iunius118.chilibulletweapons.item.ChiliBulletPistol;
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
        basicItem(ModItems.BULLET_CHILI);
        basicItem(ModItems.CURVED_CHILI);
        basicItem(ModItems.CHILI_SEEDS);
        basicItem(ModItems.CHILI_BULLET);
        registerPistolModel(ModItems.PISTOL);
        registerRifleModel(ModItems.RIFLE);
    }

    private void registerSimpleItemModel(Item item, ModelFile modelFile, String suffix) {
        ResourceLocation item_id = getItemId(item);

        if (item_id != null) {
            final String item_name = item_id.getPath();
            getBuilder(item_name).parent(modelFile).texture("layer0", "item/" + item_name + suffix);
        }
    }

    private void registerPistolModel(Item item) {
        ResourceLocation item_id = getItemId(item);

        if (item_id == null) {
            return;
        }

        final String item_name = item_id.getPath();
        final ResourceLocation reload_model = new ResourceLocation(item_id.getNamespace(), "item/" + item_name + "_reload");
        final ModelFile modelFile = new ModelFile.UncheckedModelFile("item/generated");
        getBuilder(item_name).parent(modelFile).texture("layer0", "item/" + item_name)
                .transforms()
                    .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(0.68F).rotation(0F, -90F, 25F).translation(3.0F, 3.2F, 1.13F).end()
                    .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).scale(0.68F).rotation(0F, 90F, -25F).translation(3.0F, 3.2F, 1.13F).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).scale(0.85F).rotation(-40F, -90F, 0F).translation(0F, 1F, -0.25F).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).scale(0.85F).rotation(-40F, 90F, 0F).translation(0F, 1F, -0.25F).end()
                    .end()
                .override().predicate(ChiliBulletPistol.PROPERTY_RELOAD, 1F).model(new ModelFile.UncheckedModelFile(reload_model)).end();

        getBuilder(item_name + "_reload").parent(modelFile).texture("layer0", reload_model.getPath())
                .transforms()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(0.68F).rotation(0F, -90F, 25F).translation(1.13F, 3.2F, 1.13F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).scale(0.68F).rotation(0F, 90F, -25F).translation(1.13F, 3.2F, 1.13F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).scale(0.85F).rotation(-40F, -90F, 0F).translation(0F, 1F, -0.25F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).scale(0.85F).rotation(-40F, 90F, 0F).translation(0F, 1F, -0.25F).end()
                .end();
    }

    private void registerRifleModel(Item item) {
        ResourceLocation item_id = getItemId(item);

        if (item_id == null) {
            return;
        }

        final String item_name = item_id.getPath();
        final ModelFile modelFile = new ModelFile.UncheckedModelFile("item/generated");
        final ResourceLocation reload_model = new ResourceLocation(item_id.getNamespace(), "item/" + item_name + "_reload");
        getBuilder(item_name).parent(modelFile).texture("layer0", "item/" + item_name)
                .transforms()
                    .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(0.68F).rotation(0F, -90F, 25F).translation(3.0F, 3.2F, -0.4F).end()
                    .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).scale(0.68F).rotation(0F, 90F, -25F).translation(3.0F, 3.2F, -0.4F).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).scale(0.85F).rotation(-40F, -90F, 0F).translation(0F, 0.5F, -2.2F).end()
                    .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).scale(0.85F).rotation(-40F, 90F, 0F).translation(0F, 0.5F, -2.2F).end()
                .end()
                .override().predicate(ChiliBulletPistol.PROPERTY_RELOAD, 1F).model(new ModelFile.UncheckedModelFile(reload_model)).end();

        getBuilder(item_name + "_reload").parent(modelFile).texture("layer0", reload_model.getPath())
                .transforms()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).scale(0.68F).rotation(0F, -90F, 25F).translation(1.13F, 3.2F, -0.4F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).scale(0.68F).rotation(0F, 90F, -25F).translation(1.13F, 3.2F, -0.4F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).scale(0.85F).rotation(-40F, -90F, 0F).translation(0F, 0.5F, -2.2F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).scale(0.85F).rotation(-40F, 90F, 0F).translation(0F, 0.5F, -2.2F).end()
                .end();
    }

    private ResourceLocation getItemId(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }
}
