package superscary.heavyinventories.data.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DisallowedMods {

    static Logger logger = LoggerFactory.getLogger(DisallowedMods.class);

    private static final String PATH = Minecraft.getInstance().gameDirectory.getAbsolutePath() + "/config/heavyinventories/disallowed.json";
    private static final File file = new File(PATH);
    private static ArrayList<DisallowedMod> disallowed = new ArrayList<>();

    public static void build () {
        if (!fileChecker()) buildFile();
        try {
            FileReader fileReader = new FileReader(getFile());
            Type type = new TypeToken<ArrayList<DisallowedMod>>() {
            }.getType();
            Gson gson = new Gson();
            disallowed = gson.fromJson(fileReader, type);
            fileReader.close();
        } catch (IOException exception) {
            logger.error("Could not read file for disallowed.json: {}", exception.getLocalizedMessage());
        }
    }

    private static void writeEmpty () {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray array = new JsonArray();
        try {
            FileWriter fileWriter = new FileWriter(getFile());
            gson.toJson(array, fileWriter);
            fileWriter.close();
        } catch (IOException exception) {
            logger.error("Could not write file for disallowed.json: {}", exception.getLocalizedMessage());
        }
    }

    public static ArrayList<DisallowedMod> get () {
        return disallowed;
    }

    public static boolean contains (String modid) {
        for (var mod : disallowed) {
            if (mod.modid().equalsIgnoreCase(modid)) return true;
        }
        return false;
    }

    /**
     * Checks if file exists
     *
     * @return {@link Boolean}
     */
    private static boolean fileChecker () {
        return getFile().exists();
    }

    /**
     * Builds file if it does not exist.
     */
    private static void buildFile () {
        try {
            getFile().mkdir();
            getFile().createNewFile();
            writeEmpty();
        } catch (IOException exception) {
            logger.error("Could not create file for disallowed.json: {}", exception.getLocalizedMessage());
        }
    }

    public static File getFile () {
        return file;
    }


}
