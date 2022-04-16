package com.group2.foodie.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Recipe;
import com.group2.foodie.model.User;
import com.group2.foodie.repository.RecipeRepository;
import com.group2.foodie.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ViewRecipeViewModel extends AndroidViewModel {
    private RecipeRepository recipeRepository;
    private UserRepository userRepository;

    public ViewRecipeViewModel(Application application) {
        super(application);
        recipeRepository = recipeRepository.getInstance();
        userRepository = userRepository.getInstance(application);
    }

    public void init(String recipeId) {
        recipeRepository.init2(recipeId);
    }

    public LiveData<Recipe> getRecipe() {
        return recipeRepository.getRecipe();
    }

    public void removeRecipe() {
        recipeRepository.removeRecipe();
    }

    public LiveData<User> getCurrentUser() {
        //return userRepository.getCurrentUser();
        return null;
    }

    public void changeFavorite() {
        if (recipeRepository.getRecipe().getValue().isFavorite()) {
            recipeRepository.removeFavorite();
        } else {
            recipeRepository.addFavorite();
        }
    }
}
