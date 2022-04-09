package com.group2.foodie.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.foodie.livedata.RecipeListLiveData;
import com.group2.foodie.livedata.RecipeLiveData;
import com.group2.foodie.model.Recipe;

public class RecipeRepository {
    private static RecipeRepository instance;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private RecipeListLiveData recipes;
    private RecipeLiveData recipe;

    private RecipeRepository() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
    }

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    public RecipeListLiveData getRecipes() {
        return recipes;
    }

    public RecipeLiveData getRecipe() {
        return recipe;
    }

    public void init() {
        recipes = new RecipeListLiveData(dbRef.child("users").child(FirebaseAuth.getInstance().getUid()));
    }

    public void init2(String recipeId) {
        recipe = new RecipeLiveData(dbRef.child("recipes").child(recipeId));
    }

    public void addRecipe(Recipe recipe) {
        DatabaseReference reference = dbRef.child("recipes").push();
        String recipeUid = reference.getKey();
        reference.setValue(recipe);
        dbRef.child("users").child(FirebaseAuth.getInstance().getUid()).child(recipeUid).setValue(recipe);
    }

    public void removeRecipe() {
        String recipeId = getRecipe().getValue().getId();
        dbRef.child("users").child(FirebaseAuth.getInstance().getUid()).child(recipeId).removeValue();
        dbRef.child("recipes").child(recipeId).removeValue();
    }
}
