package com.coolerpromc.archerythings.model.quiver;

import com.coolerpromc.archerythings.Constants;
import com.coolerpromc.archerythings.component.ModDataComponents;
import com.coolerpromc.archerythings.component.data.QuiverData;
import com.coolerpromc.archerythings.item.custom.ModQuiverItem;
import com.coolerpromc.archerythings.model.ModModelLayers;
import com.coolerpromc.archerythings.util.LivingEntityRenderStateAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class QuiverLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {

    private static final Identifier TEXTURE = Constants.id("textures/entity/quiver.png");
    private static final Identifier LEG_TEXTURE = Constants.id("textures/entity/quiver_leg.png");

    private final QuiverModel<S> model;
    private final QuiverLegModel legModel;

    public QuiverLayer(RenderLayerParent<S, M> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new QuiverModel<>(modelSet.bakeLayer(ModModelLayers.QUIVER), RenderTypes::entityCutout);
        this.legModel = new QuiverLegModel(modelSet.bakeLayer(ModModelLayers.QUIVER_LEG), RenderTypes::entityCutout);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, S renderState, float yRot, float partialTick) {
        ItemStack quiverStack = ((LivingEntityRenderStateAccessor) renderState).archerythings$getQuiverStack();
        if (!renderState.chestEquipment.isEmpty() && (renderState.chestEquipment.getItem() instanceof ModQuiverItem || renderState.chestEquipment.has(ModDataComponents.STORED_QUIVER.get())) || quiverStack != null) {
            ItemStack quiver = renderState.chestEquipment;
            if (quiverStack != null){
                quiver = quiverStack;
            }
            float z = 0.06875F;
            float z1 = -0.04875F;
            if (quiver.has(ModDataComponents.STORED_QUIVER.get())){
                quiver = quiver.get(ModDataComponents.STORED_QUIVER.get()).stack();
                z = 0.13F;
                z1 = 0.028f;
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
            if (renderState.isCrouching){
                poseStack.mulPose(Axis.XP.rotationDegrees(30));
                poseStack.translate(0.0F, -0.053125F, z1);
            }
            else{
                poseStack.translate(0.0F, -0.053125F, z);
            }
            nodeCollector.submitModel(this.model, renderState, poseStack, RenderTypes.entityCutout(TEXTURE), packedLight, OverlayTexture.NO_OVERLAY, color, null, renderState.outlineColor, null);
            poseStack.popPose();

            poseStack.pushPose();
            if (renderState.isCrouching){
                poseStack.mulPose(Axis.XP.rotationDegrees(30));
                poseStack.translate(-0.3f, 0f, 0.2f);
            }
            else{
                poseStack.translate(-0.3f, 0f, 0.325f);
            }
            poseStack.mulPose(Axis.ZP.rotationDegrees(85));
            poseStack.scale(0.5f, 0.5f, 0.5f);
            state.submit(poseStack, nodeCollector, packedLight, OverlayTexture.NO_OVERLAY, renderState.outlineColor);
            poseStack.popPose();
        }

        if (!renderState.legsEquipment.isEmpty() && (renderState.legsEquipment.getItem() instanceof ModQuiverItem || renderState.legsEquipment.has(ModDataComponents.STORED_QUIVER.get()))) {
            ItemStack quiver = renderState.legsEquipment;
            float z = 0.06875F;
            float z1 = 0.3f;
            if (quiver.has(ModDataComponents.STORED_QUIVER.get())){
                quiver = quiver.get(ModDataComponents.STORED_QUIVER.get()).stack();
                z = 0.13F;
                z1 = 0.028f;
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
            if (renderState.isCrouching){
                poseStack.mulPose(Axis.XP.rotationDegrees(30));
                poseStack.translate(0.0F, -0.053125F, -0.01);
            }
            else{
                poseStack.translate(0.0F, -0.053125F, z);
            }
            nodeCollector.submitModel(this.legModel, renderState, poseStack, RenderTypes.entityCutout(LEG_TEXTURE), packedLight, OverlayTexture.NO_OVERLAY, color, null, renderState.outlineColor, null);
            poseStack.popPose();

            poseStack.pushPose();
            if (renderState.isCrouching){
                poseStack.mulPose(Axis.XP.rotationDegrees(30));
                poseStack.translate(-0.4f, 0.85f, 0.05);
            }
            else{
                poseStack.translate(-0.4f, 0.75f, 0.05);
            }
            poseStack.mulPose(Axis.ZP.rotationDegrees(90));
            poseStack.mulPose(Axis.XN.rotationDegrees(90));
            poseStack.scale(0.5f, 0.5f, 0.5f);
            state.submit(poseStack, nodeCollector, packedLight, OverlayTexture.NO_OVERLAY, renderState.outlineColor);
            poseStack.popPose();
        }
    }
}