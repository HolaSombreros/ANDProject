package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.repository.ShoppingListRepository;

public class AddShoppingListIngredientViewModel extends ViewModel {
    private ShoppingListRepository repository;
    private MutableLiveData<String> errorMessage;

    public AddShoppingListIngredientViewModel(){
        repository = ShoppingListRepository.getInstance();
        errorMessage = new MutableLiveData<>();
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public boolean addIngredient(String name, String quantity, Measurement measurement) {
        if(validateIngredient(name, quantity)) {
            Ingredient ingredient = new Ingredient(name, (!quantity.isEmpty() ? Double.parseDouble(quantity) : 0), measurement);
            repository.addShoppingListIngredient(ingredient);
            return true;
        }
        return false;
    }

    public boolean validateIngredient(String name, String quantity) {
        if(name == null || name.trim().isEmpty()) {
            errorMessage.setValue("Please specify the ingredient's name");
            errorMessage.setValue(null);
            return false;
        }
        if (quantity == null || quantity.isEmpty()) {
            errorMessage.setValue("Please specify the quantity");
            errorMessage.setValue(null);
            return false;
        }

        try {
            double doubleQuantity = Double.parseDouble(quantity);

            if (doubleQuantity < 0) {
                errorMessage.setValue("The ingredient quantity (if any) must be larger or equal to 0");
                errorMessage.setValue(null);
                return false;
            }
        } catch (Exception e) {
            errorMessage.setValue("The ingredient quantity (if any) must be numeric");
            errorMessage.setValue(null);
            return false;
        }
        return true;
    }
}
