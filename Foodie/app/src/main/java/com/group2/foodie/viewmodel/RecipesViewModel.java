package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Recipe;
import com.group2.foodie.repository.RecipeRepository;

import java.util.List;

public class RecipesViewModel extends ViewModel {
    private RecipeRepository repository;
    private MutableLiveData<List<Recipe>> filteredList;

    public RecipesViewModel() {
        repository = RecipeRepository.getInstance();
        filteredList = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getPersonalRecipes() {
        return repository.getPersonalRecipes();
    }

    // TODO search for recipe
    public void filterByName(String text) {
//        List<Recipe> filteredRecipes = new ArrayList<>();
//        for (Recipe recipe : repository.getRecipe().getValue())
//            if (recipe.getName().toLowerCase().contains(text.toLowerCase()))
//                filteredRecipes.add(recipe);
//        filteredList.setValue(filteredRecipes);
    }

    public void init() {
        repository.init();
    }

    public LiveData<List<Recipe>> getPublicRecipes() {
        return repository.getPublicRecipes();
    }
}
