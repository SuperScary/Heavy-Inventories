package superscary.heavyinventories;

import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import superscary.heavyinventories.config.ServerConfig;

public class ModServer extends ModBase {

    static Logger logger = LoggerFactory.getLogger(ModServer.class);

    public ModServer (IEventBus modEventBus, ModContainer modContainer) {
        super(modEventBus, modContainer);
        modEventBus.addListener(ServerConfig::onLoad);
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }

    @Override
    public @Nullable Level getClientLevel () {
        return null;
    }

}
