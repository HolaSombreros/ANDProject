package com.group2.foodie.livedata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.group2.foodie.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class PersonalRecipesLiveData extends LiveData<List<Recipe>> {
    private final DatabaseReference dbRef;

    public PersonalRecipesLiveData(DatabaseReference dbRef, String uid) {
        setValue(new ArrayList<>());
        this.dbRef = dbRef.child("personalrecipes").child(uid);
    }

    private final ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Recipe recipe = snapshot.getValue(Recipe.class);
            recipe.setId(snapshot.getKey());
            List<Recipe> allRecipes = getValue();
            if(!allRecipes.contains(recipe))
                allRecipes.add(recipe);
            setValue(allRecipes);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Recipe updated = snapshot.getValue(Recipe.class);
            updated.setId(snapshot.getKey());
            List<Recipe> allRecipes = getValue();

            for (int i = 0; i < allRecipes.size(); i++) {
                if (allRecipes.get(i).getId().equals(updated.getId())) {
                    allRecipes.set(i, updated);
                    break;
                }
            }

            setValue(allRecipes);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            Recipe recipe = snapshot.getValue(Recipe.class);
            recipe.setId(snapshot.getKey());
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
