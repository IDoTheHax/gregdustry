package net.idothehax.gregdustry.init;

import net.idothehax.gregdustry.client.renderer.BlegRenderer;
import net.idothehax.gregdustry.client.renderer.WegRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = {Dist.CLIENT}
)
public class GregdustryModEntityRenderers {
    public GregdustryModEntityRenderers() {
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer((EntityType) GregdustryModEntities.WEG.get(), WegRenderer::new);
        event.registerEntityRenderer((EntityType) GregdustryModEntities.BLEG.get(), BlegRenderer::new);

    }
}

