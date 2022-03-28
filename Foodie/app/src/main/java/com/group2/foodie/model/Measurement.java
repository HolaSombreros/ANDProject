package com.group2.foodie.model;

public enum Measurement {
    GRAMS("g"), LITERS("ml");
    private String name;

    Measurement(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static Measurement fromString(String measurement) {
        for (Measurement m : values()) {
            if (m.name.equalsIgnoreCase(measurement)) {
                return m;
            }
        }

        return null;
    }
}
