package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Recipe;
import com.group2.foodie.repository.RecipeRepository;

import java.util.List;

public class PersonalRecipesViewModel extends ViewModel {
    private RecipeRepository repository;

    public PersonalRecipesViewModel() {
        repository = RecipeRepository.getInstance();
        repository.init();
    }

    public LiveData<List<Recipe>> getPersonalRecipes() {
        return repository.getRecipes();
    }
}
