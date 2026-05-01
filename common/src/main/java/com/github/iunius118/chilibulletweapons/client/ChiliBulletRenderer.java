package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.CommonClass;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class ChiliBulletRenderer extends EntityRenderer<ChiliBullet, ChiliBulletRenderState> {
    public static final Identifier TEXTURE_LOCATION = CommonClass.modLocation("textures/entity/chili_bullet.png");
    private final ChiliBulletModel<EntityRenderState> model;

    public ChiliBulletRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new ChiliBulletModel<>(context.bakeLayer(ChiliBulletModel.LAYER_LOCATION));
    }

    @Override
    public void submit(ChiliBulletRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                       CameraRenderState camera) {
        if (state.age > 1) {
            // Each bullet becomes visible from age 2 to preserve the player's view
            poseStack.pushPose();
            model.setupAnim(state);
            submitNodeCollector.submitModel(model, state, poseStack, getTextureLocation(state), state.lightCoords,
                    OverlayTexture.NO_OVERLAY, state.outlineColor, null);
            poseStack.popPose();
        }

        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    protected Identifier getTextureLocation(EntityRenderState state) {
        return TEXTURE_LOCATION;
    }

    @Override
    public ChiliBulletRenderState createRenderState() {
        return new ChiliBulletRenderState();
    }

    @Override
    public void extractRenderState(ChiliBullet entity, ChiliBulletRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.age = entity.getAge();
    }
}
