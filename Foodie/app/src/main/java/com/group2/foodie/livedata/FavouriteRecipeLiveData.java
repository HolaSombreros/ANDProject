package com.group2.foodie.livedata;

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

    public FavouriteRecipeLiveData(DatabaseReference dbRef) {
        this.favouritesRef = dbRef.child("favorites")
                .child(FirebaseAuth.getInstance().getUid());
        this.recipesDbRef = dbRef.child("publicrecipes");
        setValue(new ArrayList<>());
    }
    private ChildEventListener favouritesListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
            String uid = snapshot.getKey();
            recipesDbRef.child(uid).get().addOnCompleteListener(task -> {
                Recipe recipe = task.getResult().getValue(Recipe.class);
                recipe.setId(uid);
                recipe.setFavorite(true);
                List<Recipe> recipes = getValue();
                if(!recipes.contains(recipe))
                    recipes.add(recipe);
                setValue(recipes);
            });
        }

        @Override
        public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
        }

        @Override
        public void onChildRemoved(DataSnapshot snapshot) {
            String uid = snapshot.getKey();
            recipesDbRef.child(uid).get().addOnCompleteListener(task -> {
                Recipe recipe = task.getResult().getValue(Recipe.class);
                recipe.setId(uid);
                List<Recipe> favourite = getValue();
                for (int i = 0; i < favourite.size(); i++) {
                    if (favourite.get(i).getId().equals(uid)) {
                        favourite.remove(i);
                        break;
                    }
                }
                setValue(favourite);
            });
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
