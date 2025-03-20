package net.idothehax.gregdustry.entity.model;
import net.idothehax.gregdustry.Gregdustry;
import net.idothehax.gregdustry.entity.WegEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WegModel extends GeoModel<WegEntity> {
    public WegModel() {
    }

    public ResourceLocation getAnimationResource(WegEntity entity) {
        return new ResourceLocation("the_diffy_experience", "animations/thing32.animation.json");
    }

    public ResourceLocation getModelResource(WegEntity entity) {
        return new ResourceLocation("the_diffy_experience", "geo/thing32.geo.json");
    }

    public ResourceLocation getTextureResource(WegEntity entity) {
        return new ResourceLocation("gregdustry", "textures/entities/" + entity.getTexture() + ".png");
    }
}
