package net.superscary.heavyinventories.neoforge.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

public class HeavyInventoriesNeoForgeClient extends HeavyInventoriesNeoForgeBase {

    public HeavyInventoriesNeoForgeClient(ModContainer modContainer, IEventBus modEventBus) {
        super(modContainer, modEventBus);
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

}
