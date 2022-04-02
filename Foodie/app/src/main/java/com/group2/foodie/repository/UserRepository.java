package com.group2.foodie.repository;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.foodie.model.User;

public class UserRepository {
    private static UserRepository instance;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    private UserRepository() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("users");
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }

        return instance;
    }

    public void addUser(User user) {

    }
}
