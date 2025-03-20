package net.idothehax.gregdustry.init;

import net.idothehax.gregdustry.Gregdustry;
import net.mcreator.thediffyexperience.init.TheDiffyExperienceModEntities;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GregdustryModItems {
    public static final DeferredRegister<Item> REGISTRY;
    public static final RegistryObject<Item> WEG_SPAWN_EGG;

    public GregdustryModItems() {
    }

    private static RegistryObject<Item> block(RegistryObject<Block> block) {
        return REGISTRY.register(block.getId().getPath(), () -> {
            return new BlockItem((Block)block.get(), new Item.Properties());
        });
    }

    static {
        REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Gregdustry.MODID);
        WEG_SPAWN_EGG = REGISTRY.register("weg_spawn_egg", () -> {
            return new ForgeSpawnEggItem(GregdustryModEntities.WEG, -0xffffff, -0x7f7f7f, new Item.Properties());
        });
    }
}
