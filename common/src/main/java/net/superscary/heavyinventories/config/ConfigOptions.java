package net.superscary.heavyinventories.config;

import net.superscary.heavyinventories.api.util.MeasuringSystem;

public class ConfigOptions {

    // Client Options
    public static MeasuringSystem WEIGHT_MEASURE = MeasuringSystem.LBS;
    public static boolean ENABLE_GUI_OVERLAY = true;
    public static int NORMAL_TEXT_COLOR = 0xFFFFFF;
    public static int ENCUMBERED_TEXT_COLOR = 0xFFFF55;
    public static int OVER_ENCUMBERED_TEXT_COLOR = 0xFF5555;

    // Server Options
    public static float PLAYER_STARTING_WEIGHT = 1000f;

}
