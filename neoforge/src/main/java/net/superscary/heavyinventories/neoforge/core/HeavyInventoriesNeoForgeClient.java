package net.superscary.heavyinventories.neoforge.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.superscary.heavyinventories.config.ConfigFileManager;
import net.superscary.heavyinventories.neoforge.hooks.ModHooks;

public class HeavyInventoriesNeoForgeClient extends HeavyInventoriesNeoForgeBase {

    public HeavyInventoriesNeoForgeClient(ModContainer modContainer, IEventBus modEventBus) {
        super(modContainer, modEventBus);

        ConfigFileManager.loadClientConfig();

        NeoForge.EVENT_BUS.addListener(ModHooks::hookTooltip);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookGui);
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

}
