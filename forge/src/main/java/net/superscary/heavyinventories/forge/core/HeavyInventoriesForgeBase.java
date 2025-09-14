package net.superscary.heavyinventories.forge.core;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.superscary.heavyinventories.CommonClass;
import net.superscary.heavyinventories.ModBase;
import org.jetbrains.annotations.Nullable;

public abstract class HeavyInventoriesForgeBase extends ModBase {

    public HeavyInventoriesForgeBase(ModLoadingContext modLoadingContext) {
        super();
        CommonClass.init();
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

}
