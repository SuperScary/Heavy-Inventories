package superscary.heavyinventories;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@SuppressWarnings("unused")
@Mod(HeavyInventories.MODID)
public class ModBootstrap {

    public ModBootstrap (IEventBus modEventBus, ModContainer modContainer) {
        switch (FMLEnvironment.dist) {
            case CLIENT -> new ModClient(modEventBus, modContainer);
            case DEDICATED_SERVER -> new ModServer(modEventBus, modContainer);
        }
    }

}
