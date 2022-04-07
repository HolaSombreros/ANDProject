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
import com.group2.foodie.model.FridgeIngredient;

import java.util.ArrayList;
import java.util.List;

public class FridgeIngredientsLiveData extends LiveData<List<FridgeIngredient>> {
    private DatabaseReference dbRef;

    public FridgeIngredientsLiveData(DatabaseReference dbRef) {
        this.dbRef = dbRef.child("fridge").child(FirebaseAuth.getInstance().getUid());
        setValue(new ArrayList<>());
    }

    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            FridgeIngredient ingredient = snapshot.getValue(FridgeIngredient.class);
            ingredient.setName(snapshot.getKey());

            List<FridgeIngredient> current = getValue();
            current.add(ingredient);
            setValue(current);

            Log.d("fridgeingredient", ingredient.toString());
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            FridgeIngredient ingredient = snapshot.getValue(FridgeIngredient.class);

            List<FridgeIngredient> current = getValue();
            current.remove(ingredient);
            setValue(current);
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
        dbRef.addChildEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        dbRef.removeEventListener(listener);
    }
}
