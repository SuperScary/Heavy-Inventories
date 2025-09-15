package net.superscary.heavyinventories.neoforge.core;

import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.superscary.heavyinventories.CommonClass;
import net.superscary.heavyinventories.ModBase;
import net.superscary.heavyinventories.neoforge.hooks.ModHooks;
import org.jetbrains.annotations.Nullable;

public abstract class HeavyInventoriesNeoForgeBase extends ModBase {

    public HeavyInventoriesNeoForgeBase(ModContainer modContainer, IEventBus modEventBus) {
        super();
        CommonClass.init();

        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayer);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayerMove);
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

}
