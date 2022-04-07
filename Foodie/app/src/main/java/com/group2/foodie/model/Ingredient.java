package com.group2.foodie.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Ingredient {
    private String id;
    private String name;
    private double quantity;
    private Measurement measurement;
    private String expirationDate;

    public Ingredient() {

    }

    public Ingredient(String name, double quantity, Measurement measurement, String expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return String.format("%.1fx %s (%s)", quantity, name, measurement.toString());
    }

    @Exclude
    public Map<String, Object> asMap() {
        Map<String, Object> output = new HashMap<>();
        output.put("quantity", quantity);
        output.put("measurement", measurement);
        output.put("expirationDate", expirationDate);
        return output;
    }
}
