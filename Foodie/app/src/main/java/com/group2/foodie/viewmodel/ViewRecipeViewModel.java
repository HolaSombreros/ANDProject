package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Recipe;
import com.group2.foodie.repository.RecipeRepository;

public class ViewRecipeViewModel extends ViewModel {
    private RecipeRepository recipeRepository;

    public ViewRecipeViewModel() {
        recipeRepository = recipeRepository.getInstance();
    }

    public void init(String publisherId, String recipeId) {
        recipeRepository.init(publisherId, recipeId);
    }

    public LiveData<Recipe> getRecipe() {
        return recipeRepository.getSpecificRecipe();
    }

    public void removeRecipe() {
        recipeRepository.removeRecipe();
    }

    public void changeFavorite() {
        if (recipeRepository.getSpecificRecipe().getValue().isFavorite()) {
            recipeRepository.removeFavorite();
        } else {
            recipeRepository.addFavorite();
        }
    }
}
