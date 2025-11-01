package net.superscary.heavyinventories.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.client.Minecraft;
import net.superscary.heavyinventories.HeavyInventories;
import net.superscary.heavyinventories.api.util.MeasuringSystem;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Manages saving and loading of config files for Fabric.
 * Client config is saved to .minecraft/config/heavyinventories-client.json
 */
public class ConfigFileManager {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final String CLIENT_CONFIG_FILE = "heavyinventories-client.json";
    @SuppressWarnings("unused")
    private static final String SERVER_CONFIG_FILE = "heavyinventories-server.json";
    @SuppressWarnings("unused")
    private static final String COMMON_CONFIG_FILE = "heavyinventories-common.json";

    /**
     * Loads client config from file and applies it to ConfigOptions.
     * Should be called during mod initialization on client side.
     */
    public static void loadClientConfig() {

        Path configFile = getConfigDirectory().resolve(CLIENT_CONFIG_FILE);

        if (!Files.exists(configFile)) {
            return;
        }

        try (Reader reader = Files.newBufferedReader(configFile, StandardCharsets.UTF_8)) {
            JsonObject root = GSON.fromJson(reader, JsonObject.class);
            if (root == null) return;

            // Load weight measure
            if (root.has("weightMeasure")) {
                String measureStr = root.get("weightMeasure").getAsString();
                try {
                    ConfigOptions.WEIGHT_MEASURE = MeasuringSystem.valueOf(measureStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    HeavyInventories.LOGGER.warn("Invalid weight measure in config: {}, using default", measureStr);
                }
            }

            if (root.has("enableGuiOverlay")) {
                ConfigOptions.ENABLE_GUI_OVERLAY = root.get("enableGuiOverlay").getAsBoolean();
            }

            if (root.has("normalTextColor")) {
                ConfigOptions.NORMAL_TEXT_COLOR = root.get("normalTextColor").getAsInt();
            }

            if (root.has("overencumberedTextColor")) {
                ConfigOptions.OVER_ENCUMBERED_TEXT_COLOR = root.get("overencumberedTextColor").getAsInt();
            }

            if (root.has("encumberedTextColor")) {
                ConfigOptions.ENCUMBERED_TEXT_COLOR = root.get("encumberedTextColor").getAsInt();
            }

        } catch (IOException | JsonParseException e) {
            HeavyInventories.LOGGER.error("Failed to load client config: {}", e.getMessage());
        }
    }

    /**
     * Saves current client config values to file.
     * Called when user clicks "Done" in the config screen.
     */
    public static void saveClientConfig() {
        Path configFile = getConfigDirectory().resolve(CLIENT_CONFIG_FILE);
        JsonObject root = new JsonObject();

        root.addProperty("weightMeasure", ConfigOptions.WEIGHT_MEASURE.name());
        root.addProperty("enableGuiOverlay", ConfigOptions.ENABLE_GUI_OVERLAY);
        root.addProperty("normalTextColor", ConfigOptions.NORMAL_TEXT_COLOR);
        root.addProperty("overencumberedTextColor", ConfigOptions.OVER_ENCUMBERED_TEXT_COLOR);
        root.addProperty("encumberedTextColor", ConfigOptions.ENCUMBERED_TEXT_COLOR);

        try {
            ensureParentDirectories(configFile);
            Path temp = Files.createTempFile(configFile.getParent(), configFile.getFileName().toString(), ".tmp");

            try (Writer writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
                GSON.toJson(root, writer);
            }

            Files.move(temp, configFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            HeavyInventories.LOGGER.info("Client config saved successfully");
        } catch (IOException e) {
            HeavyInventories.LOGGER.error("Failed to save client config: {}", e.getMessage());
            try (Writer writer = Files.newBufferedWriter(configFile, StandardCharsets.UTF_8)) {
                GSON.toJson(root, writer);
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Loads server config from file.
     */
    public static void loadServerConfig() {
        Path configFile = getConfigDirectory().resolve(SERVER_CONFIG_FILE);

        if (!Files.exists(configFile)) {
            return;
        }

        try (Reader reader = Files.newBufferedReader(configFile, StandardCharsets.UTF_8)) {
            JsonObject root = GSON.fromJson(reader, JsonObject.class);
            if (root == null) return;

            if (root.has("startingWeight")) {
                ConfigOptions.PLAYER_STARTING_WEIGHT = root.get("startingWeight").getAsInt();
            }

        } catch (IOException | JsonParseException e) {
            HeavyInventories.LOGGER.error("Failed to load server config: {}", e.getMessage());
        }
    }

    /**
     * Saves server config to file.
     */
    public static void saveServerConfig() {
        Path configFile = getConfigDirectory().resolve(SERVER_CONFIG_FILE);
        JsonObject root = new JsonObject();

        root.addProperty("startingWeight", ConfigOptions.PLAYER_STARTING_WEIGHT);

        try {
            ensureParentDirectories(configFile);
            Path temp = Files.createTempFile(configFile.getParent(), configFile.getFileName().toString(), ".tmp");

            try (Writer writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
                GSON.toJson(root, writer);
            }

            Files.move(temp, configFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            HeavyInventories.LOGGER.info("Server config saved successfully");
        } catch (IOException e) {
            HeavyInventories.LOGGER.error("Failed to save server config: {}", e.getMessage());
            // Fallback: try direct write
            try (Writer writer = Files.newBufferedWriter(configFile, StandardCharsets.UTF_8)) {
                GSON.toJson(root, writer);
            } catch (IOException ignored) {
                HeavyInventories.LOGGER.error("Fallback save also failed");
            }
        }
    }

    /**
     * Loads common config from file.
     * TODO: Implement when common config options are added.
     */
    public static void loadCommonConfig() {
        // TODO: Implement common config loading
    }

    /**
     * Saves common config to file.
     * TODO: Implement when common config options are added.
     */
    public static void saveCommonConfig() {
        // TODO: Implement common config saving
    }

    /**
     * Gets the config directory path (usually .minecraft/config).
     */
    private static Path getConfigDirectory() {
        return Minecraft.getInstance().gameDirectory.toPath().resolve("config");
    }

    /**
     * Ensures parent directories exist.
     */
    private static void ensureParentDirectories(Path filePath) throws IOException {
        Path parent = filePath.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }
}

