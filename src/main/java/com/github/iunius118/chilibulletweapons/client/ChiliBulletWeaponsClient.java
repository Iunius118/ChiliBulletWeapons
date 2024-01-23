package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.entity.ModEntityTypes;
import com.github.iunius118.chilibulletweapons.item.ChiliBulletPistol;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ChiliBulletWeaponsClient {
    public static void onInitializeClient(IEventBus modEventBus) {
        modEventBus.addListener(ChiliBulletWeaponsClient::onRegisterEntityRenderer);
        registerItemProperties();
    }

    public static void onRegisterEntityRenderer(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.CHILI_BULLET, ChiliBulletRenderer::new);
    }

    public static void registerItemProperties() {
        ItemProperties.registerGeneric(ChiliBulletPistol.PROPERTY_RELOAD, (stack, l, e, i) -> ChiliBulletPistol.isReloading(stack) ? 1.0F : 0.0F);
    }
}
