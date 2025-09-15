package net.superscary.heavyinventories.api.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
        JsonObject root = new JsonObject();
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                root = GSON.fromJson(reader, JsonObject.class);
                if (root == null) {
                    root = new JsonObject();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return root;
    }

    private static void save(File file, JsonObject root) {
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(root, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}