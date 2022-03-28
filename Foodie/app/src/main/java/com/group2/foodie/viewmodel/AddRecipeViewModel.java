package com.group2.foodie.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.repositorx.Repository;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeViewModel extends ViewModel {
    private MutableLiveData<String> name;
    private MutableLiveData<List<Ingredient>> ingredients;
    private MutableLiveData<String> instructions;
    private MutableLiveData<String> errorMessage;
    private Repository repository;

    public AddRecipeViewModel() {
        repository = Repository.getInstance();

        name = new MutableLiveData<>();
        ingredients = new MutableLiveData<>();
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredients.setValue(ingredientList);
        addNewIngredient();

        instructions = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
    }

    public LiveData<String> getName() {
        return name;
    }

    public String[] getCategories() {
        return new String[]{"Antipasti", "BBQ food", "Bread & doughs", "Cakes & tea time treats", "Cookies", "Curry", "Drinks", "Meatballs", "Muffins", "Pasta bakes", "Pasta & risotto", "Pies & pastries", "Pizza", "Puddings & desserts", "Roast", "Salad", "Sandwiches & wraps", "Sauces & condiments", "Soup", "Stew", "Vegetable sides"};
    }

    public MutableLiveData<List<Ingredient>> getIngredients() {
        return ingredients;
    }

    public void addNewIngredient() {
        List<Ingredient> currentIngredients = ingredients.getValue();
        currentIngredients.add(new Ingredient());
        ingredients.setValue(currentIngredients);
    }

    public LiveData<String> getInstructions() {
        return instructions;
    }

    public boolean createRecipe(String name, String category, String instructions) {
        if (name == null || name.isEmpty()) {
            errorMessage.setValue("Please enter the recipe title");
            return false;
        }

        if (category == null || category.isEmpty()) {
            errorMessage.setValue("Please select a recipe category");
            return false;
        }

        if (instructions == null || instructions.isEmpty()) {
            errorMessage.setValue("Please enter the recipe instructions");
            return false;
        }

//        // TODO - category should be an enum...?
//        Recipe recipe = new Recipe(name, 0, ingredients.getValue(), instructions, false, category);
//        Log.w("recipecreated", recipe.toString());
        return true;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void reset() {
        name.setValue(null);
        ingredients.setValue(new ArrayList<>());
        addNewIngredient();

        instructions.setValue(null);
        errorMessage.setValue(null);
    }
}
