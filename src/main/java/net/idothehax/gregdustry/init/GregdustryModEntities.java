package net.idothehax.gregdustry.init;

import net.idothehax.gregdustry.Gregdustry;
import net.idothehax.gregdustry.entity.WegEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class GregdustryModEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY;
    public static final RegistryObject<EntityType<WegEntity>> WEG;

    public GregdustryModEntities() {

    }

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
        return REGISTRY.register(registryname, () -> {
            return entityTypeBuilder.build(registryname);
        });
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            WegEntity.init();

        });
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(WEG.get(), WegEntity.createAttributes().build());
    }

    static {
        REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Gregdustry.MODID);
        WEG = register("weg", EntityType.Builder.of(WegEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(256).setUpdateInterval(3).canSpawnFarFromPlayer().sized(0.6F, 1.8F));
    }
}
