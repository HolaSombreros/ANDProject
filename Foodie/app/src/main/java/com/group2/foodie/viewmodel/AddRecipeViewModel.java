package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.repositorx.TempRepository;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeViewModel extends ViewModel {
    private MutableLiveData<List<Ingredient>> ingredients;
    private MutableLiveData<String> errorMessage;
    private TempRepository repository;

    public AddRecipeViewModel() {
        repository = TempRepository.getInstance();
        ingredients = new MutableLiveData<>();
        ingredients.setValue(new ArrayList<>());
        errorMessage = new MutableLiveData<>();
    }

    public String[] getRecipeCategories() {
        return repository.getDummyRecipeCategories();
    }

    public String[] getIngredientNames() {
        return repository.getDummyIngredientNames();
    }

    public String[] getIngredientMeasurements() {
        String[] output = new String[Measurement.values().length];

        int i = 0;
        for (Measurement measurement : Measurement.values()) {
            output[i++] = measurement.toString();
        }

        return output;
    }

    public LiveData<List<Ingredient>> getIngredients() {
        return ingredients;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public boolean addNewIngredient(String name, String quantity, String measurement) {
        if (isIngredientInputValid(name, quantity)) {

            Ingredient newIngredient = new Ingredient(name,
                    0,
                    (!quantity.isEmpty() ? Integer.parseInt(quantity) : 0),
                    Measurement.fromString(measurement),
                    null);

            List<Ingredient> currentIngredients = ingredients.getValue();
            currentIngredients.add(newIngredient);
            ingredients.setValue(currentIngredients);

            return true;
        }

        return false;
    }

    private boolean isIngredientInputValid(String name, String quantity) {
        if (name == null || name.isEmpty()) {
            errorMessage.setValue("Please specify the ingredient name");
            return false;
        }

        if (quantity != null && !quantity.isEmpty()) {
            try {
                int intQuantity = Integer.parseInt(quantity);

                if (intQuantity <= 0) {
                    errorMessage.setValue("The ingredient quantity (if any) must be larger than 0");
                    return false;
                }
            } catch (Exception e) {
                errorMessage.setValue("The ingredient quantity (if any) must be numeric");
                return false;
            }
        }

        return true;
    }

    public void removeIngredient(Ingredient ingredient) {
        List<Ingredient> currentIngredients = ingredients.getValue();
        currentIngredients.remove(ingredient);
        ingredients.setValue(currentIngredients);
    }

    public boolean addRecipe(String name, String category, String instructions) {
        if (name == null || name.isEmpty()) {
            errorMessage.setValue("Please specify the recipe title");
            return false;
        }

        if (category == null || category.isEmpty()) {
            errorMessage.setValue("Please specify the recipe category");
            return false;
        }

        if (ingredients.getValue().size() < 1) {
            errorMessage.setValue("Please specify the recipe ingredients");
            return false;
        }

        if (instructions == null || instructions.isEmpty()) {
            errorMessage.setValue("Please specify the recipe instructions");
            return false;
        }

        // TODO - category should be an enum...?
        Recipe recipe = new Recipe(name, 0, ingredients.getValue(), instructions, false, category, null);
        return true;
    }

    public void reset() {
        ingredients.setValue(new ArrayList<>());
        errorMessage.setValue(null);
    }
}
