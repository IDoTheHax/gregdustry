package net.idothehax.gregdustry.entity.model;
import net.idothehax.gregdustry.entity.BlegEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BlegModel extends GeoModel<BlegEntity> {
    public BlegModel() {
    }

    public ResourceLocation getAnimationResource(BlegEntity entity) {
        return new ResourceLocation("the_diffy_experience", "animations/thing32.animation.json");
    }

    public ResourceLocation getModelResource(BlegEntity entity) {
        return new ResourceLocation("the_diffy_experience", "geo/thing32.geo.json");
    }

    public ResourceLocation getTextureResource(BlegEntity entity) {
        return new ResourceLocation("gregdustry", "textures/entities/" + entity.getTexture() + ".png");
    }
}
