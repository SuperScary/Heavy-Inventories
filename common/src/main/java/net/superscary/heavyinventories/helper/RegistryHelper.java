package net.superscary.heavyinventories.helper;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;

/**
 * Utility class for retrieving registered blocks and items associated with a specific mod ID.
 */
public final class RegistryHelper {

    /**
     * Gets all registered items for a given modid.
     * @param modid The modid to search for.
     * @return A list of all items for the given modid.
     */
    public static List<Item> getItemsFor(String modid) {
        return BuiltInRegistries.ITEM.stream()
                .filter(item -> BuiltInRegistries.ITEM.getKey(item).getNamespace().equals(modid)).toList();
    }

    /**
     * Gets all registered blocks for a given modid.
     * @param modid The modid to search for.
     * @return A list of all blocks for the given modid.
     */
    public static List<Block> getBlocksFor(String modid) {
        return BuiltInRegistries.BLOCK.stream()
                .filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(modid)).toList();
    }

}
