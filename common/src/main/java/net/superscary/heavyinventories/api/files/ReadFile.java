package net.superscary.heavyinventories.api.files;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.superscary.heavyinventories.HeavyInventories;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {

    public static float get(ItemLike item, DataType type) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item.asItem());
        return readFromFile(Minecraft.getInstance().gameDirectory.getPath() + "/weights/" + id.getNamespace() + ".json", item, type);
    }

    /**
     * Reads a specific property (weight or density) for a given item from the JSON file.
     *
     * @param fileName The JSON file path.
     * @param item     The Minecraft ItemLike.
     * @param type     The property to read.
     * @return The value if found, otherwise 0.
     */
    public static float readFromFile(String fileName, ItemLike item, DataType type) {
        File file = new File(fileName);
        if (!file.exists()) {
            return 0.1f;
        }

        try (FileReader reader = new FileReader(file)) {
            var jsonElement = JsonParser.parseReader(reader);
            if (jsonElement == null || jsonElement.isJsonNull()) {
                return 0.1f;
            }
            
            JsonObject root = jsonElement.getAsJsonObject();

            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item.asItem());

            String field = id.getPath();

            if (root.has(field)) {
                JsonObject fieldObj = root.getAsJsonObject(field);
                String property = type.name().toLowerCase();

                if (fieldObj.has(property)) {
                    return fieldObj.get(property).getAsFloat();
                }
            }
        } catch (IOException e) {
            // Fail gracefully
            HeavyInventories.LOGGER.warn("Failed to read file: {}!", fileName);
        } catch (Exception e) {
            // Handle any other JSON parsing errors
            HeavyInventories.LOGGER.warn("Failed to parse JSON file: {}! Error: {}", fileName, e.getMessage());
        }

        return 0.1f; // default if not found
    }
}
