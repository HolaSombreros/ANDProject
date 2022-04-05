package com.group2.foodie.service;

import com.group2.foodie.dailyrecipe.DailyRecipeResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpoonacularApi {

    @GET("recipes/random?apiKey=26661a9d28db4a8bacd57d8133c3a076")
    Call<DailyRecipeResponse> getDailyRecipe();

}
