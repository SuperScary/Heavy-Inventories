package net.superscary.heavyinventories.fabric.core;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class HeavyInventoriesFabricClient extends HeavyInventoriesFabricBase {

    public HeavyInventoriesFabricClient() {
        super();
    }

    @Override
    public Level getClientLevel() {
        return Minecraft.getInstance().level;
    }

}
