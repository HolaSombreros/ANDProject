package com.group2.foodie.livedata;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.group2.foodie.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class FavouriteRecipeLiveData extends LiveData<List<Recipe>> {
    private DatabaseReference recipesDbRef;
    private DatabaseReference favouritesRef;
    private DatabaseReference publicDbRef;

    public FavouriteRecipeLiveData(DatabaseReference dbRef) {
        this.favouritesRef = dbRef.child("favorites")
                .child(FirebaseAuth.getInstance().getUid());
        this.recipesDbRef = dbRef.child("personalrecipes").child(FirebaseAuth.getInstance().getUid());
        this.publicDbRef = dbRef.child("publicrecipes");
        setValue(new ArrayList<>());
    }
    private ChildEventListener favouritesListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
            String recipeId = snapshot.getKey();

            recipesDbRef.child(recipeId).get().addOnCompleteListener(task -> {
                if (task.getResult().exists()) {
                    DataSnapshot data = task.getResult();
                    Recipe recipe = data.getValue(Recipe.class);
                    recipe.setId(data.getKey());
                    recipe.setFavorite(true);
                    List<Recipe> recipes = getValue();
                    if (!recipes.contains(recipe))
                        recipes.add(recipe);
                    setValue(recipes);
                } else {
                    publicDbRef.child(task.getResult().getKey()).get().addOnCompleteListener(result -> {
                        DataSnapshot data = result.getResult();
                        Recipe recipe = data.getValue(Recipe.class);
                        recipe.setId(data.getKey());
                        recipe.setFavorite(true);
                        List<Recipe> recipes = getValue();
                        if (!recipes.contains(recipe))
                            recipes.add(recipe);
                        setValue(recipes);
                    });
                }
            });
        }

        @Override
        public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
        }

        @Override
        public void onChildRemoved(DataSnapshot snapshot) {
            String uid = snapshot.getKey();

            List<Recipe> recipes = getValue();
            for (int i = 0; i < recipes.size(); i++) {
                if (recipes.get(i).getId().equals(uid)) {
                    recipes.remove(i);
                    break;
                }
            }

            setValue(recipes);
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
        favouritesRef.addChildEventListener(favouritesListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        favouritesRef.removeEventListener(favouritesListener);
    }



}
