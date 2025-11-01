package net.superscary.heavyinventories.neoforge.core;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.superscary.heavyinventories.config.ConfigFileManager;

public class HeavyInventoriesNeoForgeServer extends HeavyInventoriesNeoForgeBase {

    public HeavyInventoriesNeoForgeServer(ModContainer modContainer, IEventBus modEventBus) {
        super(modContainer, modEventBus);

        ConfigFileManager.loadServerConfig();
    }

    @Override
    public Level getClientLevel() {
        return null;
    }

}
