package net.superscary.heavyinventories.api.player;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.superscary.heavyinventories.api.weight.CalculateWeight;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Caches a player's total carried weight. Recompute only when "dirty"
 * or when an inventory fingerprint changes.
 */
public final class PlayerWeightCache {
    private static final Map<UUID, Entry> CACHE = new ConcurrentHashMap<>();

    private PlayerWeightCache() {
    }

    public static float getOrCompute(Player player) {
        var id = player.getUUID();
        var entry = CACHE.computeIfAbsent(id, k -> new Entry());

        // Fast path: if not dirty and fingerprint matches, return cached value
        int fp = fingerprint(player);
        if (!entry.dirty && entry.lastFingerprint == fp) {
            return entry.weight;
        }

        // Recompute
        float w = CalculateWeight.from(player);
        entry.weight = w;
        entry.lastFingerprint = fp;
        entry.dirty = false;
        return w;
    }

    /**
     * Mark a player's cache entry as dirty (recompute next read).
     */
    public static void markDirty(Player player) {
        CACHE.computeIfAbsent(player.getUUID(), k -> new Entry()).dirty = true;
        PlayerHolder.getOrCreate(player).update();
    }

    /**
     * Clear all (e.g., on datapack/config reload).
     */
    public static void clearAll() {
        CACHE.clear();
    }

    /**
     * Remove one player (e.g., on logout).
     */
    public static void remove(Player player) {
        CACHE.remove(player.getUUID());
    }

    private static final class Entry {
        volatile float weight = 0f;
        volatile int lastFingerprint = 0;
        volatile boolean dirty = true;
    }

    /**
     * Lightweight inventory fingerprint. Cheap integer that changes when
     * item type, count, or simple damage changes across main, armor, offhand.
     * (If you need NBT/components sensitivity, extend the hash.)
     */
    private static int fingerprint(Player p) {
        int h = 1;
        for (ItemStack s : p.getInventory().items) h = mix(h, s);
        for (ItemStack s : p.getInventory().armor) h = mix(h, s);
        for (ItemStack s : p.getInventory().offhand) h = mix(h, s);
        return h;
    }

    private static int mix(int h, ItemStack s) {
        if (s.isEmpty()) return h * 31 + 1;
        // key by Item identity + count + basic damage
        int x = System.identityHashCode(s.getItem());
        x = 31 * x + s.getCount();
        if (s.isDamageableItem()) x = 31 * x + s.getDamageValue();
        return 31 * h + x;
    }
}
