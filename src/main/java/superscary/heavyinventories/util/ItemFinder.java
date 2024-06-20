package superscary.heavyinventories.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import superscary.heavyinventories.data.weight.ItemWeight;
import superscary.heavyinventories.data.weight.WeightGenerator;

import java.util.ArrayList;
import java.util.Comparator;

public class ItemFinder {

    /**
     * Finds all items registered to a defined modid
     *
     * @param modid The id being checked
     * @return list of {@link ItemWeight}
     */
    public static ArrayList<ItemWeight> findModItems (String modid) {
        ArrayList<ItemWeight> weights = new ArrayList<>();
        for (var entry : BuiltInRegistries.ITEM) {
            Item item = entry.asItem();
            String[] resourceName = item.toString().split(":");
            if (resourceName[0].equalsIgnoreCase(modid)) {
                weights.add(new ItemWeight(modid, resourceName[1], item.getDescription().getString(), WeightGenerator.generateForItem(entry)));
            }
        }

        Comparator<ItemWeight> comparator = Comparator.comparing(ItemWeight::registryName); // makes it less randomly put when writing to file
        weights.sort(comparator);
        return weights;
    }

}
