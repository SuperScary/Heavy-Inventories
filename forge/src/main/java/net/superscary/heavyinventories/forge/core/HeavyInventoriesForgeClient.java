package net.superscary.heavyinventories.forge.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModLoadingContext;

public class HeavyInventoriesForgeClient extends HeavyInventoriesForgeBase {

    public HeavyInventoriesForgeClient(ModLoadingContext modLoadingContext) {
        super(modLoadingContext);
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

}
