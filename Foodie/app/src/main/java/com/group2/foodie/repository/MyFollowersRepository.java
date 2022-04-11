package com.group2.foodie.repository;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.foodie.livedata.MyFollowersLiveData;
import com.group2.foodie.livedata.MyFollowingLiveData;
import com.group2.foodie.model.Follower;

import java.util.List;

public class MyFollowersRepository {
    private static MyFollowersRepository instance;
    private DatabaseReference dbRef;

    private MyFollowersLiveData myFollowersLiveData;
    private MyFollowingLiveData myFollowingLiveData;

    private MyFollowersRepository() {
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    public static MyFollowersRepository getInstance() {
        if (instance == null) {
            instance = new MyFollowersRepository();
        }

        return instance;
    }

    public void init() {
        myFollowersLiveData = new MyFollowersLiveData(dbRef);
        myFollowingLiveData = new MyFollowingLiveData(dbRef);
    }

    public LiveData<List<Follower>> getMyFollowers() {
        return myFollowersLiveData;
    }

    public LiveData<List<Follower>> getMyFollowing() {
        return myFollowingLiveData;
    }
}
