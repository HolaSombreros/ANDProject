package com.group2.foodie.livedata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.group2.foodie.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class FridgeIngredientsLiveData extends LiveData<List<Ingredient>> {
    private DatabaseReference dbRef;

    public FridgeIngredientsLiveData(DatabaseReference dbRef) {
        this.dbRef = dbRef;
        setValue(new ArrayList<>());
    }

    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Ingredient ingredient = snapshot.getValue(Ingredient.class);
            ingredient.setId(snapshot.getKey());

            List<Ingredient> current = getValue();
            current.add(ingredient);
            setValue(current);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Ingredient ingredient = snapshot.getValue(Ingredient.class);
            ingredient.setId(snapshot.getKey());
            List<Ingredient> current = getValue();

            for (int i = 0; i < current.size(); i++) {
                if (current.get(i).getId().equals(ingredient.getId())) {
                    current.set(i, ingredient);
                    break;
                }
            }

            setValue(current);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            List<Ingredient> current = getValue();

            for (int i = 0; i < current.size(); i++) {
                if (current.get(i).getId().equals(snapshot.getKey())) {
                    current.remove(i);
                    break;
                }
            }

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
        dbRef.orderByChild("expirationDate").addChildEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        dbRef.removeEventListener(listener);
    }
}
