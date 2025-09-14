package net.superscary.heavyinventories.neoforge.core;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public class HeavyInventoriesNeoForgeServer extends HeavyInventoriesNeoForgeBase {

    public HeavyInventoriesNeoForgeServer(ModContainer modContainer, IEventBus modEventBus) {
        super(modContainer, modEventBus);
    }

    @Override
    public Level getClientLevel() {
        return null;
    }

}
