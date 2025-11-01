package net.superscary.heavyinventories.platform.services;

/**
 * Platform abstraction for opening config screens.
 * Each platform (Fabric, Forge, NeoForge) should implement this interface.
 */
public interface IConfigScreenHelper {

    /**
     * Opens the client config screen on the client side.
     * This should only be called from the client.
     */
    void openClientConfig();

    /**
     * Opens the server config screen on the client side.
     * This should only be called from the client.
     */
    void openServerConfig();

    /**
     * Opens the common config screen on the client side.
     * This should only be called from the client.
     */
    void openCommonConfig();

    /**
     * Checks if the current side is the client.
     * @return true if on client side, false otherwise
     */
    boolean isClientSide();

    /**
     * Sends a packet to the client to open the specified config screen.
     * @param playerId the player to send the packet to
     * @param configType the type of config to open ("client", "server", or "common")
     */
    void sendOpenConfigPacket(Object playerId, String configType);

    /**
     * A no-op implementation for when the platform doesn't support config screens.
     */
    IConfigScreenHelper NO_OP = new IConfigScreenHelper() {
        @Override
        public void openClientConfig() {}

        @Override
        public void openServerConfig() {}

        @Override
        public void openCommonConfig() {}

        @Override
        public boolean isClientSide() {
            return false;
        }

        @Override
        public void sendOpenConfigPacket(Object playerId, String configType) {}
    };
}

