package net.superscary.heavyinventories.fabric.platform;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.superscary.heavyinventories.HeavyInventories;
import net.superscary.heavyinventories.api.config.ConfigScreens;
import net.superscary.heavyinventories.fabric.config.ModClientConfig;
import net.superscary.heavyinventories.fabric.config.ModCommonConfig;
import net.superscary.heavyinventories.fabric.config.ModServerConfig;
import net.superscary.heavyinventories.platform.services.IConfigScreenHelper;

public class FabricConfigScreenHelper implements IConfigScreenHelper {

    public static final ResourceLocation OPEN_CONFIG_PACKET_ID = ResourceLocation.fromNamespaceAndPath(HeavyInventories.MOD_ID, "open_config");
    
    public static final StreamCodec<FriendlyByteBuf, OpenConfigPacket> STREAM_CODEC = StreamCodec.of(
        (buf, packet) -> buf.writeUtf(packet.configType()),
        buf -> new OpenConfigPacket(buf.readUtf())
    );

    public static final CustomPacketPayload.Type<OpenConfigPacket> OPEN_CONFIG_PACKET_TYPE = 
        new CustomPacketPayload.Type<>(OPEN_CONFIG_PACKET_ID);

    public record OpenConfigPacket(String configType) implements CustomPacketPayload {
        @Override
        public Type<? extends CustomPacketPayload> type() {
            return OPEN_CONFIG_PACKET_TYPE;
        }
    }

    private static boolean payloadRegistered = false;
    private static boolean handlerRegistered = false;

    /**
     * Call this during mod initialization to register the payload type.
     * This must be called from HeavyInventoriesFabricBase during mod initialization,
     * before any network communication happens.
     */
    public static void registerPayloadType() {
        if (!payloadRegistered) {
            PayloadTypeRegistry.playS2C().register(OPEN_CONFIG_PACKET_TYPE, STREAM_CODEC);
            payloadRegistered = true;
            
            // If on client, also set up handler registration
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                ensureHandlerRegistered();
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private static void ensureHandlerRegistered() {
        if (handlerRegistered) return;
        
        // Register handler when client connects to a server
        ClientPlayConnectionEvents.INIT.register((handler, client) -> {
            if (!handlerRegistered && payloadRegistered) {
                ClientPlayNetworking.registerGlobalReceiver(
                    OPEN_CONFIG_PACKET_TYPE,
                    (packet, context) -> {
                        context.client().execute(() -> {
                            switch (packet.configType()) {
                                case "client" -> ConfigScreens.openClientConfig();
                                case "server" -> ConfigScreens.openServerConfig();
                                case "common" -> ConfigScreens.openCommonConfig();
                            }
                        });
                    }
                );
                handlerRegistered = true;
            }
        });
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
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public void sendOpenConfigPacket(Object playerId, String configType) {
        if (playerId instanceof ServerPlayer player) {
            OpenConfigPacket packet = new OpenConfigPacket(configType);
            ServerPlayNetworking.send(player, packet);
        }
    }
}
