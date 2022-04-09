package com.group2.foodie.model;

import java.time.LocalDate;

public class FridgeIngredient {
    private String name;
    private double quantity;
    private Measurement measurement;
    private LocalDate expirationDate;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return "FridgeIngredient{" + "name='" + name + '\'' + ", quantity=" + quantity +
                ", measurement=" + measurement + ", expirationDate=" + expirationDate + '}';
    }
}


