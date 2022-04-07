package com.group2.foodie.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group2.foodie.R;
import com.group2.foodie.dailyrecipe.DailyRecipe;
import com.group2.foodie.dailyrecipe.DailyRecipeResponse;
import com.group2.foodie.livedata.DailyRecipeLiveData;
import com.group2.foodie.livedata.RecipeListLiveData;
import com.group2.foodie.livedata.RecipeLiveData;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.service.ServiceGenerator;
import com.group2.foodie.service.SpoonacularApi;
import com.group2.foodie.util.DailyFormatter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

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

    public void removeRecipe(Recipe recipe) {
        // TODO - Uncomment and test.
//        dbRef.child(recipe.getId()).removeValue();
    }
}
