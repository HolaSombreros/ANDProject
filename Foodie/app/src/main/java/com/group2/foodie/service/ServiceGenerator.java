package com.group2.foodie.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static SpoonacularApi spoonacularApi;

    public static SpoonacularApi getSpoonacularApi(){
        if(spoonacularApi == null) {
            synchronized (new Object()){
                spoonacularApi = new Retrofit.Builder().baseUrl("https://api.spoonacular.com/").
                        addConverterFactory(GsonConverterFactory.create()).
                        build().create(SpoonacularApi.class);
            }
            return spoonacularApi;
        }
        return null;
    }
}
