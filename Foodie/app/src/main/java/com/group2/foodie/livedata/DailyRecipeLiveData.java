package com.group2.foodie.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.group2.foodie.dailyrecipe.DailyRecipe;

public class DailyRecipeLiveData extends LiveData<DailyRecipe> {
    private DatabaseReference dbRef;

    public DailyRecipeLiveData(DatabaseReference dbRef) {
        this.dbRef = dbRef.child("dailyrecipe");
    }

    private ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            DailyRecipe dailyRecipe = snapshot.getValue(DailyRecipe.class);
            setValue(dailyRecipe);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    protected void onActive() {
        super.onActive();
        dbRef.addValueEventListener(eventListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        dbRef.removeEventListener(eventListener);
    }
}
