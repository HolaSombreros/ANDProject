package com.group2.foodie.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.group2.foodie.model.Ingredient;

public class FridgeIngredientLiveData  extends LiveData<Ingredient> {
    private DatabaseReference dbRef;

    public FridgeIngredientLiveData(DatabaseReference dbRef) {
        this.dbRef = dbRef;
    }

    private ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Ingredient ingredient = snapshot.getValue(Ingredient.class);
            ingredient.setId(snapshot.getKey());

            setValue(ingredient);
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
