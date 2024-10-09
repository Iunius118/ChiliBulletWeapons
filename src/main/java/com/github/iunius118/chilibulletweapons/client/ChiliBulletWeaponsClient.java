package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.block.ModBlocks;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGun;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;

public class ChiliBulletWeaponsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerItemProperties();
        registerCropBlockLayer();
        registerEntityModelLayer();
        registerEntityRenderer();
    }

    private void registerItemProperties() {
        ItemProperties.register(ModItems.GUN, ChiliBulletGun.PROPERTY_LOADING, (stack, l, e, i) -> ChiliBulletGun.isLoading(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.GUN, ChiliBulletGun.PROPERTY_MULTISHOT, (stack, l, e, i) -> ChiliBulletGun.getMultishotLevel(stack) != 0 ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.GUN, ChiliBulletGun.PROPERTY_PIERCING, (stack, l, e, i) -> ChiliBulletGun.getPiercingLevel(stack) > 0 ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.BAYONETED_GUN, ChiliBulletGun.PROPERTY_LOADING, (stack, l, e, i) -> ChiliBulletGun.isLoading(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.BAYONETED_GUN, ChiliBulletGun.PROPERTY_MULTISHOT, (stack, l, e, i) -> ChiliBulletGun.getMultishotLevel(stack) != 0 ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.BAYONETED_GUN, ChiliBulletGun.PROPERTY_PIERCING, (stack, l, e, i) -> ChiliBulletGun.getPiercingLevel(stack) > 0 ? 1.0F : 0.0F);
    }

    private void registerCropBlockLayer() {
        // Set render type of crop block to cutout
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHILI_PEPPER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CURVED_CHILI_STRING, RenderType.cutout());
    }

    private void registerEntityModelLayer() {
        EntityModelLayerRegistry.registerModelLayer(ChiliBulletModel.LAYER_LOCATION, ChiliBulletModel::createBodyLayer);
    }

    private void registerEntityRenderer() {
        EntityRendererRegistry.register(ModEntityTypes.CHILI_ARROW, ChiliArrowRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.CHILI_BULLET, ChiliBulletRenderer::new);
    }
}
