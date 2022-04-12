package com.group2.foodie.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.foodie.model.User;

public class UserRepository {
    private static UserRepository instance;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private FirebaseAuth auth;

    private UserRepository() {
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance(database.getApp());
        dbRef = database.getReference("users");
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }

        return instance;
    }
    public void addUser(User user) {
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword());
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(user.getUsername()).build();


    }

    //TODO: upodate method
    public void updateUser(User user){

    }

    public void logOut(){
        auth.signOut();
    }


    //TODO: figure this out
    public User getCurrentUser(){
//        return auth.getCurrentUser();
        return null;
    }
}
