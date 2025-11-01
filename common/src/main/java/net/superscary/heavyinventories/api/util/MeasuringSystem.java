package net.superscary.heavyinventories.api.util;

public enum MeasuringSystem {
    KGS("Metric", "Kilograms", "kgs"),
    LBS("Imperial", "Pounds", "lbs"),
    NONE("None", "", "");

    final String unit;
    final String name;
    final String sub;
    MeasuringSystem(String unit, String name, String sub) {
        this.unit = unit;
        this.name = name;
        this.sub = sub;
    }

    public String getName() {
        return name;
    }

    public String getSub() {
        return sub;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", getUnit(), getName());
    }
}
