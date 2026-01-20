package com.coolerpromc.archerythings.model.quiver;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

public class QuiverModel extends Model {
    private final ModelPart root;
    private final ModelPart quiver;

    public QuiverModel(ModelPart root, Function<Identifier, RenderType> func) {
        super(root.getChild("quiver"), func);
        this.root = root;
        this.quiver = root.getChild("quiver");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("quiver", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, -1.0F, -4.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(13, 0).addBox(3.0F, -15.0F, -4.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -15.0F, -2.0F, 5.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -12.0F, 6.0F, 0.0F, 0.0F, -0.6981F));

        PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(13, 0).addBox(-0.5F, -7.5F, -1.0F, 1.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.736F, -16.1384F, 3.0F, 0.0F, 3.1416F, -0.6981F));

        PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -7.5F, -0.5F, 5.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4379F, -18.0667F, 1.5F, 0.0F, 3.1416F, -0.6981F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public ModelPart getQuiver() {
        return this.quiver;
    }
}