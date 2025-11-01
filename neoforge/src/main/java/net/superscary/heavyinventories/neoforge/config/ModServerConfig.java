package net.superscary.heavyinventories.neoforge.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;
import net.superscary.heavyinventories.config.ConfigFileManager;
import net.superscary.heavyinventories.config.ConfigOptions;

/**
 * Server config screen for Fabric using Cloth Config.
 * Note: This is a client-side GUI for viewing/editing server config.
 * Actual server config values should be synced from the server.
 */
public class ModServerConfig {

    private static ConfigBuilder builder;
    private static boolean entriesInitialized = false;

    public static void init() {
        if (builder == null) {
            builder = ConfigBuilder.create().setParentScreen(null).setTitle(Component.translatable("title.heavyinventories.config.server"));
        }

        builder.setSavingRunnable(ModServerConfig::saveConfig);

        // Only add entries once to prevent duplication
        if (!entriesInitialized) {
            ConfigCategory general = builder.getOrCreateCategory(Component.translatable("category.heavyinventories.general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            general.addEntry(entryBuilder.startFloatField(Component.translatable("option.heavyinventories.starting_max_weight"), ConfigOptions.PLAYER_STARTING_WEIGHT)
                    .setDefaultValue(1000f)
                    .setMin(0)
                    .setMax(Float.MAX_VALUE)
                    .setTooltip(Component.translatable("option.heavyinventories.starting_max_weight.tooltip"))
                    .setSaveConsumer(newValue -> ConfigOptions.PLAYER_STARTING_WEIGHT = newValue)
                    .build());
            
            entriesInitialized = true;
        }
    }

    /**
     * Save the config.
     * Note: Server config changes may need to be sent to the server.
     */
    private static void saveConfig() {
        ConfigFileManager.saveServerConfig();
    }

    public static ConfigBuilder getBuilder() {
        if (builder == null) {
            init();
        }
        return builder;
    }
}

