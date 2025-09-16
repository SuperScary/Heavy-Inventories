package net.superscary.heavyinventories.forge.core;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.superscary.heavyinventories.CommonClass;
import net.superscary.heavyinventories.ModBase;
import net.superscary.heavyinventories.forge.hooks.ModHooks;
import org.jetbrains.annotations.Nullable;

public abstract class HeavyInventoriesForgeBase extends ModBase {

    public HeavyInventoriesForgeBase(ModLoadingContext modLoadingContext) {
        super();
        CommonClass.init();

        MinecraftForge.EVENT_BUS.addListener(ModHooks::hookPlayer);
        MinecraftForge.EVENT_BUS.addListener(ModHooks::hookPlayerClone);
        MinecraftForge.EVENT_BUS.addListener(ModHooks::hookPlayerChangedDimension);
        MinecraftForge.EVENT_BUS.addListener(ModHooks::hookPlayerPickupItem);
        MinecraftForge.EVENT_BUS.addListener(ModHooks::hookOnCraft);
        MinecraftForge.EVENT_BUS.addListener(ModHooks::hookOnSmelt);
        MinecraftForge.EVENT_BUS.addListener(ModHooks::hookPlayerMove);

        MinecraftForge.EVENT_BUS.addListener(ModHooks::hookCommands);
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

}
