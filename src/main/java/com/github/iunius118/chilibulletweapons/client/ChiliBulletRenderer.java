package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ChiliBulletRenderer extends EntityRenderer<ChiliBullet> {
    public static final ResourceLocation TEXTURE_LOCATION = ChiliBulletWeapons.makeId("textures/entity/chili_bullet.png");
    private final ChiliBulletModel<ChiliBullet> model;

    protected ChiliBulletRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new ChiliBulletModel<>(context.bakeLayer(ChiliBulletModel.LAYER_LOCATION));
    }

    @Override
    public void render(ChiliBullet chiliBullet, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
        if (chiliBullet.getAge() > 0) {
            // Each bullet is rendered from the second tick to maintain the player's field of view
            poseStack.pushPose();
            model.setupAnim(chiliBullet, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(TEXTURE_LOCATION));
            model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }

        super.render(chiliBullet, entityYaw, partialTicks, poseStack, multiBufferSource, light);
    }

    @Override
    public ResourceLocation getTextureLocation(ChiliBullet chiliBullet) {
        return TEXTURE_LOCATION;
    }
}
