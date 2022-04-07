package com.group2.foodie.livedata;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.group2.foodie.model.Recipe;

public class RecipeLiveData  extends LiveData<Recipe> {
    private DatabaseReference dbRef;

    public RecipeLiveData(DatabaseReference dbRef) {
        this.dbRef = dbRef;
        Log.e("recipecreated", "Instantiated RecipeLiveData!");
    }

    private ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Recipe recipe = snapshot.getValue(Recipe.class);
            setValue(recipe);
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
