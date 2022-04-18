package com.group2.foodie.viewmodel;

import androidx.lifecycle.ViewModel;

import com.group2.foodie.livedata.ShoppingListLiveData;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.repository.ShoppingListRepository;

public class ShoppingListViewModel extends ViewModel {
    private ShoppingListRepository repository;

    public ShoppingListViewModel() {
        repository = ShoppingListRepository.getInstance();
    }
    public ShoppingListLiveData getShoppingListIngredients(){
        return repository.getShoppingListLiveData();
    }
    public void init(){
        repository.init();
    }
    public void removeIngredient(Ingredient ingredient) {
        repository.removeShoppingListIngredient(ingredient);
    }

}
