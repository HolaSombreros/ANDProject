package com.group2.foodie.dailyrecipe;

import java.util.ArrayList;
import java.util.List;

public class DailyRecipeResponse {
    private List<DailyRecipe> recipes;

    public DailyRecipeResponse() {
        recipes = new ArrayList<>();
    }

    public void setRecipes(List<DailyRecipe> recipes) {
        this.recipes = recipes;
    }
    public List<DailyRecipe> getRecipes() {
        return recipes;
    }
    public DailyRecipe getDailyRecipe() {
        return recipes.get(0);
    }


}
