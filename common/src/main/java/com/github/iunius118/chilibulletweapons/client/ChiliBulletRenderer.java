package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.CommonClass;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ChiliBulletRenderer extends EntityRenderer<ChiliBullet> {
    public static final ResourceLocation TEXTURE_LOCATION = CommonClass.modLocation("textures/entity/chili_bullet.png");
    private final ChiliBulletModel<ChiliBullet> model;

    public ChiliBulletRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new ChiliBulletModel<>(context.bakeLayer(ChiliBulletModel.LAYER_LOCATION));
    }

    @Override
    public void render(ChiliBullet chiliBullet, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (chiliBullet.getAge() > 1) {
            // Each bullet is rendered from the second tick to maintain the player's field of view
            poseStack.pushPose();
            model.setupAnim(chiliBullet, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
            VertexConsumer buffer = bufferSource.getBuffer(model.renderType(TEXTURE_LOCATION));
            model.renderToBuffer(poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
            poseStack.popPose();
        }

        super.render(chiliBullet, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ChiliBullet entity) {
        return TEXTURE_LOCATION;
    }
}
