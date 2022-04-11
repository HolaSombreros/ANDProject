package com.group2.foodie.repositorx;

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

    public String[] getDummyIngredientNames() {
        // TODO - FETCH FROM DATABASE
        return new String[] {
                "Avocado", "Butter (salted)", "Butter (unsalted)", "Cumin", "Garlic",
                "Ginger", "Ground beef", "Milk", "Oil", "Onion (red)", "Onion (yellow)",
                "Paprika", "Pepper", "Rice", "Salt", "Tomato", "Water"};
    }
}
