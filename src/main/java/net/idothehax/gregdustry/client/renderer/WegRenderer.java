package net.idothehax.gregdustry.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.idothehax.gregdustry.entity.WegEntity;
import net.idothehax.gregdustry.entity.model.WegModel;
import net.mcreator.thediffyexperience.entity.GregEntity;
import net.mcreator.thediffyexperience.entity.model.GregModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WegRenderer extends GeoEntityRenderer<WegEntity> {
    public WegRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WegModel());
        this.shadowRadius = 0.5F;
    }

    public RenderType getRenderType(WegEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    public void preRender(PoseStack poseStack, WegEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float scale = 1.0F;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
