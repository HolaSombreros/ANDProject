package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.repository.FridgeRepository;

public class AddEditIngredientViewModel extends ViewModel {
    private FridgeRepository repository;
    private String date;

    // TODO - Data validation.
    public AddEditIngredientViewModel() {
        repository = FridgeRepository.getInstance();
    }

    public LiveData<Ingredient> getIngredient() {
        return repository.getFridgeIngredient();
    }

    public void saveIngredient(String name, double quantity, Measurement measurement, String expirationDate) {
        Ingredient ingredient = new Ingredient(name, quantity, measurement, expirationDate);
        repository.editFridgeIngredient(ingredient);
    }

    public void init(String ingredientName) {
        repository.init(ingredientName);
    }

    public void removeIngredient() {
        repository.removeFridgeIngredient();
    }

    public void addIngredient(String name, double quantity, Measurement measurement, String expirationDate) {
        Ingredient ingredient = new Ingredient(name, quantity, measurement, expirationDate);
        repository.addFridgeIngredient(ingredient);
    }

    public void setDate(String string) {
        date = string;
    }

    public String getDate() {
        return date;
    }
}
