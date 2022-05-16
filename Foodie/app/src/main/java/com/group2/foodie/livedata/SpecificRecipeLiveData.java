package com.group2.foodie.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.model.User;

public class SpecificRecipeLiveData extends LiveData<Recipe> {
    private final DatabaseReference dbRef;
    private final DatabaseReference dbRefFavorite;
    private final DatabaseReference userRef;

    public SpecificRecipeLiveData(DatabaseReference dbRef, String publisherId, String recipeId) {
        this.dbRef = dbRef.child("personalrecipes").child(publisherId).child(recipeId);
        this.dbRefFavorite = dbRef.child("favorites").child(FirebaseAuth.getInstance().getUid()).child(recipeId);
        this.userRef = dbRef.child("users");
    }

    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Recipe recipe = snapshot.getValue(Recipe.class);
            recipe.setId(snapshot.getKey());

            userRef.child(recipe.getPublisherId()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    User publisher = task.getResult().getValue(User.class);
                    recipe.setPublisherUsername(publisher.getUsername());

                    dbRefFavorite.get().addOnCompleteListener(task2 -> {
                        recipe.setFavorite(task2.getResult().exists());
                        setValue(recipe);
                    });
                }
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
