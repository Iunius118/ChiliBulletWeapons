package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.client.*;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

public class ChiliBulletWeaponsClient {

    public static void onInitializeClient(IEventBus modEventBus) {
        modEventBus.addListener(ChiliBulletWeaponsClient::onClientSetup);
        modEventBus.addListener(ChiliBulletWeaponsClient::onItemColorHandlerEvent);
        modEventBus.addListener(ChiliBulletWeaponsClient::onRegisterLayerDefinitions);
        modEventBus.addListener(ChiliBulletWeaponsClient::onRegisterEntityRenderer);
    }

    private static void onClientSetup(final FMLClientSetupEvent event) {
        registerItemProperties();
    }

    private static void registerItemProperties() {
        ItemProperties.register(ModItems.GUN, Constants.ItemProperties.PROPERTY_GUN, ModItemProperties.PROPERTY_GUN);
    }

    public static void onItemColorHandlerEvent(RegisterColorHandlersEvent.Item event) {
        event.register(new DyedGunItemColor(), ModItems.GUN);
    }

    private static void onRegisterLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ChiliBulletModel.LAYER_LOCATION, ChiliBulletModel::createBodyLayer);
    }

    private static void onRegisterEntityRenderer(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.CHILI_ARROW, ChiliArrowRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CHILI_BULLET, ChiliBulletRenderer::new);
    }
}
