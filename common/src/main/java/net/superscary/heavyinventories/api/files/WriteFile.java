package net.superscary.heavyinventories.api.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.superscary.heavyinventories.api.weight.WeightCache;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class WriteFile {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * Writes or overwrites a single type (weight or density) under a field.
     */
    public static void writeToFile(String fileName, String field, DataType type, float value) {
        File file = new File(fileName);
        JsonObject root = loadOrCreate(file);

        // Ensure the field object exists
        JsonObject fieldObj = root.has(field)
                ? root.getAsJsonObject(field)
                : new JsonObject();

        // Add or overwrite weight/density
        fieldObj.addProperty(type.name().toLowerCase(), value);

        // Put it back into root
        root.add(field, fieldObj);

        save(file, root);
    }

    /**
     * Writes or overwrites both weight and density under a field in one call.
     */
    public static void writeToFile(String fileName, String field, Float weight, Float density) {
        File file = new File(fileName);
        JsonObject root = loadOrCreate(file);

        // Ensure the field object exists
        JsonObject fieldObj = root.has(field)
                ? root.getAsJsonObject(field)
                : new JsonObject();

        if (weight != null) {
            fieldObj.addProperty(DataType.WEIGHT.name().toLowerCase(), weight);
        }
        if (density != null) {
            fieldObj.addProperty(DataType.DENSITY.name().toLowerCase(), density);
        }

        root.add(field, fieldObj);

        save(file, root);
    }

    private static JsonObject loadOrCreate(File file) {
        /*JsonObject root = new JsonObject();
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                root = GSON.fromJson(reader, JsonObject.class);
                if (root == null) {
                    root = new JsonObject();
                }
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        return root;*/
        try {
            ensureParentDirectories(file.toPath());

            if (!file.exists()) {
                // Create an empty JSON object file
                save(file, new JsonObject());
                return new JsonObject();
            }

            if (file.length() == 0L) {
                // Empty file: treat as empty JSON object
                return new JsonObject();
            }

            try (Reader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
                return GSON.fromJson(reader, JsonObject.class) != null
                        ? GSON.fromJson(reader, JsonObject.class)
                        : new JsonObject();
            } catch (JsonParseException e) {
                // Malformed content: recover by returning empty object
                return new JsonObject();
            }
        } catch (IOException ioe) {
            // I/O problem: fail gracefully with an empty object; next save will try to write
            return new JsonObject();
        }

    }

    private static void save(File file, JsonObject root) {
        Path target = file.toPath();
        try {
            ensureParentDirectories(target);

            Path temp = Files.createTempFile(target.getParent(), target.getFileName().toString(), ".tmp");
            try (Writer writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
                GSON.toJson(root, writer);
            }
            // Atomic replace where supported; otherwise best-effort replace
            Files.move(temp, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING, java.nio.file.StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            // Best effort fallback: try direct write if atomic move failed earlier
            try (Writer writer = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
                GSON.toJson(root, writer);
            } catch (IOException ignored) {
                // Swallow to avoid crashing the game; logging could be added if desired
            }
        }

        WeightCache.clearAll();
    }


    private static void ensureParentDirectories(Path filePath) throws IOException {
        Path parent = filePath.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }


}