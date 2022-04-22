package com.group2.foodie.livedata;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.group2.foodie.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class PersonalRecipesLiveData extends LiveData<List<Recipe>> {
    private final DatabaseReference dbRef;

    public PersonalRecipesLiveData(DatabaseReference dbRef) {
        setValue(new ArrayList<>());
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.dbRef = dbRef.child("personalrecipes").child(userId);
    }

    private final ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Recipe recipe = snapshot.getValue(Recipe.class);
            recipe.setId(snapshot.getKey());
            List<Recipe> allRecipes = getValue();
            allRecipes.add(recipe);
            setValue(allRecipes);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Recipe updated = snapshot.getValue(Recipe.class);
            List<Recipe> allRecipes = getValue();

            for (int i = 0; i < allRecipes.size(); i++) {
                if (allRecipes.get(i).getId().equals(snapshot.getKey())) {
                    allRecipes.set(i, updated);
                    break;
                }
            }

            setValue(allRecipes);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            Recipe recipe = snapshot.getValue(Recipe.class);
            List<Recipe> allRecipes = getValue();
            allRecipes.remove(recipe);
            setValue(allRecipes);
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

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
