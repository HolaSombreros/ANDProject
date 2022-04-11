package com.group2.foodie.livedata;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.group2.foodie.model.Recipe;

public class RecipeLiveData  extends LiveData<Recipe> {
    private DatabaseReference dbRef;
    private DatabaseReference dbRefFavorite;

    public RecipeLiveData(DatabaseReference dbRef, String id) {
        this.dbRef = dbRef.child("recipes").child(id);
        this.dbRefFavorite = dbRef.child("favorites").child(FirebaseAuth.getInstance().getUid()).child(id);
        Log.e("recipecreated", "Instantiated RecipeLiveData!");
    }

    private ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Recipe recipe = snapshot.getValue(Recipe.class);
            recipe.setId(snapshot.getKey());

            dbRefFavorite.get().addOnCompleteListener(task-> {
                recipe.setFavorite(task.getResult().exists());
                setValue(recipe);
            });
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    protected void onActive() {
        super.onActive();
        dbRef.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        dbRef.removeEventListener(listener);
    }
}
