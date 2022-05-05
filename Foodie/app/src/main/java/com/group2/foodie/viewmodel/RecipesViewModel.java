package com.group2.foodie.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group2.foodie.model.Recipe;
import com.group2.foodie.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

public class RecipesViewModel extends AndroidViewModel {
    private RecipeRepository repository;
    private MutableLiveData<List<Recipe>> filteredList;

    public RecipesViewModel(Application application) {
        super(application);
        repository = RecipeRepository.getInstance();
        filteredList = new MutableLiveData<>();
    }

    public void init() {
        repository.init();
    }

    public LiveData<List<Recipe>> getPublicRecipes() {
        return repository.getPublicRecipes();
    }

    public LiveData<List<Recipe>> getPersonalRecipes() {
        return repository.getPersonalRecipes();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return filteredList;
    }

    public void setRecipes(boolean isPublic, String text) {
        filteredList.setValue(filterByName(text, isPublic).getValue());
    }

    public LiveData<List<Recipe>> filterByName(String text, boolean isPublic) {
        List<Recipe> filteredRecipes = new ArrayList<>();
        if (text.isEmpty()) {
            if (isPublic)
                return repository.getPublicRecipes();
            else
                return repository.getPersonalRecipes();
        }
        if (isPublic) {
            for (Recipe recipe1 : repository.getPublicRecipes().getValue())
                if (recipe1.getName().toLowerCase().contains(text.toLowerCase()))
                    filteredRecipes.add(recipe1);
        } else
            for (Recipe recipe2 : repository.getPersonalRecipes().getValue())
                if (recipe2.getName().toLowerCase().contains(text.toLowerCase()))
                    filteredRecipes.add(recipe2);
        return new MutableLiveData<>(filteredRecipes);
    }
}
