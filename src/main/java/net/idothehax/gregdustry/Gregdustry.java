package net.idothehax.gregdustry;

import net.idothehax.gregdustry.init.GregdustryModEntities;
import net.idothehax.gregdustry.init.GregdustryModItems;
import net.mcreator.thediffyexperience.init.TheDiffyExperienceModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("gregdustry")
public class Gregdustry {
    public static final Logger LOGGER = LogManager.getLogger(Gregdustry.class);
    public static final String MODID = "gregdustry";

    public Gregdustry() {
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        GregdustryModEntities.REGISTRY.register(bus);
        GregdustryModItems.REGISTRY.register(bus);
    }
}
