package com.group2.foodie.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.foodie.livedata.UserLiveData;
import com.group2.foodie.model.User;
import com.group2.foodie.util.AwaitHelper;

public class UserRepository {
    private static UserRepository instance;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private FirebaseAuth auth;
    private UserLiveData user;
    private UserLiveData visitUser;
    private static Object lock = new Object();

    private UserRepository() {
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance(database.getApp());
        dbRef = database.getReference();
    }

    public static UserRepository getInstance() {
        if(instance ==null) {
                if(instance == null)
                    instance = new UserRepository();
        }
        return instance;
    }

    public void initCurrentUser(){
        Log.e("repo init", dbRef.child(auth.getUid()).getKey());
        user = new UserLiveData(dbRef.child("users").child(auth.getUid()));

    }

    public void initVisitUser(String userUid){
        visitUser = new UserLiveData(dbRef.child("users").child(userUid));
    }

    //TODO: add user to database
    public void addUser(User user) throws Exception {
        AuthResult result = AwaitHelper.await(auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()));
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(user.getUsername()).build();
        DatabaseReference reference = dbRef.child("users").push();
        dbRef.child("users").child(result.getUser().getUid()).setValue(user);

    }

    //TODO: update method
    public void updateUser(User user){

    }

    public void logOut(){
        auth.signOut();
    }


    public LiveData<User> getCurrentUser() {
        return user;
    }

    public LiveData<User> getVisitingUser(String uid){
        return visitUser;
    }

}
