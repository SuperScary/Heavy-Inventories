package net.superscary.heavyinventories.api.weight;

/**
 * If the default weight for an item is overridden in the .json5 file, with convention to its "modid.json5",
 * we will load that weight instead here and return it.
 */
public final class WeightOverride {

    private WeightOverride() {}

    public static final Weight ZERO_WEIGHT = new Weight() {

        @Override
        public float getWeight() {
            return 0;
        }
    };

}
