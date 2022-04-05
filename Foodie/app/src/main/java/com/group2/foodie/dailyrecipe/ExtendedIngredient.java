package com.group2.foodie.dailyrecipe;

public class ExtendedIngredient {
    private String originalName;
    private double amount;
    private String unit;

    public ExtendedIngredient(){}

    public ExtendedIngredient(String originalName, double amount, String unit) {
        this.originalName = originalName;
        this.amount = amount;
        this.unit = unit;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Ingredient " +
                "name='" + originalName + '\'' +
                ", amount=" + amount +
                ", tablespoon='" + unit;
    }
}
