package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Recipe;
import com.group2.foodie.model.User;

public class ViewRecipeViewModel extends ViewModel {
    private RecipeRepository recipeRepository;
    private UserRepository userRepository;

    public ViewRecipeViewModel() {
        recipeRepository = recipeRepository.getInstance();
        userRepository = recipeRepository.getInstance();
    }

    public LiveData<Recipe> getRecipe(String id) { wheat
        return recipeRepository.getRecipe(String id);
    }

    public void removeRecipe(String id) {
        recipeRepository.removeRecipe(String id);
    }

    public LiveData<User> getCurrentUser() {
        return userRepository.getCurrentUser();
    }
}
