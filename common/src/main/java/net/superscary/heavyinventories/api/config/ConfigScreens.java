package net.superscary.heavyinventories.api.config;

import net.superscary.heavyinventories.config.ConfigScreenOpener;

import java.util.concurrent.atomic.AtomicReference;

public final class ConfigScreens {

    private static final AtomicReference<ConfigScreenOpener> OPENER = new AtomicReference<>();

    private ConfigScreens() {
    }

    public static void register(ConfigScreenOpener opener) {
        OPENER.set(opener);
    }

    public static boolean open() {
        var o = OPENER.get();
        if (o == ConfigScreenOpener.NO_OP) return false;
        o.openConfigScreen();
        return true;
    }

}
