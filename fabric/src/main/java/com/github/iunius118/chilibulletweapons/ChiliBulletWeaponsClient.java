package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.client.*;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;

public class ChiliBulletWeaponsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerItemModelProperties();
        registerItemTintSources();
        registerEntityModelLayer();
        registerEntityRenderer();
    }

    private void registerItemModelProperties() {
        RangeSelectItemModelProperties.ID_MAPPER
                .put(Constants.ItemProperties.PROPERTY_GUN, GunItemModelProperty.MAP_CODEC);
    }

    private void registerItemTintSources() {
        ItemTintSources.ID_MAPPER.put(Constants.ItemTintSources.DYED_GUN, DyedGunItemTintSource.MAP_CODEC);
    }

    private void registerEntityModelLayer() {
        ModelLayerRegistry.registerModelLayer(ChiliBulletModel.LAYER_LOCATION, ChiliBulletModel::createBodyLayer);
    }

    private void registerEntityRenderer() {
        EntityRenderers.register(ModEntityTypes.CHILI_ARROW, ChiliArrowRenderer::new);
        EntityRenderers.register(ModEntityTypes.CHILI_BULLET, ChiliBulletRenderer::new);
    }
}
