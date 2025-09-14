package net.superscary.heavyinventories.forge.core;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.superscary.heavyinventories.HeavyInventories;

@Mod(HeavyInventories.MOD_ID)
public class HeavyInventoriesForgeBootstrap {

    public HeavyInventoriesForgeBootstrap(ModLoadingContext modLoadingContext) {
        switch (FMLEnvironment.dist) {
            case CLIENT -> new HeavyInventoriesForgeClient(modLoadingContext);
            case DEDICATED_SERVER -> new HeavyInventoriesForgeServer(modLoadingContext);
        }
    }

}
