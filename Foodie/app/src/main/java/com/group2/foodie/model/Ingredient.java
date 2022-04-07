package com.group2.foodie.model;

import java.time.LocalDate;

public class Ingredient {
    private String name;
    private double quantity;
    private Measurement measurement;
    private String localDate;

    public Ingredient() {
        name = null;
        quantity = 0;
        measurement = null;
        localDate = null;
    }

    public Ingredient(String name, double quantity, Measurement measurement, String localDate) {
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
        this.localDate = localDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return String.format("%.1fx %s (%s)", quantity, name, measurement.toString());
    }
}
