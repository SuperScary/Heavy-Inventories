package net.superscary.heavyinventories.fabric.core;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.superscary.heavyinventories.CommonClass;
import net.superscary.heavyinventories.ModBase;
import net.superscary.heavyinventories.fabric.hooks.ModHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class HeavyInventoriesFabricBase extends ModBase {

    private volatile MinecraftServer currentServer;

    public HeavyInventoriesFabricBase() {
        super();
        CommonClass.init();

        ServerLifecycleEvents.SERVER_STARTED.register(server -> currentServer = server);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> currentServer = null);

        ModHooks.registerHooks();
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer() {
        return currentServer;
    }

    @Override
    public List<String> getModIds() {
        return FabricLoader.getInstance().getAllMods().stream().map(mod -> mod.getMetadata().getId()).toList();
    }

}
