package net.superscary.heavyinventories.neoforge.core;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.superscary.heavyinventories.HeavyInventories;

@Mod(HeavyInventories.MOD_ID)
public class HeavyInventoriesNeoForgeBootstrap {

    public HeavyInventoriesNeoForgeBootstrap(ModContainer modContainer, IEventBus modEventBus) {
        switch (FMLEnvironment.dist) {
            case CLIENT -> new HeavyInventoriesNeoForgeClient(modContainer, modEventBus);
            case DEDICATED_SERVER -> new HeavyInventoriesNeoForgeServer(modContainer, modEventBus);
        }
    }

}
