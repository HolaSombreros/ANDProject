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

    public void init(String recipeId) {
        recipeRepository.init2(recipeId);
    }

    public LiveData<Recipe> getRecipe() {
        return recipeRepository.getRecipe();
    }

    public void removeRecipe() {
        //recipeRepository.removeRecipe(String id);
    }

    public LiveData<User> getCurrentUser() {
        //return userRepository.getCurrentUser();
        return null;
    }
}
