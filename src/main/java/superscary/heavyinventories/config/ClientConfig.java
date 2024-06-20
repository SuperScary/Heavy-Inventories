package superscary.heavyinventories.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import superscary.heavyinventories.render.style.EnumBarRenderSide;
import superscary.heavyinventories.render.style.EnumBarRenderStyle;
import superscary.heavyinventories.render.style.EnumColor;

public class ClientConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.EnumValue<EnumBarRenderSide> BAR_RENDER_ENUM_VALUE = BUILDER
            .comment("Dictates which side of the screen the encumbrance bar should render on.")
            .defineEnum("WeightBarRenderSide", EnumBarRenderSide.LEFT);
    public static final ModConfigSpec.EnumValue<EnumBarRenderStyle> BAR_RENDER_STYLE_ENUM_VALUE = BUILDER
            .comment("Dictates the style of bar that should be rendered.")
            .defineEnum("WeightBarRenderStyle", EnumBarRenderStyle.VERTICAL);
    public static final ModConfigSpec.EnumValue<EnumColor> NOT_ENCUMBERED_BAR_COLOR = BUILDER
            .comment("Dictates the color of the weight bar when the player is not encumbered.")
            .defineEnum("WeightBarNotEncumberedColor", EnumColor.GREEN);
    public static final ModConfigSpec.EnumValue<EnumColor> ENCUMBERED_BAR_COLOR = BUILDER
            .comment("Dictates the color of the weight bar when the player is encumbered.")
            .defineEnum("WeightBarEncumberedColor", EnumColor.YELLOW);
    public static final ModConfigSpec.EnumValue<EnumColor> OVER_ENCUMBERED_BAR_COLOR = BUILDER
            .comment("Dictates the color of the weight bar when the player is over encumbered.")
            .defineEnum("WeightBarOverEncumberedColor", EnumColor.RED);
    public static final ModConfigSpec.BooleanValue SHOULD_SHOW_TEXT = BUILDER
            .comment("Dictates if the player weight text should be shown.")
            .define("ShowWeightText", true);
    public static final ModConfigSpec.BooleanValue SHOULD_SHOW_BAR = BUILDER
            .comment("Dictates if the weight bar should render.")
            .define("ShowWeightBar", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static EnumBarRenderSide enumBarRenderSide;
    public static EnumBarRenderStyle enumBarRenderStyle;
    public static EnumColor enumBarNotEncumbered;
    public static EnumColor enumBarEncumbered;
    public static EnumColor enumBarOverEncumbered;
    public static boolean showWeightText;
    public static boolean showWeightBar;

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onLoad (final ModConfigEvent event) {
        enumBarRenderSide = BAR_RENDER_ENUM_VALUE.get();
        enumBarRenderStyle = BAR_RENDER_STYLE_ENUM_VALUE.get();
        enumBarNotEncumbered = NOT_ENCUMBERED_BAR_COLOR.get();
        enumBarEncumbered = ENCUMBERED_BAR_COLOR.get();
        enumBarOverEncumbered = OVER_ENCUMBERED_BAR_COLOR.get();
        showWeightText = SHOULD_SHOW_TEXT.get();
    }

}
