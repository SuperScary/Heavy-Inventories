package net.superscary.heavyinventories.neoforge.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.superscary.heavyinventories.api.util.Measure;

public class ClientConfig {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.EnumValue<Measure> WEIGHT_MEASURE = BUILDER
            .comment("The weight measurement system to use. This is purely cosmetic.")
            .defineEnum("weightMeasure", Measure.LBS);

    public static final ModConfigSpec.BooleanValue SHOW_WEIGHT_ON_SCREEN = BUILDER
            .comment("Whether or not to show the player's weight on their screen.")
            .define("showWeightOnScreen", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

}
