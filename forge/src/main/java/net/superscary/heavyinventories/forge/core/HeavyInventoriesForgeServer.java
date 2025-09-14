package net.superscary.heavyinventories.forge.core;

import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModLoadingContext;

public class HeavyInventoriesForgeServer extends HeavyInventoriesForgeBase {

    public HeavyInventoriesForgeServer(ModLoadingContext modLoadingContext) {
        super(modLoadingContext);
    }

    @Override
    public Level getClientLevel() {
        return null;
    }

}
