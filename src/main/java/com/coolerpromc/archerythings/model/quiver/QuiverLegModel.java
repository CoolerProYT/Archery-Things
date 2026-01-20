package com.coolerpromc.archerythings.model.quiver;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

public class QuiverLegModel extends Model {
    private final ModelPart root;
    private final ModelPart quiver;

    public QuiverLegModel(ModelPart root, Function<Identifier, RenderType> func) {
        super(root.getChild("quiver"), func);
        this.root = root;
        this.quiver = root.getChild("quiver");
    }

    public static LayerDefinition createLegLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("quiver", CubeListBuilder.create().texOffs(0, 13).addBox(-6.0F, -12.0F, 1.0F, 11.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(-1, 13).addBox(-6.0F, -11.0F, -5.0F, 11.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(12, 7).addBox(0.0F, -1.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -10.5F, -2.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(10, 0).addBox(0.2395F, -4.5228F, -1.0F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.7605F, -4.5228F, 1.0F, 3.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.3372F, -8.3498F, -1.0F, 1.5708F, 0.8727F, 1.5708F));

        PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.2395F, -4.5228F, 1.0F, 3.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(2.7605F, -4.5228F, -1.0F, 1.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(17, 2).addBox(-0.2395F, 5.4772F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.3372F, -8.3498F, -1.0F, 1.5708F, -0.8727F, -1.5708F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public ModelPart getQuiver() {
        return this.quiver;
    }
}