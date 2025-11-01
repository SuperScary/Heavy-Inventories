package net.superscary.heavyinventories.neoforge.core;

import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.neoforged.neoforgespi.language.IModInfo;
import net.superscary.heavyinventories.CommonClass;
import net.superscary.heavyinventories.ModBase;
import net.superscary.heavyinventories.api.player.PlayerHolder;
import net.superscary.heavyinventories.config.ConfigOptions;
import net.superscary.heavyinventories.neoforge.enchantments.ModEnchantmentEffects;
import net.superscary.heavyinventories.neoforge.hooks.ModHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class HeavyInventoriesNeoForgeBase extends ModBase {

    public HeavyInventoriesNeoForgeBase(ModContainer modContainer, IEventBus modEventBus) {
        super();
        CommonClass.init();

        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayer);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayerClone);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayerChangedDimension);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayerPickupItem);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookOnCraft);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookOnSmelt);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayerMove);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayerEquip);
        NeoForge.EVENT_BUS.addListener(ModHooks::hookPlayerLogout);

        NeoForge.EVENT_BUS.addListener(ModHooks::hookCommands);

        modEventBus.addListener(HeavyInventoriesNeoForgeBase::postInitialization);

        ModEnchantmentEffects.register(modEventBus);

    }

    private static void postInitialization(FMLLoadCompleteEvent event) {
        PlayerHolder.setWeightStarting(ConfigOptions.PLAYER_STARTING_WEIGHT);
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    @Override
    public List<String> getModIds() {
        return ModList.get().getMods().stream().map(IModInfo::getModId).toList();
    }

}
