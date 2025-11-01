package net.superscary.heavyinventories.neoforge.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.superscary.heavyinventories.api.util.MeasuringSystem;
import net.superscary.heavyinventories.config.ConfigFileManager;
import net.superscary.heavyinventories.config.ConfigOptions;
import net.superscary.heavyinventories.config.ConfigScreenOpener;

public class ModClientConfig implements ConfigScreenOpener {

    private static ConfigBuilder builder;
    private static boolean entriesInitialized = false;

    public static void init() {
        if (builder == null) {
            builder = ConfigBuilder.create().setParentScreen(null).setTitle(Component.translatable("title.heavyinventories.config.client"));
        }

        builder.setSavingRunnable(ModClientConfig::saveConfig);

        // Only add entries once to prevent duplication
        if (!entriesInitialized) {
            ConfigCategory general = builder.getOrCreateCategory(Component.translatable("category.heavyinventories.general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            general.addEntry(entryBuilder.startEnumSelector(Component.translatable("option.heavyinventories.weight_measure"), MeasuringSystem.class, ConfigOptions.WEIGHT_MEASURE)
                    .setDefaultValue(MeasuringSystem.LBS)
                    .setTooltip(Component.translatable("option.heavyinventories.weight_measure.tooltip"))
                    .setSaveConsumer(newValue -> ConfigOptions.WEIGHT_MEASURE = newValue)
                    .build());

            general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.heavyinventories.enable_gui_overlay"), ConfigOptions.ENABLE_GUI_OVERLAY)
                    .setDefaultValue(true)
                    .setTooltip(Component.translatable("option.heavyinventories.enable_gui_overlay.tooltip"))
                    .setSaveConsumer(newValue -> ConfigOptions.ENABLE_GUI_OVERLAY = newValue)
                    .build());

            general.addEntry(entryBuilder.startColorField(Component.translatable("option.heavyinventories.normal_text_color"), ConfigOptions.NORMAL_TEXT_COLOR)
                    .setDefaultValue(0xFFFFFF)
                    .setTooltip(Component.translatable("option.heavyinventories.normal_text_color.tooltip"))
                    .setSaveConsumer(newValue -> ConfigOptions.NORMAL_TEXT_COLOR = newValue)
                    .build());

            general.addEntry(entryBuilder.startColorField(Component.translatable("option.heavyinventories.encumbered_text_color"), ConfigOptions.ENCUMBERED_TEXT_COLOR)
                    .setDefaultValue(0xFFFF55)
                    .setTooltip(Component.translatable("option.heavyinventories.encumbered_text_color.tooltip"))
                    .setSaveConsumer(newValue -> ConfigOptions.ENCUMBERED_TEXT_COLOR = newValue)
                    .build());

            general.addEntry(entryBuilder.startColorField(Component.translatable("option.heavyinventories.over_encumbered_text_color"), ConfigOptions.OVER_ENCUMBERED_TEXT_COLOR)
                    .setDefaultValue(0xFF5555)
                    .setTooltip(Component.translatable("option.heavyinventories.over_encumbered_text_color.tooltip"))
                    .setSaveConsumer(newValue -> ConfigOptions.OVER_ENCUMBERED_TEXT_COLOR = newValue)
                    .build());
            
            entriesInitialized = true;
        }
    }

    public static ConfigBuilder getBuilder() {
        if (builder == null) {
            init();
        }
        return builder;
    }

    /**
     * Save the config.
     */
    private static void saveConfig() {
        ConfigFileManager.saveClientConfig();
    }

    @Override
    public void openConfigScreen() {
        Screen screen = getBuilder().build();
        Minecraft.getInstance().setScreen(screen);
    }
}
