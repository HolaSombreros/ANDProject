package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.livedata.ShoppingListLiveData;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.repository.ShoppingListRepository;

public class ShoppingListViewModel extends ViewModel {
    private ShoppingListRepository repository;
    private MutableLiveData<String> errorMessage;

    public ShoppingListViewModel() {
        repository = ShoppingListRepository.getInstance();
        errorMessage = new MutableLiveData<>();
    }

    public LiveData<String> getErrorMessage(){
        return errorMessage;
    }

    public ShoppingListLiveData getShoppingListIngredients(){
        return repository.getShoppingListLiveData();
    }

    public void init(){
        repository.init();
    }

    public void removeIngredient(String id) {
        repository.removeShoppingListIngredient(id);
    }
}
