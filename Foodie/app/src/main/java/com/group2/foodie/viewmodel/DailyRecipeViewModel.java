package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.dailyrecipe.DailyRecipe;
import com.group2.foodie.service.DailyRecipeService;

public class DailyRecipeViewModel extends ViewModel {
    private DailyRecipeService service;

    public DailyRecipeViewModel(){
        this.service = DailyRecipeService.getInstance();
    }

    public LiveData<DailyRecipe> getDailyRecipe(){
        return service.getDailyRecipe();
    }

    public void searchDailyRecipe() {
        service.searchDailyRecipe();
    }

    public void init() {
        service.init();
    }
}
