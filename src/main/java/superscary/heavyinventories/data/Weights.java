package superscary.heavyinventories.data;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import superscary.heavyinventories.data.weight.ItemWeight;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public final class Weights {

    static Logger logger = LoggerFactory.getLogger(Weights.class);

    private static final String PATH = Minecraft.getInstance().gameDirectory.getAbsolutePath() + "/weight/";

    private final String modid;
    private final String path;
    private final File file;

    public Weights (String modid) {
        this.modid = modid;
        this.path = PATH + modid;
        this.file = new File(this.path + ".json");
    }

    /**
     * Creates {@link Gson} instance of each {@link ItemWeight}
     *
     * @param modItems list of contained items, look at {@link superscary.heavyinventories.util.ItemFinder}
     */
    public void write (List<ItemWeight> modItems) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray array = new JsonArray();

        for (var item : modItems) {
            JsonObject container = new JsonObject();
            JsonObject object = new JsonObject();
            object.add("readable_name", gson.toJsonTree(item.readableName()));
            object.add("weight", gson.toJsonTree(item.weight()));
            container.add(item.registryName(), object);
            array.add(container);
        }
        writeItem(gson, array);
    }

    public void read (List<ItemWeight> modItems) {
        for (var item : modItems) {
            readItem(item);
        }
    }

    /**
     * Writes {@link JsonArray} to file
     *
     * @param gson  {@link Gson}
     * @param array {@link JsonArray}
     */
    private void writeItem (Gson gson, JsonArray array) {
        if (!fileChecker()) buildFile();
        try {
            FileWriter fileWriter = new FileWriter(getFile());
            gson.toJson(array, fileWriter);
            fileWriter.close();
        } catch (IOException exception) {
            logger.error("Could not write file for {}!", getModid());
        }
    }

    private void readItem (ItemWeight item) {
        if (!fileChecker()) buildFile();

        try {
            FileReader fileReader = new FileReader(getFile());
            Type type = new TypeToken<ArrayList<ItemWeight>>() {
            }.getType();
            Gson gson = new Gson();
            ArrayList<ItemWeight> list = gson.fromJson(fileReader, type);
            fileReader.close();
            for (var i : list) {
                System.out.println(i.readableName() + " " + i.weight());
            }
        } catch (IOException exception) {
            logger.error("Could not read file for {}!", getModid());
        }

    }

    /**
     * Builds file if it does not exist.
     */
    private void buildFile () {
        try {
            getFile().createNewFile();
        } catch (IOException exception) {
            logger.error("Could not create file for {}!", getModid());
        }
    }

    /**
     * Checks if file exists
     *
     * @return {@link Boolean}
     */
    private boolean fileChecker () {
        return getFile().exists();
    }

    public String getModid () {
        return modid;
    }

    public String getPath () {
        return path;
    }

    public File getFile () {
        return file;
    }
}
