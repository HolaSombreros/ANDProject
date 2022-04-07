package com.group2.foodie.repository;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.foodie.livedata.FridgeIngredientsLiveData;
import com.group2.foodie.model.FridgeIngredient;
import com.group2.foodie.model.Ingredient;

import java.util.List;

public class FridgeRepository {
    private static FridgeRepository instance;
    private DatabaseReference dbRef;
    private FridgeIngredientsLiveData fridgeIngredientsLiveData;

    private FridgeRepository() {
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    public static FridgeRepository getInstance() {
        if (instance == null) {
            instance = new FridgeRepository();
        }

        return instance;
    }

    public void init() {
        fridgeIngredientsLiveData = new FridgeIngredientsLiveData(dbRef);
    }

    public LiveData<List<Ingredient>> getFridgeIngredients() {
        return fridgeIngredientsLiveData;
    }
}
