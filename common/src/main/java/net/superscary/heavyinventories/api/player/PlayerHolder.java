package net.superscary.heavyinventories.api.player;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.superscary.heavyinventories.api.weight.CalculateWeight;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a wrapper for a player, used to store their weight and max weight.
 * We still need to register data attachments to the player entity to store this information.
 * Data attachments are specific per loader.
 */
public class PlayerHolder {

    private static List<PlayerHolder> PLAYERS = new ArrayList<>();

    private final Player player;
    private float weight;
    private float maxWeight;
    private boolean encumbered;
    private boolean overEncumbered;

    public PlayerHolder(Player player) {
        this.player = player;
        this.weight = 0;
        this.maxWeight = 1_000;
        this.encumbered = false;
        this.overEncumbered = false;
    }

    /**
     * Main player update method.
     */
    public void update() {
        setWeight(PlayerWeightCache.getOrCompute(getPlayer()));
        this.encumbered = isEncumbered();
        this.overEncumbered = isOverEncumbered();
    }

    /**
     * Gets the player.
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the weight of the player.
     * @return The weight.
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the player.
     * @param weight The weight to set.
     * @return The weight.
     */
    public float setWeight(float weight) {
        this.weight = weight;
        return weight;
    }

    /**
     * Sets the maximum weight the player can carry.
     * @param maxWeight The maximum weight.
     * @return The maximum weight.
     */
    public float setMaxWeight(float maxWeight) {
        this.maxWeight = maxWeight;
        return maxWeight;
    }

    /**
     * Gets the maximum weight the player can carry.
     * @return The maximum weight.
     */
    public float getMaxWeight() {
        return maxWeight;
    }

    /**
     * Checks if the player is encumbered.
     * The weight percentage is compared to 90% through 99.9%.
     * @return True if the player is encumbered, false otherwise.
     */
    public boolean isEncumbered() {
        // Allow the percentage range to be 100%-110% with strength potion.
        if (hasStrength()) return getEncumberedPercentage() >= 100 && getEncumberedPercentage() < 110;

        return getEncumberedPercentage() >= 90 && getEncumberedPercentage() < 100;
    }

    /**
     * Checks if the player is over encumbered.
     * The weight percentage is compared to 100%+.
     * @return True if the player is over encumbered, false otherwise.
     */
    public boolean isOverEncumbered() {
        // Allow the percentage range to be 115%-125% with strength potion.
        if (hasStrength()) return getEncumberedPercentage() >= 115 && getEncumberedPercentage() < 125;
        return getEncumberedPercentage() >= 100;
    }

    /**
     * Allows the strength effect to be checked to determine encumbrance.
     * @return True if the player has the strength effect, false otherwise.
     */
    private boolean hasStrength() {
        return getPlayer().hasEffect(MobEffects.DAMAGE_BOOST);
    }

    public float getEncumberedPercentage() {
        return ((getWeight() / getMaxWeight()) * 100);
    }

    public boolean canJump() {
        return !encumbered && !overEncumbered;
    }

    public boolean canSwim() {
        return !encumbered && !overEncumbered;
    }

    /**
     * Gets a list of all players.
     * @return The list of players.
     */
    public static List<PlayerHolder> getPlayers() {
        return PLAYERS;
    }

    /**
     * Gets a player holder for the given player.
     * @param player The player to get the holder for.
     * @return The player holder.
     */
    public static PlayerHolder getOrCreate(Player player) {
        for (PlayerHolder playerHolder : PLAYERS) {
            if (playerHolder.getPlayer().getUUID().equals(player.getUUID())) {
                return playerHolder;
            }
        }

        PlayerHolder playerHolder = new PlayerHolder(player);
        PLAYERS.add(playerHolder);
        return playerHolder;
    }

}
