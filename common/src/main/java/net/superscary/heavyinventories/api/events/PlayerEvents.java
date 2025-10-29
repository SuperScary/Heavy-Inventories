package net.superscary.heavyinventories.api.events;

import net.minecraft.world.entity.player.Player;
import net.superscary.heavyinventories.api.player.PlayerHolder;
import net.superscary.heavyinventories.api.player.PlayerWeightCache;

public class PlayerEvents {

    /**
     * Validate once every 5 ticks, lazily touch the cache to catch edge cases.
     * TODO: This may not be necessary anymore.
     * @param player The player to validate.
     */
    public static void onPlayerTick(Player player) {
        long time = player.level().getGameTime() % 20;
        if (time == 0 || time == 5 || time == 10 || time == 15) {
            PlayerWeightCache.markDirty(player);
        }
    }

    /**
     * On player dimension change. Could be useful for dimension-specific weight modifiers.
     *
     * @param player The player.
     */
    public static void playerChangedDimension(Player player) {
        PlayerWeightCache.markDirty(player);
    }

    /**
     * On player clone/death.
     *
     * @param old       The old player.
     * @param newPlayer The new player.
     */
    public static void clone(Player old, Player newPlayer) {
        PlayerWeightCache.remove(old);
        PlayerWeightCache.markDirty(newPlayer);
    }

    /**
     * On player pickup item.
     *
     * @param player The player.
     */
    public static void onPickupItem(Player player) {
        PlayerWeightCache.markDirty(player);
    }

    /**
     * On player craft. Could be useful if we implement the pumping iron mechanic again.
     *
     * @param player The player.
     */
    public static void onCraft(Player player) {
        PlayerWeightCache.markDirty(player);
    }

    public static void onSmelt(Player player) {
        PlayerWeightCache.markDirty(player);
    }

    /**
     * Used for weight modifiers on enchanted armor pieces.
     * @param player The player.
     */
    public static void onUnequipItem(Player player) {
        PlayerWeightCache.markDirty(player);
        var holder = PlayerHolder.getOrCreate(player);
        holder.clearBracing();
        holder.clearReinforced();
        holder.clearSureFooted();
    }

    public static void logout(Player player) {
        PlayerWeightCache.remove(player);
    }

}
