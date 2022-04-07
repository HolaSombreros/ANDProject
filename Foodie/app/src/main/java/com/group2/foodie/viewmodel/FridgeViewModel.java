package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Ingredient;

import java.util.List;

public class FridgeViewModel extends ViewModel {

    private FridgeRepository repository;

    public FridgeViewModel() {
        repository = FridgeRepository.getInstance();
    }

    public LiveData<List<Ingredient>> getFridgeIngredients() {
        return repository.getFridgeIngredients();
    }
}
