package net.superscary.heavyinventories.fabric.core;

import net.minecraft.world.level.Level;
import net.superscary.heavyinventories.config.ConfigFileManager;

public class HeavyInventoriesFabricServer extends HeavyInventoriesFabricBase {

    public HeavyInventoriesFabricServer() {
        super();

        ConfigFileManager.loadServerConfig();
    }

    @Override
    public Level getClientLevel() {
        return null;
    }

}
