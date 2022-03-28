package com.group2.foodie.repositorx;

import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.model.Recipe;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static Repository instance;

    private Repository() {

    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }

        return instance;
    }

    public List<Recipe> getDummyRecipes() {
        List<Recipe> output = new ArrayList<>();
        List<Ingredient> aIngredients = new ArrayList<>();
        List<Ingredient> bIngredients = new ArrayList<>();
        aIngredients.add(new Ingredient("Pizza dough", 0, 200, Measurement.GRAMS, null));
        aIngredients.add(new Ingredient("Pineapple", 0, 0.5, null, null));
        bIngredients.add(new Ingredient("Ground beef", 0, 300, Measurement.GRAMS, null));
        bIngredients.add(new Ingredient("Tomato", 0, 2, null, null));
        bIngredients.add(new Ingredient("Cheese", 0, 1, Measurement.LITERS, null));

        output.add(new Recipe("Hawaii Pizza", 0, aIngredients, "Do this, then " +
                "do that - pretty straight forward.\nAlso, don't forget to eat it at the end", false, "Pizza"));
        output.add(new Recipe("Cheeseburger", 0, bIngredients, "Do this, then " +
                "do that - pretty straight forward.\nAlso, don't forget to eat it at the end", false, "Burger"));

        return output;
    }

    public String[] getDummyIngredients() {
        // TODO - FETCH FROM DATABASE
        return new String[] {
                "Avocado", "Butter (salted)", "Butter (unsalted)", "Cumin", "Garlic",
                "Ginger", "Ground beef", "Milk", "Oil", "Onion (red)", "Onion (yellow)",
                "Paprika", "Pepper", "Rice", "Salt", "Tomato", "Water"};
    }
}
