package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.client.ChiliArrowRenderer;
import com.github.iunius118.chilibulletweapons.client.ChiliBulletModel;
import com.github.iunius118.chilibulletweapons.client.ChiliBulletRenderer;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletGun;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ChiliBulletWeaponsClient {
    public static void onInitializeClient(IEventBus modEventBus) {
        modEventBus.addListener(ChiliBulletWeaponsClient::onClientSetup);
        modEventBus.addListener(ChiliBulletWeaponsClient::onRegisterLayerDefinitions);
        modEventBus.addListener(ChiliBulletWeaponsClient::onRegisterEntityRenderer);
    }

    private static void onClientSetup(final FMLClientSetupEvent event) {
        registerItemProperties();
    }

    private static void registerItemProperties() {
        ItemProperties.register(ModItems.GUN, ChiliBulletGun.PROPERTY_LOADING,
                (stack, l, e, i) -> ChiliBulletGun.isLoading(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.GUN, ChiliBulletGun.PROPERTY_MULTISHOT,
                (stack, l, e, i) -> ChiliBulletGun.getMultishotLevel(stack) != 0 ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.GUN, ChiliBulletGun.PROPERTY_PIERCING,
                (stack, l, e, i) -> ChiliBulletGun.getPiercingLevel(stack) > 0 ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.BAYONETED_GUN, ChiliBulletGun.PROPERTY_LOADING,
                (stack, l, e, i) -> ChiliBulletGun.isLoading(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.BAYONETED_GUN, ChiliBulletGun.PROPERTY_MULTISHOT,
                (stack, l, e, i) -> ChiliBulletGun.getMultishotLevel(stack) != 0 ? 1.0F : 0.0F);
        ItemProperties.register(ModItems.BAYONETED_GUN, ChiliBulletGun.PROPERTY_PIERCING,
                (stack, l, e, i) -> ChiliBulletGun.getPiercingLevel(stack) > 0 ? 1.0F : 0.0F);
    }

    private static void onRegisterLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ChiliBulletModel.LAYER_LOCATION, ChiliBulletModel::createBodyLayer);
    }

    private static void onRegisterEntityRenderer(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.CHILI_ARROW, ChiliArrowRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CHILI_BULLET, ChiliBulletRenderer::new);
    }
}
