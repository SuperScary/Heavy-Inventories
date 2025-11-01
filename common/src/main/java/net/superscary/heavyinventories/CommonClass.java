package net.superscary.heavyinventories;

import net.superscary.heavyinventories.api.player.PlayerHolder;
import net.superscary.heavyinventories.config.ConfigOptions;

public class CommonClass {

    public static void init() {
        PlayerHolder.setWeightStarting(ConfigOptions.PLAYER_STARTING_WEIGHT);
    }

}