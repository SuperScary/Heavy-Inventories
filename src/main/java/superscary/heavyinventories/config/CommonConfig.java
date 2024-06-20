package superscary.heavyinventories.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import superscary.heavyinventories.HeavyInventories;

public class CommonConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue PLAYER_STARTING_CARRYING_WEIGHT = BUILDER
            .comment("The starting max carrying weight of the player.")
            .defineInRange("StartingWeight", 1200d, 0d, 100000d);
    private static final ModConfigSpec.DoubleValue ENCUMBERED_PERCENTAGE = BUILDER
            .comment("The percentage value of when a player should be set as encumbered.")
            .defineInRange("EncumberedPercentage", 0.85d, 0d, 1.d);
    private static final ModConfigSpec.DoubleValue MINIMUM_ITEM_WEIGHT = BUILDER
            .comment("Minimum item weight.")
            .comment("NOTE: This value is used while generating item weights.")
            .defineInRange("MinimumItemWeight", 0.1d, 0.1d, 1000000);
    private static final ModConfigSpec.DoubleValue PLAYER_MOVEMENT_FACTOR_ENCUMBERED = BUILDER
            .comment("The speed the player can move at while encumbered.")
            .defineInRange("EncumberedMovementFactor", 0.5d, 0.d, 1.d);
    private static final ModConfigSpec.DoubleValue PLAYER_MOVEMENT_FACTOR_OVER_ENCUMBERED = BUILDER
            .comment("The speed the player can move at while over encumbered.")
            .defineInRange("OverEncumberedMovementFactor", 0.0d, 0.d, 1.d);
    private static final ModConfigSpec.BooleanValue ENCUMBERED_CAN_JUMP = BUILDER
            .comment("Whether the player can jump while encumbered.")
            .define("EncumberedJumpEnable", true);
    private static final ModConfigSpec.BooleanValue OVER_ENCUMBERED_CAN_JUMP = BUILDER
            .comment("Whether the player can jump while over encumbered.")
            .define("OverEncumberedJumpEnable", false);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static double playerStartingWeight;
    public static double encumberedPercentage;
    public static double minimumItemWeight;
    public static double encumberedMovementFactor;
    public static double overEncumberedMovementFactor;
    public static boolean encumberedCanJump;
    public static boolean overEncumberedCanJump;

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onLoad (final ModConfigEvent event) {
        playerStartingWeight = PLAYER_STARTING_CARRYING_WEIGHT.get();
        encumberedPercentage = ENCUMBERED_PERCENTAGE.get();
        minimumItemWeight = MINIMUM_ITEM_WEIGHT.get();
        encumberedMovementFactor = PLAYER_MOVEMENT_FACTOR_ENCUMBERED.get();
        overEncumberedMovementFactor = PLAYER_MOVEMENT_FACTOR_OVER_ENCUMBERED.get();
        encumberedCanJump = ENCUMBERED_CAN_JUMP.get();
        overEncumberedCanJump = OVER_ENCUMBERED_CAN_JUMP.get();
    }

}
