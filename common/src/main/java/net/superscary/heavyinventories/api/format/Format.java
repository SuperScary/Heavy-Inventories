package net.superscary.heavyinventories.api.format;

import java.text.DecimalFormat;

public class Format {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");

    public static float format(float value) {
        return Float.parseFloat(DECIMAL_FORMAT.format(value));
    }

}
