package com.github.iunius118.chilibulletweapons;

import com.github.iunius118.chilibulletweapons.client.*;
import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.mixin.client.RangeSelectItemModelPropertiesAccessor;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;

public class ChiliBulletWeaponsClient {

    public static void onInitializeClient(BusGroup modBusGroup) {
        registerItemModelProperties();
        registerItemTintSources();
        EntityRenderersEvent.RegisterLayerDefinitions.BUS
                .addListener(ChiliBulletWeaponsClient::onRegisterLayerDefinitions);
        EntityRenderersEvent.RegisterRenderers.BUS.addListener(ChiliBulletWeaponsClient::onRegisterEntityRenderer);
    }

    private static void registerItemModelProperties() {
        RangeSelectItemModelPropertiesAccessor.getIdMapper()
                .put(Constants.ItemProperties.PROPERTY_GUN, GunItemModelProperty.MAP_CODEC);
    }

    private static void registerItemTintSources() {
        ItemTintSources.ID_MAPPER.put(Constants.ItemTintSources.DYED_GUN, DyedGunItemTintSource.MAP_CODEC);
    }

    private static void onRegisterLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ChiliBulletModel.LAYER_LOCATION, ChiliBulletModel::createBodyLayer);
    }

    private static void onRegisterEntityRenderer(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.CHILI_ARROW, ChiliArrowRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CHILI_BULLET, ChiliBulletRenderer::new);
    }
}
