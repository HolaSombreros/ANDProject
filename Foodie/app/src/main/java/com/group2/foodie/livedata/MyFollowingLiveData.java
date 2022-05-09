package com.group2.foodie.livedata;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.group2.foodie.model.Follower;

import java.util.ArrayList;
import java.util.List;

public class MyFollowingLiveData extends LiveData<List<Follower>> {
    private DatabaseReference followingRef;
    private DatabaseReference usersRef;

    public MyFollowingLiveData(DatabaseReference dbRef) {
        followingRef = dbRef.child("followers")
                .child(FirebaseAuth.getInstance().getUid())
                .child("following");

        usersRef = dbRef.child("users");
        setValue(new ArrayList<>());
    }

    private ChildEventListener followersListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
            String uid = snapshot.getKey();
            usersRef.child(uid).get().addOnCompleteListener(task -> {
                Follower user = task.getResult().getValue(Follower.class);
                user.setId(uid);
                user.setFollows(true);
                List<Follower> following = getValue();
                if(!following.contains(user))
                    following.add(user);
                setValue(following);
            });
        }

        @Override
        public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
        }

        @Override
        public void onChildRemoved(DataSnapshot snapshot) {
            String uid = snapshot.getKey();
            usersRef.child(uid).get().addOnCompleteListener(task -> {
                Follower user = task.getResult().getValue(Follower.class);
                user.setId(uid);
                List<Follower> following = getValue();
                for (int i = 0; i < following.size(); i++) {
                    if (following.get(i).getId().equals(uid)) {
                        following.remove(i);
                        break;
                    }
                }
                setValue(following);
            });
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
        followingRef.addChildEventListener(followersListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        followingRef.removeEventListener(followersListener);
    }
}
