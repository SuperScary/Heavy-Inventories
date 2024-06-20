package superscary.heavyinventories;

import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;
import superscary.heavyinventories.codec.ModCodecs;
import superscary.heavyinventories.config.CommonConfig;
import superscary.heavyinventories.data.player.ModDataAttachments;
import superscary.heavyinventories.data.util.DisallowedMods;
import superscary.heavyinventories.data.util.ModBuilder;

public abstract class ModBase implements HeavyInventories {

    static ModBase INSTANCE;
    private final ModContainer modContainer;

    public ModBase (IEventBus modEventBus, ModContainer modContainer) {
        if (INSTANCE != null) {
            throw new IllegalStateException();
        }
        INSTANCE = this;
        this.modContainer = modContainer;

        modEventBus.addListener(CommonConfig::onLoad);
        ModCodecs.REGISTRY.register(modEventBus);
        ModDataAttachments.TYPES.register(modEventBus);
        modEventBus.addListener(this::postLoad);

        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
    }

    @SubscribeEvent
    public void postLoad (FMLLoadCompleteEvent event) {
        DisallowedMods.build();
        ModBuilder.build();
    }

    public ModContainer getModContainer () {
        return modContainer;
    }

    public static ModBase getInstance () {
        return INSTANCE;
    }

    @Nullable
    @Override
    public MinecraftServer getCurrentServer () {
        return ServerLifecycleHooks.getCurrentServer();
    }

}
