package net.superscary.heavyinventories.forge;

import net.minecraftforge.fml.common.Mod;
import net.superscary.heavyinventories.CommonClass;
import net.superscary.heavyinventories.HeavyInventories;

@Mod(HeavyInventories.MOD_ID)
public class ExampleMod {

    public ExampleMod() {

        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
        HeavyInventories.LOGGER.info("Hello Forge world!");
        CommonClass.init();

    }
}