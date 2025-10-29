package net.superscary.heavyinventories.api.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.superscary.heavyinventories.api.weight.WeightCache;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

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

        JsonObject fieldObj = root.has(field)
                ? root.getAsJsonObject(field)
                : new JsonObject();

        fieldObj.addProperty(type.name().toLowerCase(), value);

        root.add(field, fieldObj);

        save(file, root);
    }

    /**
     * Writes or overwrites both weight and density under a field in one call.
     */
    public static void writeToFile(String fileName, String field, Float weight, Float density) {
        File file = new File(fileName);
        JsonObject root = loadOrCreate(file);

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
        try {
            ensureParentDirectories(file.toPath());

            if (!file.exists()) {
                save(file, new JsonObject());
                return new JsonObject();
            }

            if (file.length() == 0L) {
                return new JsonObject();
            }

            try (Reader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
                JsonObject result = GSON.fromJson(reader, JsonObject.class);
                return result != null ? result : new JsonObject();
            } catch (JsonParseException e) {
                return new JsonObject();
            }
        } catch (IOException ioe) {
            return new JsonObject();
        }

    }

    private static void save(File file, JsonObject root) {
        Path target = file.toPath();
        try {
            ensureParentDirectories(target);

            var tempPath = new File(target.getParent().toString() + File.pathSeparator + "tmp");

            Path temp = Files.createTempFile(tempPath.toPath(), target.getFileName().toString(), ".tmp");
            try (Writer writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
                GSON.toJson(root, writer);
            }

            Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            try (Writer writer = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
                GSON.toJson(root, writer);
            } catch (IOException ignored) {
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