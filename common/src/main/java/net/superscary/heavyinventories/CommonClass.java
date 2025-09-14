package net.superscary.heavyinventories;

import net.superscary.heavyinventories.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

public class CommonClass {

    public static void init() {

        HeavyInventories.LOGGER.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        HeavyInventories.LOGGER.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

        if (Services.PLATFORM.isModLoaded(HeavyInventories.MOD_ID)) {
            HeavyInventories.LOGGER.info("Hello to {}", HeavyInventories.MOD_NAME);
        }
    }

}