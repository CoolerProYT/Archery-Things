package com.coolerpromc.archerythings.model.quiver;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class QuiverModel extends Model {
    private final ModelPart root;
    private final ModelPart quiver;

    public QuiverModel(ModelPart root, Function<ResourceLocation, RenderType> func) {
        super(root.getChild("quiver"), func);
        this.root = root;
        this.quiver = root.getChild("quiver");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("quiver", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -15.0F, -2.0F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -12.0F, 3.0F, 0.0F, 0.0F, -0.6981F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public ModelPart getQuiver() {
        return this.quiver;
    }
}