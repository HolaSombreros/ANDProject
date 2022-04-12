package com.group2.foodie.viewmodel;

import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.repository.ShoppingListRepository;

public class AddShoppingListIngredientViewModel extends ViewModel {
    private ShoppingListRepository repository;

    public AddShoppingListIngredientViewModel(){
        repository = ShoppingListRepository.getInstance();
    }

    public void addIngredient(String name, double quantity, Measurement measurement) {
        Ingredient ingredient = new Ingredient(name, quantity, measurement);
        repository.addShoppingListIngredient(ingredient);
    }
}
