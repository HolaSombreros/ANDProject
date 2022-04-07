package com.group2.foodie.repository;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.foodie.livedata.FridgeIngredientLiveData;
import com.group2.foodie.livedata.FridgeIngredientsLiveData;
import com.group2.foodie.model.Ingredient;

import java.util.List;

public class FridgeRepository {
    private static FridgeRepository instance;
    private DatabaseReference dbRef;
    private FridgeIngredientsLiveData fridgeIngredientsLiveData;
    private FridgeIngredientLiveData fridgeIngredientLiveData;

    private FridgeRepository() {
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    public static FridgeRepository getInstance() {
        if (instance == null) {
            instance = new FridgeRepository();
        }

        return instance;
    }

    public void init(String ingredientId) {
        fridgeIngredientsLiveData = new FridgeIngredientsLiveData(dbRef.child("fridge").child(FirebaseAuth.getInstance().getUid()));
        fridgeIngredientLiveData = new FridgeIngredientLiveData(dbRef.child("fridge").child(FirebaseAuth.getInstance().getUid()).child(ingredientId));
    }

    public LiveData<List<Ingredient>> getFridgeIngredients() {
        return fridgeIngredientsLiveData;
    }

    public LiveData<Ingredient> getFridgeIngredient() {
        return fridgeIngredientLiveData;
    }

    public void addFridgeIngredient(Ingredient ingredient) {
        String userId = FirebaseAuth.getInstance().getUid();
        dbRef.child("fridge").child(userId).child(ingredient.getName()).setValue(ingredient);
    }

    public void editFridgeIngredient(Ingredient ingredient) {
        String userId = FirebaseAuth.getInstance().getUid();
        dbRef.child("fridge").child(userId).child(ingredient.getName()).updateChildren(ingredient.asMap());
    }

    public void removeFridgeIngredient() {
        String userId = FirebaseAuth.getInstance().getUid();
        Ingredient ingredient = fridgeIngredientLiveData.getValue();
        dbRef.child("fridge").child(userId).child(ingredient.getName()).removeValue();
    }
}
