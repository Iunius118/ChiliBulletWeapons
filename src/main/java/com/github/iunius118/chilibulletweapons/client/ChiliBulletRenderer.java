package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.ChiliBulletWeapons;
import com.github.iunius118.chilibulletweapons.entity.ChiliBullet;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class ChiliBulletRenderer extends EntityRenderer<ChiliBullet> {
    public static final ResourceLocation TEXTURE_LOCATION = ChiliBulletWeapons.makeId("textures/item/chili_bullet.png");

    protected ChiliBulletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    private static final Vector3f[] vertices = {
            new Vector3f(-0.03125F, 0.03125F, 0.03125F),
            new Vector3f(-0.03125F, -0.03125F, 0.03125F),
            new Vector3f(0.03125F, 0.03125F, 0.03125F),
            new Vector3f(0.03125F, -0.03125F, 0.03125F),
            new Vector3f(0.03125F, 0.03125F, -0.03125F),
            new Vector3f(0.03125F, -0.03125F, -0.03125F),
            new Vector3f(-0.03125F, 0.03125F, -0.03125F),
            new Vector3f(-0.03125F, -0.03125F, -0.03125F)
    };

    @Override
    public void render(ChiliBullet chiliBullet, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        poseStack.pushPose();
        var vertexConsumer = bufferSource.getBuffer(ChiliBulletRenderType.CHILI_BULLET);
        Matrix4f pose = poseStack.last().pose();

        renderQuad(vertexConsumer, pose, light, vertices[1], vertices[3], vertices[2], vertices[0]);
        renderQuad(vertexConsumer, pose, light, vertices[3], vertices[5], vertices[4], vertices[2]);
        renderQuad(vertexConsumer, pose, light, vertices[5], vertices[7], vertices[6], vertices[4]);
        renderQuad(vertexConsumer, pose, light, vertices[7], vertices[1], vertices[0], vertices[6]);
        renderQuad(vertexConsumer, pose, light, vertices[0], vertices[2], vertices[4], vertices[6]);
        renderQuad(vertexConsumer, pose, light, vertices[7], vertices[5], vertices[3], vertices[1]);

        poseStack.popPose();
        super.render(chiliBullet, entityYaw, partialTicks, poseStack, bufferSource, light);
    }

    private void renderQuad(VertexConsumer vertexConsumer, Matrix4f pose, int light, Vector3f... v) {
        for (int i = 0; i < 4; i++) {
            Vector3f vertex = new Vector3f(v[i]);
            vertexConsumer.vertex(pose, vertex.x(), vertex.y(), vertex.z()).color(155, 0, 0, 255).uv2(light).endVertex();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(ChiliBullet p_114482_) {
        return TEXTURE_LOCATION;
    }

    private static class ChiliBulletRenderType extends RenderType {
        public static final RenderType CHILI_BULLET = create("chili_bullet", DefaultVertexFormat.POSITION_COLOR_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(POSITION_COLOR_LIGHTMAP_SHADER).setLightmapState(LIGHTMAP).createCompositeState(true));

        public ChiliBulletRenderType(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
            super(string, vertexFormat, mode, i, bl, bl2, runnable, runnable2);
        }
    }
}
