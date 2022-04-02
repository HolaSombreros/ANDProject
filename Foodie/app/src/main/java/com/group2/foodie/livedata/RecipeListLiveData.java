package com.group2.foodie.livedata;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.group2.foodie.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeListLiveData extends LiveData<List<Recipe>> {
    private DatabaseReference dbRef;

    public RecipeListLiveData(DatabaseReference dbRef) {
        this.dbRef = dbRef;
        setValue(new ArrayList<>());
        Log.e("recipecreated", "Instantiated RecipeListLiveData!");
    }

    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
            Recipe recipe = snapshot.getValue(Recipe.class);

            Log.w("recipecreated", "Recipe: " + recipe.toString());

            List<Recipe> currentRecipes = getValue();
       //     currentRecipes.add(recipe);
            setValue(currentRecipes);
        }

        @Override
        public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

        }

        @Override
        public void onChildRemoved(DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

        }

        @Override
        public void onCancelled(DatabaseError error) {

        }
    };

    @Override
    protected void onActive() {
        super.onActive();
        dbRef.addChildEventListener(childEventListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        dbRef.removeEventListener(childEventListener);
    }
}