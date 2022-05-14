package com.group2.foodie.repository;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.group2.foodie.livedata.FirebaseUserLiveData;
import com.group2.foodie.livedata.UserLiveData;
import com.group2.foodie.model.User;

import java.io.ByteArrayOutputStream;

public class UserRepository {
    private static UserRepository instance;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private UserLiveData user;
    private UserLiveData visitUser;
    private FirebaseUserLiveData currentFirebaseUser;
    private MutableLiveData<String> errorMessage;

    private UserRepository() {
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
        currentFirebaseUser = new FirebaseUserLiveData();
        errorMessage = new MutableLiveData<>();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }

        return instance;
    }

    public void initCurrentUser() {
        user = new UserLiveData(dbRef.child("users").child(FirebaseAuth.getInstance().getUid()));
    }

    public void initVisitUser(String userUid) {
        visitUser = new UserLiveData(dbRef.child("users").child(userUid));
    }

    public void addUser(User user) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        dbRef.child("users").child(FirebaseAuth.getInstance().getUid()).setValue(user);

                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(user.getUsername())
                                .build();

                        currentFirebaseUser.getValue().updateProfile(request);
                    }
                });
    }

    public void updateUser(User user) {
        String userId = FirebaseAuth.getInstance().getUid();
        dbRef.child("users").child(userId).setValue(user);
    }

    public void logIn(String email, String password) {
        if (email == null || email.isEmpty()) {
            errorMessage.setValue("Please enter an email address");
            errorMessage.setValue(null);
        } else if (password == null || password.isEmpty()) {
            errorMessage.setValue("Please enter a password");
            errorMessage.setValue(null);
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(result -> {
                        if (!result.isSuccessful()) {
                            errorMessage.setValue("Invalid email/password combination");
                            errorMessage.setValue(null);
                        }
                    });
        }
    }

    public UploadTask uploadUserImage(Bitmap bitmap, String path){
        Log.e("profile picture", "Before the storage ref");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/users/" + path);
        Log.e("profile picture", "After storage ref");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        return storageRef.putBytes(data);
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
