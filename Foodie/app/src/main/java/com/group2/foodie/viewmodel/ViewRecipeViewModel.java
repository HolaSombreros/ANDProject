package com.group2.foodie.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.group2.foodie.livedata.FavoriteLiveData;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.repository.RecipeRepository;
import com.group2.foodie.repository.UserRepository;

public class ViewRecipeViewModel extends AndroidViewModel {
    private RecipeRepository recipeRepository;
    private UserRepository userRepository;

    public ViewRecipeViewModel(Application application) {
        super(application);
        recipeRepository = RecipeRepository.getInstance();
        userRepository = UserRepository.getInstance(application);
    }

    public void init(String publisherId, String recipeId) {
        recipeRepository.init(publisherId, recipeId);
    }

    public FavoriteLiveData getFavorite() {
        return recipeRepository.getFavorite();
    }

    public LiveData<Recipe> getRecipe() {
        return recipeRepository.getSpecificRecipe();
    }

    public void removeRecipe() {
        recipeRepository.removeRecipe();
    }

    public void changeFavorite() {
        recipeRepository.changeFavorite();
    }

    public LiveData<FirebaseUser> getCurrentUser() {
        return userRepository.getCurrentFirebaseUser();
    }
}
