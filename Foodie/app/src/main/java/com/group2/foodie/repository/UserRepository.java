package com.group2.foodie.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.foodie.livedata.FirebaseUserLiveData;
import com.group2.foodie.livedata.UserLiveData;
import com.group2.foodie.model.User;
import com.group2.foodie.util.AwaitHelper;

public class UserRepository {
    private static UserRepository instance;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private UserLiveData user;
    private UserLiveData visitUser;
    private FirebaseUserLiveData currentFirebaseUser;
    private Application application;
    private MutableLiveData<String> errorMessage;

    private UserRepository(Application application) {
        this.application = application;
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
        currentFirebaseUser = new FirebaseUserLiveData();
        errorMessage = new MutableLiveData<>();
    }

    public static UserRepository getInstance(Application application) {
        if (instance == null) {
            instance = new UserRepository(application);
        }

        return instance;
    }

    public void initCurrentUser() {
        user = new UserLiveData(dbRef.child("users").child(FirebaseAuth.getInstance().getUid()));

    }

    public void initVisitUser(String userUid) {
        visitUser = new UserLiveData(dbRef.child("users").child(userUid));
    }

    //TODO: add user to database
    public void addUser(User user) throws Exception {
        AuthResult result = AwaitHelper.await(FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(), user.getPassword()));
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(user.getUsername()).build();
        DatabaseReference reference = dbRef.child("users").push();
        dbRef.child("users").child(result.getUser().getUid()).setValue(user);

    }

    //TODO: update method
    public void updateUser(User user) {

    }

    // TODO - Ask Kasper about application.getMainExecutor(). I dunno how else to do it :<
    //  Also had to suppress it for it to not error out...
    @SuppressLint("NewApi")
    public void logIn(String email, String password) {
        if (email == null || email.isEmpty()) {
            errorMessage.setValue("Please enter an email address");
        } else if (password == null || password.isEmpty()) {
            errorMessage.setValue("Please enter a password");
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnFailureListener(application.getMainExecutor(), task -> {
                        errorMessage.setValue("Invalid email/password combination");
                    });
        }
    }

    public void logOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public LiveData<User> getCurrentUser() {
        return user;
    }

    public LiveData<User> getVisitingUser(String uid) {
        return visitUser;
    }

    public FirebaseUserLiveData getCurrentFirebaseUser() {
        return currentFirebaseUser;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
