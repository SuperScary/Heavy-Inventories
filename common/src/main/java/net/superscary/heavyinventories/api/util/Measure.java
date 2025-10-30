package net.superscary.heavyinventories.api.util;

public enum Measure {
    KGS,
    LBS,
    NONE;

    @Override
    public String toString() {
        if (this == NONE) return "";
        return name().toLowerCase();
    }
}
