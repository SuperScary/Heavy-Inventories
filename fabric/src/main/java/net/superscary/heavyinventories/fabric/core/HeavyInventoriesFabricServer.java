package net.superscary.heavyinventories.fabric.core;

import net.minecraft.world.level.Level;

public class HeavyInventoriesFabricServer extends HeavyInventoriesFabricBase {

    public HeavyInventoriesFabricServer() {
        super();
    }

    @Override
    public Level getClientLevel() {
        return null;
    }

}
