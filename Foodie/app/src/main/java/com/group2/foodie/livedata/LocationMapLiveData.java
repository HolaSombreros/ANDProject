package com.group2.foodie.livedata;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationMapLiveData extends LiveData<List<Location>> {
    private DatabaseReference dbRef;

    public LocationMapLiveData(DatabaseReference dbRef) {
        this.dbRef = dbRef;
        setValue(new ArrayList<>());
    }
    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Location location = snapshot.getValue(Location.class);
            List<Location> current = getValue();

            current.add(location);
            setValue(current);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            Location location = snapshot.getValue(Location.class);

            List<Location> current = getValue();
            current.remove(location);
            setValue(current);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
