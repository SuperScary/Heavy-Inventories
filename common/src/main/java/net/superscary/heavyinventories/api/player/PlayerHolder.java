package net.superscary.heavyinventories.api.player;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.superscary.heavyinventories.api.util.Functions;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a wrapper for a player, used to store their weight and max weight.
 * We still need to register data attachments to the player entity to store this information.
 * Data attachments are specific per loader.
 */
public class PlayerHolder {

    // Tunables
    private static final float ENCUMBERED_BASE_MULT = 0.25f; // encumbered speed without boots
    private static final float SUREFOOTED_OVER_PERLVL = 0.05f; // +5%/lvl when over-encumbered (L4=>0.20)
    private static final float SUREFOOTED_ENC_PERLVL = 0.10f; // +10%/lvl when encumbered (L4=>0.40)
    private static final float ENCUMBERED_SWIM_MULT = 0.75f; // 75% horizontal swim speed
    private static final float OVER_ENC_SWIM_MULT = 0.50f; // 50% horizontal swim speed
    private static final float ENCUMBERED_SINK_MULT = 1.5f;  // +50% gravity in fluids
    private static final float OVER_ENC_SINK_MULT = 3.0f;  // +200% gravity in fluids

    private static List<PlayerHolder> PLAYERS = new ArrayList<>();
    private static float STARTING_WEIGHT = 1000f;

    private final Player player;
    private float weight;
    private float maxWeight;
    private boolean encumbered;
    private boolean overEncumbered;

    // ENCHANTMENT EFFECTS
    private float bracingOffset;
    private int bracingAppliedLevel;
    private float reinforcedOffset;
    private int reinforcedAppliedLevel;
    private int sureFootedAppliedLevel = 0;
    private float sureFootedMult = 1.0f; // final movement multiplier floor in [0..1


    public PlayerHolder(Player player) {
        this.player = player;
        this.weight = 0;
        this.maxWeight = STARTING_WEIGHT;
        this.encumbered = false;
        this.overEncumbered = false;
        this.bracingOffset = 0f;
        this.reinforcedOffset = 0f;
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
     *
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the weight of the player.
     *
     * @return The weight.
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the player.
     *
     * @param weight The weight to set.
     * @return The weight.
     */
    public float setWeight(float weight) {
        this.weight = weight;
        return weight;
    }

    /**
     * Sets the maximum weight the player can carry.
     *
     * @param maxWeight The maximum weight.
     * @return The maximum weight.
     */
    public float setMaxWeight(float maxWeight) {
        this.maxWeight = maxWeight;
        return maxWeight;
    }

    /**
     * Gets the maximum weight the player can carry.
     *
     * @return The maximum weight.
     */
    public float getMaxWeight() {
        return maxWeight + getBracingOffset() + getReinforcedOffset();
    }

    public float getBaseMaxWeight() {
        return maxWeight;
    }

    public void setBracingOffset(float offset) {
        bracingOffset = offset;
    }

    public void addWeightOffset(float offset) {
        bracingOffset += offset;
    }

    public float getBracingOffset() {
        return bracingOffset;
    }

    public float getReinforcedOffset() {
        return reinforcedOffset;
    }

    /**
     * Checks if the player is encumbered.
     * The weight percentage is compared to 90% through 99.9%.
     *
     * @return True if the player is encumbered, false otherwise.
     */
    public boolean isEncumbered() {
        if (getPlayer().isCreative()) return false;

        // Allow the percentage range to be 100%-110% with strength potion.
        if (hasStrength()) return getEncumberedPercentage() >= 100 && getEncumberedPercentage() < 110;

        return getEncumberedPercentage() >= 90 && getEncumberedPercentage() < 100;
    }

    /**
     * Checks if the player is over encumbered.
     * The weight percentage is compared to 100%+.
     *
     * @return True if the player is over encumbered, false otherwise.
     */
    public boolean isOverEncumbered() {
        if (getPlayer().isCreative()) return false;

        // Allow the percentage range to be 115%-125% with strength potion.
        if (hasStrength()) return getEncumberedPercentage() >= 115 && getEncumberedPercentage() < 125;
        return getEncumberedPercentage() >= 100;
    }

    /**
     * Allows the strength effect to be checked to determine encumbrance.
     *
     * @return True if the player has the strength effect, false otherwise.
     */
    private boolean hasStrength() {
        return getPlayer().hasEffect(MobEffects.DAMAGE_BOOST);
    }

    public float getEncumberedPercentage() {
        return ((getWeight() / getMaxWeight()) * 100);
    }

    /**
     * Horizontal swim multiplier when in fluids.
     */
    public float getFluidSwimMultiplier() {
        if (isOverEncumbered()) return OVER_ENC_SWIM_MULT;
        if (isEncumbered()) return ENCUMBERED_SWIM_MULT;
        return 1.0f;
    }

    /**
     * Gravity multiplier used when falling/sinking in fluids.
     */
    public float getFluidSinkGravityMultiplier() {
        if (isOverEncumbered()) return OVER_ENC_SINK_MULT;
        if (isEncumbered()) return ENCUMBERED_SINK_MULT;
        return 1.0f;
    }

    public void applyBracing(int level, float perLevelPct, float capPct) {
        if (level < 0) level = 0;
        if (level == bracingAppliedLevel) return;

        addWeightOffset(-bracingOffset);

        float base = getBaseMaxWeight();
        float pct = Math.min(perLevelPct * level, capPct);
        float offset = (base * pct);

        addWeightOffset(offset);
        bracingOffset = offset;
        bracingAppliedLevel = level;
    }

    public void clearBracing() {
        // remove any previous bonus
        addWeightOffset(-bracingOffset);
        bracingOffset = 0f;
        bracingAppliedLevel = 0;
    }

    public void applyReinforced(int level, float perLevelPct, float capPct) {
        if (level < 0) level = 0;
        if (level == reinforcedAppliedLevel) return;

        addWeightOffset(-reinforcedOffset);

        float base = getBaseMaxWeight();
        float pct = Math.min(perLevelPct * level, capPct);
        float offset = (base * pct);

        addWeightOffset(offset);
        reinforcedOffset = offset;
        reinforcedAppliedLevel = level;
    }

    public void clearReinforced() {
        addWeightOffset(-reinforcedOffset);
        reinforcedOffset = 0f;
        reinforcedAppliedLevel = 0;
    }

    public void applySureFooted(int level) {
        if (level < 0) level = 0;
        if (level == sureFootedAppliedLevel) return;
        sureFootedAppliedLevel = level;

        float base = Functions.either(isOverEncumbered(), 0.0f, Functions.either(isEncumbered(), ENCUMBERED_BASE_MULT, 1.0f));
        float floor = Functions.either(isOverEncumbered(), SUREFOOTED_ENC_PERLVL * level, Functions.either(isEncumbered(), SUREFOOTED_ENC_PERLVL * level, 1.0f));
        this.sureFootedMult = Math.min(1.0f, Math.max(base, floor));
    }

    public void clearSureFooted() {
        this.sureFootedMult = 1.0f;
        this.sureFootedAppliedLevel = 0;
    }

    public float getSureFootedMult() {
        return this.sureFootedMult;
    }

    /**
     * Gets a list of all players.
     *
     * @return The list of players.
     */
    public static List<PlayerHolder> getPlayers() {
        return PLAYERS;
    }

    /**
     * Gets a player holder for the given player.
     *
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

    public static void setWeightStarting(float startingWeight) {
        PlayerHolder.STARTING_WEIGHT = startingWeight;
    }

}
