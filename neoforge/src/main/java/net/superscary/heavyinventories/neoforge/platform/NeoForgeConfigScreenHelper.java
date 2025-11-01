package net.superscary.heavyinventories.neoforge.platform;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.superscary.heavyinventories.HeavyInventories;
import net.superscary.heavyinventories.api.config.ConfigScreens;
import net.superscary.heavyinventories.neoforge.config.ModClientConfig;
import net.superscary.heavyinventories.neoforge.config.ModCommonConfig;
import net.superscary.heavyinventories.neoforge.config.ModServerConfig;
import net.superscary.heavyinventories.platform.services.IConfigScreenHelper;

public class NeoForgeConfigScreenHelper implements IConfigScreenHelper {

    public static final ResourceLocation OPEN_CONFIG_PACKET_ID = ResourceLocation.fromNamespaceAndPath(HeavyInventories.MOD_ID, "open_config");
    
    public static final StreamCodec<FriendlyByteBuf, OpenConfigPacket> STREAM_CODEC = StreamCodec.of(
        (buf, packet) -> buf.writeUtf(packet.configType()),
        buf -> new OpenConfigPacket(buf.readUtf())
    );

    public record OpenConfigPacket(String configType) implements CustomPacketPayload {
        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
        
        public static final Type<OpenConfigPacket> TYPE = new Type<>(OPEN_CONFIG_PACKET_ID);
    }

    /**
     * Registers the payload type during mod initialization.
     * This event handler is registered statically.
     */
    @EventBusSubscriber(modid = HeavyInventories.MOD_ID)
    public static class NetworkHandler {
        @SubscribeEvent
        public static void registerPayload(RegisterPayloadHandlersEvent event) {
            PayloadRegistrar registrar = event.registrar(HeavyInventories.MOD_ID);
            
            // Register server-to-client payload
            registrar.playToClient(OpenConfigPacket.TYPE, STREAM_CODEC, 
                (packet, context) -> {
                    context.enqueueWork(() -> {
                        switch (packet.configType()) {
                            case "client" -> ConfigScreens.openClientConfig();
                            case "server" -> ConfigScreens.openServerConfig();
                            case "common" -> ConfigScreens.openCommonConfig();
                        }
                    });
                });
        }
    }

    @Override
    public void openClientConfig() {
        if (isClientSide()) {
            ModClientConfig.init();
            Minecraft.getInstance().setScreen(
                ModClientConfig.getBuilder().build()
            );
        }
    }

    @Override
    public void openServerConfig() {
        if (isClientSide()) {
            ModServerConfig.init();
            Minecraft.getInstance().setScreen(
                ModServerConfig.getBuilder().build()
            );
        }
    }

    @Override
    public void openCommonConfig() {
        if (isClientSide()) {
            ModCommonConfig.init();
            Minecraft.getInstance().setScreen(
                ModCommonConfig.getBuilder().build()
            );
        }
    }

    @Override
    public boolean isClientSide() {
        return FMLEnvironment.dist == Dist.CLIENT;
    }

    @Override
    public void sendOpenConfigPacket(Object playerId, String configType) {
        if (playerId instanceof ServerPlayer player) {
            OpenConfigPacket packet = new OpenConfigPacket(configType);
            player.connection.send(packet);
        }
    }
}
