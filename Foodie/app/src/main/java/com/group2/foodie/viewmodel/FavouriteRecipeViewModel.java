package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Recipe;
import com.group2.foodie.repository.FavouriteRecipeRepository;

import java.util.List;

public class FavouriteRecipeViewModel extends ViewModel {
    private FavouriteRecipeRepository repository;

    public FavouriteRecipeViewModel() {
        repository = FavouriteRecipeRepository.getInstance();
    }
    public void init(){
        repository.init();
    }
    public LiveData<List<Recipe>> getFavouriteRecipes(){
        return repository.getFavouriteRecipes();
    }

}
