package com.group2.foodie.model;

import androidx.annotation.NonNull;

public enum Measurement {
    GRAMS("g"), LITERS("l"), QTY("qty");
    private String name;

    Measurement(String name) {
        this.name = name;
    }

    @NonNull
    public String toString() {
        return name;
    }
}
