package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.dailyrecipe.DailyRecipe;
import com.group2.foodie.repository.RecipeRepository;

public class DailyRecipeViewModel extends ViewModel {
    private RecipeRepository recipeRepository;

    public DailyRecipeViewModel(){
        this.recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<DailyRecipe> getDailyRecipe(){
        return recipeRepository.getDailyRecipe();
    }

    public void searchDailyRecipe() {
        recipeRepository.test();
    }
    public void test() {
        recipeRepository.test();
    }

    public void init() {
        recipeRepository.init();
    }
}
