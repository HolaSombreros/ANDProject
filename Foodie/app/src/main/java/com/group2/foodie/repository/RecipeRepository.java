package com.group2.foodie.repository;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.foodie.livedata.RecipeListLiveData;
import com.group2.foodie.model.Recipe;

public class RecipeRepository {
    private static RecipeRepository instance;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private RecipeListLiveData recipes;

    private RecipeRepository() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("recipes");
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

    public void init() {
        recipes = new RecipeListLiveData(dbRef);
    }

    public void addRecipe(Recipe recipe) {
        dbRef.push().setValue(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        // TODO - Uncomment and test.
//        dbRef.child(recipe.getId()).removeValue();
    }
}
