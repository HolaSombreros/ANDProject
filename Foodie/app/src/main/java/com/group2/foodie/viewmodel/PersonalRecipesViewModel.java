package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Recipe;
import com.group2.foodie.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

public class PersonalRecipesViewModel extends ViewModel {
    private RecipeRepository repository;
    private MutableLiveData<List<Recipe>> filteredList;

    public PersonalRecipesViewModel() {
        repository = RecipeRepository.getInstance();
        filteredList = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getPersonalRecipes() {
        return repository.getRecipes();
    }

    public void filterByName(String text) {
        List<Recipe> filteredRecipes = new ArrayList<>();
        if (repository.getRecipes().getValue() != null)
            for (Recipe recipe : repository.getRecipes().getValue())
                if (recipe.getName().toLowerCase().contains(text.toLowerCase()))
                    filteredRecipes.add(recipe);
        filteredList.setValue(filteredRecipes);
    }

    public void init() {
        repository.init();
    }
}
