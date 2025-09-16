package net.superscary.heavyinventories.neoforge.core;

import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.superscary.heavyinventories.CommonClass;
import net.superscary.heavyinventories.ModBase;
import net.superscary.heavyinventories.api.player.PlayerHolder;
import net.superscary.heavyinventories.neoforge.config.ServerConfig;
import net.superscary.heavyinventories.neoforge.enchantments.ModEnchantmentEffects;
import net.superscary.heavyinventories.neoforge.hooks.ModHooks;
import org.jetbrains.annotations.Nullable;

public abstract class HeavyInventoriesNeoForgeBase extends ModBase {

    public HeavyInventoriesNeoForgeBase(ModContainer modContainer, IEventBus modEventBus) {
        super();
        CommonClass.init();

        modContainer.registerConfig(ModConfig.Type.COMMON, ServerConfig.SPEC);

        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayer);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayerClone);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayerChangedDimension);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayerPickupItem);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookOnCraft);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookOnSmelt);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayerMove);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayerEquip);

        NeoForge.EVENT_BUS.addListener(ModHooks::hookCommands);

        modEventBus.addListener(HeavyInventoriesNeoForgeBase::postInitialization);

        ModEnchantmentEffects.register(modEventBus);
    }

    private static void postInitialization(FMLLoadCompleteEvent event) {
        PlayerHolder.setWeightStarting(ServerConfig.STARTING_WEIGHT.get().floatValue());
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

}
