package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.client.*;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

public class ChiliBulletWeaponsClient {

    public static void onInitializeClient(IEventBus modEventBus) {
        modEventBus.addListener(ChiliBulletWeaponsClient::registerItemProperties);
        modEventBus.addListener(ChiliBulletWeaponsClient::onItemColorHandlerEvent);
        modEventBus.addListener(ChiliBulletWeaponsClient::onRegisterLayerDefinitions);
        modEventBus.addListener(ChiliBulletWeaponsClient::onRegisterEntityRenderer);
        modEventBus.addListener(ChiliBulletWeaponsClient::onRegisterClientExtensions);
    }

    private static void registerItemProperties(final RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(Constants.ItemProperties.PROPERTY_GUN, GunItemModelProperty.MAP_CODEC);
    }

    public static void onItemColorHandlerEvent(final RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(Constants.ItemTintSources.DYED_GUN, DyedGunItemTintSource.MAP_CODEC);
    }

    private static void onRegisterLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ChiliBulletModel.LAYER_LOCATION, ChiliBulletModel::createBodyLayer);
    }

    private static void onRegisterEntityRenderer(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.CHILI_ARROW, ChiliArrowRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CHILI_BULLET, ChiliBulletRenderer::new);
    }

    private static void onRegisterClientExtensions(final RegisterClientExtensionsEvent event) {
        event.registerItem(new GunClientItemExtensions(), ModItems.GUN, ModItems.MACHINE_GUN);
    }
}
