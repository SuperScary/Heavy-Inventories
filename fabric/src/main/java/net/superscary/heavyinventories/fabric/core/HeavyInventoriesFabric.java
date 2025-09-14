package net.superscary.heavyinventories.fabric.core;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class HeavyInventoriesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            new HeavyInventoriesFabricClient();
        } else {
            new HeavyInventoriesFabricServer();
        }
    }

}
