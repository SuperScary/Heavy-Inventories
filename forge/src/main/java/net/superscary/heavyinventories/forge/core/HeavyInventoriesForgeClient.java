package net.superscary.heavyinventories.forge.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.superscary.heavyinventories.forge.hooks.ModHooks;

public class HeavyInventoriesForgeClient extends HeavyInventoriesForgeBase {

    public HeavyInventoriesForgeClient(ModLoadingContext modLoadingContext) {
        super(modLoadingContext);

        MinecraftForge.EVENT_BUS.addListener(ModHooks::hookTooltip);
        MinecraftForge.EVENT_BUS.addListener(ModHooks::hookGui);
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

}
