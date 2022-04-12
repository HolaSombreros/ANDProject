package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.repository.FridgeRepository;

public class AddEditIngredientViewModel extends ViewModel {
    private FridgeRepository repository;
    private MutableLiveData<String> errorMessage;
    private String date;

    public AddEditIngredientViewModel() {
        repository = FridgeRepository.getInstance();
        errorMessage = new MutableLiveData<>();
    }

    public LiveData<Ingredient> getIngredient() {
        return repository.getFridgeIngredient();
    }

    public boolean saveIngredient(String name, String quantity, Measurement measurement, String expirationDate) {
        if (!validate(name, quantity))
            return false;

        Ingredient ingredient = new Ingredient(name, Double.parseDouble(quantity), measurement, expirationDate);
        repository.editFridgeIngredient(ingredient);
        return true;
    }

    public void init(String ingredientName) {
        repository.init(ingredientName);
    }

    public void removeIngredient() {
        repository.removeFridgeIngredient();
    }

    public boolean addIngredient(String name, String quantity, Measurement measurement, String expirationDate) {
        if (!validate(name, quantity))
            return false;

        Ingredient ingredient = new Ingredient(name, Double.parseDouble(quantity), measurement, expirationDate);
        repository.addFridgeIngredient(ingredient);
        return true;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    private boolean validate(String name, String quantity) {
        if (name == null || name.isEmpty()) {
            errorMessage.setValue("Please specify the ingredient name");
            return false;
        }

        if (quantity == null || quantity.isEmpty()) {
            errorMessage.setValue("Please specify the quantity name");
            return false;
        }

        try {
            double doubleQuantity = Double.parseDouble(quantity);

            if (doubleQuantity < 0) {
                errorMessage.setValue("The ingredient quantity (if any) must be larger or equal to 0");
                return false;
            }
        } catch (Exception e) {
            errorMessage.setValue("The ingredient quantity (if any) must be numeric");
            return false;
        }
        return true;
    }

    public void setDate(String string) {
        date = string;
    }

    public String getDate() {
        return date;
    }
}
