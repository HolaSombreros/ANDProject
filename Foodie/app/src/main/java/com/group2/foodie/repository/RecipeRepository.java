package com.group2.foodie.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.service.ServiceGenerator;
import com.group2.foodie.service.SpoonacularApi;

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
    private static SpoonacularApi spoonacularApi;
    private DailyRecipeLiveData dailyRecipeLiveData;

    private RecipeRepository() {
        spoonacularApi = ServiceGenerator.getSpoonacularApi();
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
        init();
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

    public LiveData<List<Recipe>> getRecipe() {
        LiveData<List<Recipe>> r = new MutableLiveData<>(new ArrayList<>());
        Recipe r1 = new Recipe("Hawaii Pizza", R.drawable.ic_fridge, new ArrayList<>(), "Do this, then " +
                "do that - pretty straight forward.\nAlso, don't forget to eat it at the end", false, "Pizza", null);
        List<Ingredient> aIngredients = new ArrayList<>();
        aIngredients.add(new Ingredient("Pizza dough", 0, 200, Measurement.G, null));
        aIngredients.add(new Ingredient("Pineapple", 0, 0.5, Measurement.G, null));
        r1.setIngredients(aIngredients);
        r1.setId("r111");
        r.getValue().add(r1);
        r.getValue().add(new Recipe("Cheeseburger", 0, new ArrayList<>(), "Do this, then " +
                "do that - pretty straight forward.\nAlso, don't forget to eat it at the end", false, "Burger", null));
        return r;
    }

    public void init() {
        recipes = new RecipeListLiveData(dbRef);
        dailyRecipeLiveData = new DailyRecipeLiveData(dbRef);

    }
    public String formatDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String timeStamp = LocalDate.now().format(formatter);
        return timeStamp;
    }

    public void test() {
        DatabaseReference ref = dbRef.child("dailyrecipe");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DailyRecipe dailyRecipe = snapshot.getValue(DailyRecipe.class);
                if(dailyRecipe != null && Integer.parseInt(dailyRecipe.getDate()) < Integer.parseInt(formatDate())) {
                    Call<DailyRecipeResponse> call = spoonacularApi.getDailyRecipe();
                    call.enqueue(new Callback<DailyRecipeResponse>() {
                        @EverythingIsNonNull
                        @Override
                        public void onResponse(Call<DailyRecipeResponse> call, Response<DailyRecipeResponse> response) {
                            if (response.isSuccessful()) {
                                DailyRecipe otherDaily = response.body().getDailyRecipe();
                                otherDaily.setDate(formatDate());
                                dbRef.child("dailyrecipe").setValue(otherDaily);
                            }
                        }

                        @EverythingIsNonNull
                        @Override
                        public void onFailure(Call<DailyRecipeResponse> call, Throwable t) {
                            Log.i("Retrofit", "Something went wrong :(");
                            Log.w("error", t.getMessage());
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addRecipe(Recipe recipe) {
        dbRef.child("recipes").push().setValue(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        // TODO - Uncomment and test.
//        dbRef.child(recipe.getId()).removeValue();
    }

    public DailyRecipeLiveData getDailyRecipe() {
        return dailyRecipeLiveData;
    }

}
