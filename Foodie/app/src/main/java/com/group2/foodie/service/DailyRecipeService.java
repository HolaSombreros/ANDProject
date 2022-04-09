package com.group2.foodie.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group2.foodie.dailyrecipe.DailyRecipe;
import com.group2.foodie.dailyrecipe.DailyRecipeResponse;
import com.group2.foodie.dailyrecipe.ExtendedIngredient;
import com.group2.foodie.livedata.DailyRecipeLiveData;
import com.group2.foodie.util.DailyFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class DailyRecipeService {
    private static SpoonacularApi spoonacularApi;
    private static DailyRecipeService instance;
    private DailyRecipeLiveData dailyRecipeLiveData;
    private DatabaseReference dbRef;
    private FirebaseDatabase database;

    public DailyRecipeService(){
        spoonacularApi = ServiceGenerator.getSpoonacularApi();
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
        init();
    }
    public static DailyRecipeService getInstance(){
        if(instance == null)
            return new DailyRecipeService();
        return null;
    }
    public DailyRecipeLiveData getDailyRecipe() {
        return dailyRecipeLiveData;
    }

    public void init() {
        dailyRecipeLiveData = new DailyRecipeLiveData(dbRef);
    }

    public String formatDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.now().format(formatter);
    }

    public void searchDailyRecipe() {
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
                                otherDaily.getExtendedIngredients().removeIf(ex -> ex.getOriginalName().equals(""));
                                otherDaily.setDate(formatDate());
                                otherDaily.setInstructions(DailyFormatter.formatIngredients(otherDaily.getInstructions()));
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
}
