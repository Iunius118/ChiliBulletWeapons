package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.entity.ChiliArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ChiliArrowRenderer extends ArrowRenderer<ChiliArrow> {
    public static final ResourceLocation TEXTURE_LOCATION = ChiliBulletWeapons.makeId("textures/entity/chili_arrow.png");

    public ChiliArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ChiliArrow entity) {
        return TEXTURE_LOCATION;
    }
}
