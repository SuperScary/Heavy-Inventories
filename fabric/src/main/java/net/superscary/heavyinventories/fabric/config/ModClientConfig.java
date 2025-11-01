package net.superscary.heavyinventories.fabric.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.superscary.heavyinventories.config.ConfigOptions;
import net.superscary.heavyinventories.api.util.Measure;
import net.superscary.heavyinventories.config.ConfigScreenOpener;

public class ModClientConfig implements ConfigScreenOpener {

    private static final ConfigBuilder builder = ConfigBuilder.create().setParentScreen(null).setTitle(Component.translatable("title.heavyinventories.config"));

    public static void init() {

        builder.setSavingRunnable(ModClientConfig::saveConfig);

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("category.heavyinventories.general"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startEnumSelector(Component.translatable("option.heavyinventories.weight_measure"), Measure.class, ConfigOptions.WEIGHT_MEASURE)
                .setDefaultValue(Measure.LBS)
                .setTooltip(Component.translatable("option.heavyinventories.weight_measure.tooltip"))
                .setSaveConsumer(newValue -> ConfigOptions.WEIGHT_MEASURE = newValue)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.heavyinventories.enable_gui_overlay"), ConfigOptions.ENABLE_GUI_OVERLAY)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("option.heavyinventories.enable_gui_overlay.tooltip"))
                .setSaveConsumer(newValue -> ConfigOptions.ENABLE_GUI_OVERLAY = newValue)
                .build());
    }

    /**
     * Save the config.
     */
    private static void saveConfig() {

    }

    @Override
    public void openConfigScreen() {
        ModClientConfig.init();
        Screen screen = builder.build();
        Minecraft.getInstance().setScreen(screen);
    }
}
