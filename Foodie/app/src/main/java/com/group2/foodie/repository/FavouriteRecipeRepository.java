package com.group2.foodie.repository;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.foodie.livedata.FavouriteRecipeLiveData;
import com.group2.foodie.model.Recipe;

import java.util.List;

public class FavouriteRecipeRepository {
    private static FavouriteRecipeRepository instance;
    private DatabaseReference dbRef;
    private FavouriteRecipeLiveData favouriteRecipeLiveData;

    public static FavouriteRecipeRepository getInstance() {
        if(instance == null){
            instance = new FavouriteRecipeRepository();
        }
        return instance;
    }

    public FavouriteRecipeRepository() {
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    public void init(){
        favouriteRecipeLiveData = new FavouriteRecipeLiveData(dbRef);
    }

    public LiveData<List<Recipe>> getFavouriteRecipes(){
        return favouriteRecipeLiveData;
    }


}
