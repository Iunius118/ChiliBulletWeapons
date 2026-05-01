package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.CommonClass;
import com.github.iunius118.chilibulletweapons.entity.ChiliArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class ChiliArrowRenderer extends ArrowRenderer<ChiliArrow, ArrowRenderState> {
    public static final Identifier TEXTURE_LOCATION = CommonClass.modLocation("textures/entity/chili_arrow.png");

    public ChiliArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected Identifier getTextureLocation(ArrowRenderState state) {
        return TEXTURE_LOCATION;
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }
}
