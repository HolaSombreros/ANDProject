package com.group2.foodie.livedata;

import androidx.lifecycle.LiveData;

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
    }

    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
            Recipe recipe = snapshot.getValue(Recipe.class);
            List<Recipe> currentRecipes = getValue();
       //     currentRecipes.add(recipe);
            setValue(currentRecipes);
        }

        @Override
        public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

        }

        @Override
        public void onChildRemoved(DataSnapshot snapshot) {
            Recipe recipe = snapshot.getValue(Recipe.class);
            List<Recipe> currentRecipes = getValue();
            currentRecipes.remove(recipe);
            setValue(currentRecipes);
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
        dbRef.addChildEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        dbRef.removeEventListener(listener);
    }
}
