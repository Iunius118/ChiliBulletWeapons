package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.client.*;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.item.ItemProperties;

public class ChiliBulletWeaponsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerItemProperties();
        registerItemColors();
        registerEntityModelLayer();
        registerEntityRenderer();
    }

    private void registerItemProperties() {
        ItemProperties.register(ModItems.GUN, Constants.ItemProperties.PROPERTY_GUN, ModItemProperties.PROPERTY_GUN);
    }

    private void registerItemColors() {
        ColorProviderRegistry.ITEM.register(new DyedGunItemColor(), ModItems.GUN);
    }

    private void registerEntityModelLayer() {
        EntityModelLayerRegistry.registerModelLayer(ChiliBulletModel.LAYER_LOCATION, ChiliBulletModel::createBodyLayer);
    }

    private void registerEntityRenderer() {
        EntityRendererRegistry.register(ModEntityTypes.CHILI_ARROW, ChiliArrowRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.CHILI_BULLET, ChiliBulletRenderer::new);
    }
}
