package com.group2.foodie.model;

import java.time.LocalDate;

public class Ingredient {

    private String name;
    private int imageId;
    private double quantity;
    private Measurement measurement;
    private LocalDate localDate;

    public Ingredient() {
        name = null;
        imageId = 0;
        quantity = 0;
        measurement = null;
        localDate = null;
    }

    public Ingredient(String name, int imageId, double quantity, Measurement measurement, LocalDate localDate) {
        this.name = name;
        this.imageId = imageId;
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
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

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
