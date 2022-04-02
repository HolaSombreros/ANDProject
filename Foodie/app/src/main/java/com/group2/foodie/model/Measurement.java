package com.group2.foodie.model;

public enum Measurement {
    QUANTITY(""),
    G("g"),
    ML("ml"),
    TBSP("tsbp"),
    TSP("tsp");

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
