package net.superscary.heavyinventories.api.weight;

import net.minecraft.world.item.Item;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.DoubleSupplier;

/**
 * Caches item/stack weights to avoid hitting disk/parsing every time.
 */
public final class WeightCache {
    private static final ConcurrentHashMap<Item, Float> PER_ITEM = new ConcurrentHashMap<>();

    private WeightCache() {
    }

    /**
     * Get (or compute) the cached per-item weight for an Item.
     */
    public static float get(Item item, DoubleSupplier compute) {
        return PER_ITEM.computeIfAbsent(item, k -> (float) compute.getAsDouble());
    }

    /**
     * Put/override a cached per-item weight (e.g., after a command).
     */
    public static void put(Item item, float weight) {
        PER_ITEM.put(item, weight);
    }

    /**
     * Invalidate a single item's cache entry.
     */
    public static void invalidate(Item item) {
        PER_ITEM.remove(item);
    }

    /**
     * Clear all cached entries (e.g., on datapack/config reload).
     */
    public static void clearAll() {
        PER_ITEM.clear();
    }
}
