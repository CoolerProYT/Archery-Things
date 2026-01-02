package com.coolerpromc.archerythings.model.quiver;

import com.coolerpromc.archerythings.ArcheryThings;
import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.QuiverData;
import com.coolerpromc.archerythings.item.custom.ModQuiverItem;
import com.coolerpromc.archerythings.model.ModModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import org.joml.Quaternionf;

public class QuiverLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ArcheryThings.MODID, "textures/entity/quiver.png");

    private final QuiverModel model;

    public QuiverLayer(RenderLayerParent<S, M> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new QuiverModel(modelSet.bakeLayer(ModModelLayers.QUIVER), RenderType::entityCutoutNoCull);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, S renderState, float yRot, float partialTick) {
        if (!renderState.chestEquipment.isEmpty() && (renderState.chestEquipment.getItem() instanceof ModQuiverItem || renderState.chestEquipment.has(ModDataComponents.STORED_QUIVER.get()))) {
            ItemStack quiver = renderState.chestEquipment;
            float z = 0.06875F;
            if (quiver.has(ModDataComponents.STORED_QUIVER.get())){
                quiver = quiver.get(ModDataComponents.STORED_QUIVER.get()).stack();
                z = 0.13F;
            }
            QuiverData data = quiver.getOrDefault(ModDataComponents.QUIVER_DATA.get(), QuiverData.EMPTY);
            int selected = quiver.getOrDefault(ModDataComponents.SELECTED.get(), 0);

            ItemModelResolver resolver = new ItemModelResolver(Minecraft.getInstance().getModelManager());
            ItemStackRenderState state = new ItemStackRenderState();

            if (selected >= 0 && selected < data.getSlots()){
                resolver.updateForTopItem(state, data.getStackInSlot(selected), ItemDisplayContext.FIXED, null, null, 1);
            }

            int color = DyedItemColor.getOrDefault(quiver, DyedItemColor.LEATHER_COLOR);

            poseStack.pushPose();
            poseStack.translate(0.0F, -0.053125F, z);
            nodeCollector.submitModel(this.model, renderState, poseStack, RenderType.entityCutoutNoCull(TEXTURE), packedLight, OverlayTexture.NO_OVERLAY, color, null, renderState.outlineColor, null);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(-0.3f, 0f, 0.35f);
            poseStack.scale(0.5f, 0.5f, 0.5f);
            poseStack.last().rotate(new Quaternionf().rotateZ(190));
            state.submit(poseStack, nodeCollector, packedLight, OverlayTexture.NO_OVERLAY, renderState.outlineColor);
            poseStack.popPose();
        }
    }
}