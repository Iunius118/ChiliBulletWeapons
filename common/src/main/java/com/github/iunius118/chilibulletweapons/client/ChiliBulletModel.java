package com.github.iunius118.chilibulletweapons.client;

import com.github.iunius118.chilibulletweapons.CommonClass;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class ChiliBulletModel<T extends EntityRenderState> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(CommonClass.modLocation("chili_bullet"), "main");
    private final ModelPart bullet;

    public ChiliBulletModel(ModelPart root) {
        super(root, RenderTypes::entityCutout);
        this.bullet = root.getChild("bullet");
    }

    public static LayerDefinition createBodyLayer() {
        var meshDefinition = new MeshDefinition();
        var partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("bullet",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 16, 16);
    }
}
