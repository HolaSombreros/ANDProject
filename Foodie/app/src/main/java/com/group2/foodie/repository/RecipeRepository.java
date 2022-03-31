package com.group2.foodie.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.foodie.R;
import com.group2.foodie.livedata.RecipeListLiveData;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {
    private static RecipeRepository instance;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private RecipeListLiveData recipes;

    private RecipeRepository() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("recipes");
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
        Recipe r1 = new Recipe("Hawaii Pizza", R.drawable.ic_fridge, new ArrayList<>() , "Do this, then " +
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
    }

    public void addRecipe(Recipe recipe) {
        dbRef.push().setValue(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        // TODO - Uncomment and test.
//        dbRef.child(recipe.getId()).removeValue();
    }
}
