package net.superscary.heavyinventories.neoforge.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.superscary.heavyinventories.neoforge.hooks.TooltipHooks;

public class HeavyInventoriesNeoForgeClient extends HeavyInventoriesNeoForgeBase {

    public HeavyInventoriesNeoForgeClient(ModContainer modContainer, IEventBus modEventBus) {
        super(modContainer, modEventBus);

        NeoForge.EVENT_BUS.addListener(TooltipHooks::hookTooltip);
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

}
