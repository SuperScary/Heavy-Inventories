package net.superscary.heavyinventories.api.config;

import net.superscary.heavyinventories.config.ConfigScreenOpener;
import net.superscary.heavyinventories.platform.Services;

import java.util.concurrent.atomic.AtomicReference;

public final class ConfigScreens {

    private static final AtomicReference<ConfigScreenOpener> OPENER = new AtomicReference<>();

    private ConfigScreens() {
    }

    /**
     * @deprecated Use {@link #openClientConfig()}, {@link #openServerConfig()}, or {@link #openCommonConfig()} instead.
     */
    @Deprecated
    public static void register(ConfigScreenOpener opener) {
        OPENER.set(opener);
    }

    /**
     * @deprecated Use {@link #openClientConfig()}, {@link #openServerConfig()}, or {@link #openCommonConfig()} instead.
     */
    @Deprecated
    public static boolean open() {
        var o = OPENER.get();
        if (o == ConfigScreenOpener.NO_OP) return false;
        o.openConfigScreen();
        return true;
    }

    /**
     * Opens the client config screen.
     * Should only be called from the client side.
     */
    public static void openClientConfig() {
        if (Services.CONFIG_SCREEN.isClientSide()) {
            Services.CONFIG_SCREEN.openClientConfig();
        }
    }

    /**
     * Opens the server config screen.
     * Should only be called from the client side.
     */
    public static void openServerConfig() {
        if (Services.CONFIG_SCREEN.isClientSide()) {
            Services.CONFIG_SCREEN.openServerConfig();
        }
    }

    /**
     * Opens the common config screen.
     * Should only be called from the client side.
     */
    public static void openCommonConfig() {
        if (Services.CONFIG_SCREEN.isClientSide()) {
            Services.CONFIG_SCREEN.openCommonConfig();
        }
    }
}
