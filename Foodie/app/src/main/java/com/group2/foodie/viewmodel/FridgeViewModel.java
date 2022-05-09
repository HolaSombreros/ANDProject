package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Ingredient;
import com.group2.foodie.repository.FridgeRepository;

import java.util.ArrayList;
import java.util.List;

public class FridgeViewModel extends ViewModel {

    private FridgeRepository repository;
    private MutableLiveData<List<Ingredient>> filteredList;

    public FridgeViewModel() {
        repository = FridgeRepository.getInstance();
        filteredList = new MutableLiveData<>();
    }

    public LiveData<List<Ingredient>> getFridgeIngredients() {
        return repository.getFridgeIngredients();
    }

    public LiveData<List<Ingredient>> getFilteredFridgeIngredients() {
        return filteredList;
    }

    public void init() {
        repository.init("-1");
    }

    public void setFilter(String text) {
        filteredList.setValue(filterByIngredientName(text).getValue());
    }

    public LiveData<List<Ingredient>> filterByIngredientName(String name) {
        List<Ingredient> ingredients = new ArrayList<>();

        if (name == null || name.isEmpty()) {
            return repository.getFridgeIngredients();
        } else {
            for (Ingredient ingredient : repository.getFridgeIngredients().getValue()) {
                if (ingredient.getName().toLowerCase().contains(name.toLowerCase())) {
                    ingredients.add(ingredient);
                }
            }

            return new MutableLiveData<>(ingredients);
        }
    }
}
