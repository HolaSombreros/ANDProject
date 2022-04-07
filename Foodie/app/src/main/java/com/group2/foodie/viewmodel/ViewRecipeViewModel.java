package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Recipe;
import com.group2.foodie.model.User;
import com.group2.foodie.repository.RecipeRepository;
import com.group2.foodie.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ViewRecipeViewModel extends ViewModel {
    private RecipeRepository recipeRepository;
    private UserRepository userRepository;

    public ViewRecipeViewModel() {
        recipeRepository = recipeRepository.getInstance();
        userRepository = userRepository.getInstance();
    }

    public LiveData<Recipe> getRecipe(String id) {
        List<Recipe> recipes = recipeRepository.getRecipes().getValue();
        for (Recipe r : recipes) {
            if (r.getId().equals(id))
                return new MutableLiveData<>(r);
        }
        return null;
    }

    public void removeRecipe(String id) {
        //recipeRepository.removeRecipe(String id);
    }

    public LiveData<User> getCurrentUser() {
        //return userRepository.getCurrentUser();
        return null;
    }
}
