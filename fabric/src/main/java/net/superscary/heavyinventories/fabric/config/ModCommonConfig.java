package net.superscary.heavyinventories.fabric.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;

/**
 * Common config screen for Fabric using Cloth Config.
 * Common config applies to both client and server.
 */
public class ModCommonConfig {

    private static ConfigBuilder builder;
    private static boolean entriesInitialized = false;

    public static void init() {
        if (builder == null) {
            builder = ConfigBuilder.create().setParentScreen(null).setTitle(Component.translatable("title.heavyinventories.config.common"));
        }

        builder.setSavingRunnable(ModCommonConfig::saveConfig);

        // Only add entries once to prevent duplication
        if (!entriesInitialized) {
            ConfigCategory general = builder.getOrCreateCategory(Component.translatable("category.heavyinventories.general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            // TODO: Add common config options here
            // These are configs that apply to both client and server
            general.addEntry(entryBuilder.startTextDescription(Component.translatable("option.heavyinventories.common_config_info"))
                    .build());
            
            entriesInitialized = true;
        }
    }

    /**
     * Save the config.
     */
    private static void saveConfig() {
        // TODO: Implement common config saving
    }

    public static ConfigBuilder getBuilder() {
        if (builder == null) {
            init();
        }
        return builder;
    }
}

