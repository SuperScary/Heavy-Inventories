package net.superscary.heavyinventories.neoforge.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue STARTING_WEIGHT = BUILDER
            .comment("The starting weight each player is capable of holding.")
            .defineInRange("startingWeight", 1000d, 0d, Double.MAX_VALUE);

    public static final ModConfigSpec SPEC = BUILDER.build();

}
