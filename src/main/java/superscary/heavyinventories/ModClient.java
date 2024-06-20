package superscary.heavyinventories;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.Nullable;
import superscary.heavyinventories.config.ClientConfig;
import superscary.heavyinventories.config.CommonConfig;

public class ModClient extends ModBase {

    static ModClient INSTANCE;

    public ModClient (IEventBus modEventBus, ModContainer modContainer) {
        super(modEventBus, modContainer);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(ClientConfig::onLoad);
        modEventBus.addListener(this::commonSetup);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }

    @SubscribeEvent
    public void commonSetup (FMLCommonSetupEvent event) {
        getModContainer().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
    }

    @SubscribeEvent
    public void clientSetup (FMLClientSetupEvent event) {

    }

    public static ModClient getInstance () {
        return INSTANCE;
    }

    @Override
    public @Nullable Level getClientLevel () {
        return Minecraft.getInstance().level;
    }

}
