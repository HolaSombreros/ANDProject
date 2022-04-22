package com.group2.foodie.livedata;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserLiveData extends LiveData<User> {
    DatabaseReference dbRef;

    public UserLiveData(DatabaseReference dbRef) {
        this.dbRef = dbRef;
        Log.e("user retrieved", "Instantiated UserLiveData!");
    }

    private ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            User user = snapshot.getValue(User.class);
            setValue(user);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Log.d("user live data", error.getMessage());
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
