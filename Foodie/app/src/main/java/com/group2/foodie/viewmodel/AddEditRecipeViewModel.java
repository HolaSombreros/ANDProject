package com.group2.foodie.viewmodel;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.repositorx.TempRepository;
import com.group2.foodie.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

public class AddEditRecipeViewModel extends ViewModel {
    private MutableLiveData<List<Ingredient>> ingredients;
    private MutableLiveData<String> errorMessage;
    private TempRepository repository;
    private RecipeRepository recipeRepository;

    public AddEditRecipeViewModel() {
        repository = TempRepository.getInstance();
        recipeRepository = RecipeRepository.getInstance();
        ingredients = new MutableLiveData<>();
        ingredients.setValue(new ArrayList<>());
        errorMessage = new MutableLiveData<>();
    }

    public void init(String recipeId) {
        recipeRepository.init2(recipeId);
    }

    public LiveData<Recipe> getRecipe() {
        return recipeRepository.getRecipe();
    }

    public String[] getRecipeCategories() {
        return repository.getDummyRecipeCategories();
    }

    public String[] getIngredientNames() {
        return repository.getDummyIngredientNames();
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
                    (!quantity.isEmpty() ? Double.parseDouble(quantity) : 0),
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
                double intQuantity = Double.parseDouble(quantity);

                if (intQuantity < 0) {
                    errorMessage.setValue("The ingredient quantity (if any) must be larger or equal to 0");
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

    public boolean editRecipe(String name, String category, boolean isPublic, String instructions) {
        if (!isValid(name, category, instructions))
            return false;

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Recipe recipe = new Recipe(name, 0, ingredients.getValue(), instructions, isPublic, category, currentUser.getUid(), currentUser.getDisplayName());
        recipeRepository.editRecipe(recipe);
        return true;
    }

    public void uploadRecipeImage(Bitmap bitmap, String path) {
        recipeRepository.uploadRecipeImage(bitmap, path);
    }

    public String addRecipe(String name, String category, boolean isPublic, String instructions) {
        if (!isValid(name, category, instructions)) {
            return null;
        }

        // TODO - category should be an enum...? - display name also is null
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Recipe recipe = new Recipe(name, 0, ingredients.getValue(), instructions, isPublic, category, currentUser.getUid(), currentUser.getDisplayName());
        return recipeRepository.addRecipe(recipe);
    }

    public boolean isValid(String name, String category, String instructions) {
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

        return true;
    }

    public void reset() {
        ingredients.setValue(new ArrayList<>());
        errorMessage.setValue(null);
    }
}
