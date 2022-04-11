package com.group2.foodie.livedata;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.group2.foodie.model.Follower;

import java.util.ArrayList;
import java.util.List;

public class MyFollowersLiveData extends LiveData<List<Follower>> {
    private DatabaseReference followersRef;
    private DatabaseReference usersRef;
    private DatabaseReference followingRef;

    public MyFollowersLiveData(DatabaseReference dbRef) {
        followersRef = dbRef.child("followers")
                .child(FirebaseAuth.getInstance().getUid())
                .child("followers");

        usersRef = dbRef.child("users");

        followingRef = dbRef.child("followers")
                .child(FirebaseAuth.getInstance().getUid())
                .child("following");

        setValue(new ArrayList<>());
    }

    private ChildEventListener followersListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
            String uid = snapshot.getKey();
            usersRef.child(uid).get().addOnCompleteListener(task -> {
                Follower follower = task.getResult().getValue(Follower.class);

                followingRef.child(uid).get().addOnCompleteListener(followTask -> {
                    follower.setFollows(followTask.getResult().exists());
                    Log.d("foodiefollower", "Set follows " + follower.isFollowed());
                });

                List<Follower> followers = getValue();
                followers.add(follower);
                setValue(followers);
            });
        }

        @Override
        public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
        }

        @Override
        public void onChildRemoved(DataSnapshot snapshot) {
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
        followersRef.addChildEventListener(followersListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        followersRef.removeEventListener(followersListener);
    }
}
