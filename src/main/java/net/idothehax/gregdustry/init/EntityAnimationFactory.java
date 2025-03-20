package net.idothehax.gregdustry.init;

import net.idothehax.gregdustry.client.renderer.WegRenderer;
import net.idothehax.gregdustry.entity.WegEntity;
import net.mcreator.thediffyexperience.entity.BregEntity;
import net.mcreator.thediffyexperience.entity.BregStillEntity;
import net.mcreator.thediffyexperience.entity.CregEntity;
import net.mcreator.thediffyexperience.entity.FlyingGregEntity;
import net.mcreator.thediffyexperience.entity.FregEntity;
import net.mcreator.thediffyexperience.entity.GamblerTestEntity;
import net.mcreator.thediffyexperience.entity.GregEntity;
import net.mcreator.thediffyexperience.entity.JpegEntity;
import net.mcreator.thediffyexperience.entity.LegEntity;
import net.mcreator.thediffyexperience.entity.LgregEntity;
import net.mcreator.thediffyexperience.entity.PregBombEntity;
import net.mcreator.thediffyexperience.entity.PregEntity;
import net.mcreator.thediffyexperience.entity.RouleggEntity;
import net.mcreator.thediffyexperience.entity.TregEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class EntityAnimationFactory {
    public EntityAnimationFactory() {
    }

    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingTickEvent event) {
        if (event != null && event.getEntity() != null) {
            LivingEntity var2 = event.getEntity();
            String animation;
            if (var2 instanceof WegEntity) {
                WegEntity syncable = (WegEntity) var2;
                animation = syncable.getSyncedAnimation();
                if (!animation.equals("undefined")) {
                    syncable.setAnimation("undefined");
                    syncable.animationprocedure = animation;
                }
            }
        }
    }
}
