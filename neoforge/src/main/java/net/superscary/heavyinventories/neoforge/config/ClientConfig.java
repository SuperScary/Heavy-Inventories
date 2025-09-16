package net.superscary.heavyinventories.neoforge.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.EnumValue<Measure> WEIGHT_MEASURE = BUILDER
            .comment("The weight measurement system to use. This is purely cosmetic.")
            .defineEnum("weightMeasure", Measure.LBS);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public Measure weightMeasure = Measure.LBS;

    public enum Measure {
        KGS,
        LBS,
        NONE;

        @Override
        public String toString() {
            if (this == NONE) return "";
            return name().toLowerCase();
        }
    }

}
