package com.group2.foodie.repositorx;

import com.group2.foodie.R;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class TempRepository {
    private static TempRepository instance;

    private TempRepository() {

    }

    public static TempRepository getInstance() {
        if (instance == null) {
            instance = new TempRepository();
        }

        return instance;
    }

    public String[] getDummyRecipeCategories() {
        return new String[] {"Antipasti", "BBQ food", "Bread & doughs", "Cakes & tea time treats",
                "Cookies", "Curry", "Drinks", "Meatballs", "Muffins", "Pasta bakes",
                "Pasta & risotto", "Pies & pastries", "Pizza", "Puddings & desserts",
                "Roast", "Salad", "Sandwiches & wraps", "Sauces & condiments", "Soup", "Stew",
                "Vegetable sides", "Other"};
    }

    public List<Recipe> getDummyRecipes() {
        List<Recipe> output = new ArrayList<>();
        List<Ingredient> aIngredients = new ArrayList<>();
        List<Ingredient> bIngredients = new ArrayList<>();
        aIngredients.add(new Ingredient("Pizza dough", 0, 200, Measurement.G, null));
        aIngredients.add(new Ingredient("Pineapple", 0, 0.5, null, null));
        bIngredients.add(new Ingredient("Ground beef", 0, 300, Measurement.G, null));
        bIngredients.add(new Ingredient("Tomato", 0, 2, null, null));
        bIngredients.add(new Ingredient("Cheese", 0, 1, Measurement.ML, null));

        output.add(new Recipe("Hawaii Pizza", R.drawable.ic_fridge, aIngredients, "Do this, then " +
                "do that - pretty straight forward.\nAlso, don't forget to eat it at the end", false, "Pizza", null));
        output.add(new Recipe("Cheeseburger", 0, bIngredients, "Do this, then " +
                "do that - pretty straight forward.\nAlso, don't forget to eat it at the end", false, "Burger", null));

        return output;
    }

    public String[] getDummyIngredientNames() {
        // TODO - FETCH FROM DATABASE
        return new String[] {
                "Avocado", "Butter (salted)", "Butter (unsalted)", "Cumin", "Garlic",
                "Ginger", "Ground beef", "Milk", "Oil", "Onion (red)", "Onion (yellow)",
                "Paprika", "Pepper", "Rice", "Salt", "Tomato", "Water"};
    }
}
