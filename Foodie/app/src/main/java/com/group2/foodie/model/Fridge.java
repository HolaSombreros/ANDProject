package com.group2.foodie.model;

import java.util.List;

public class Fridge {

    private List<Ingredient> ingredients;

    public Fridge(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
