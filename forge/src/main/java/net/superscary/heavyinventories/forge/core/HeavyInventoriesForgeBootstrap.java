package net.superscary.heavyinventories.forge.core;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.superscary.heavyinventories.HeavyInventories;

@Mod(HeavyInventories.MOD_ID)
public class HeavyInventoriesForgeBootstrap {

    public HeavyInventoriesForgeBootstrap() {
        @SuppressWarnings("deprecation")
        var modLoadingContext = ModLoadingContext.get(); // just to satisfy the 1.21.1 no-arg constructor. pass as an arg in the constructor for later versions.

        switch (FMLEnvironment.dist) {
            case CLIENT -> new HeavyInventoriesForgeClient(modLoadingContext);
            case DEDICATED_SERVER -> new HeavyInventoriesForgeServer(modLoadingContext);
        }
    }

}
