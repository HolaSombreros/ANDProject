package com.group2.foodie.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.group2.foodie.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private UserRepository repository;

    public LoginViewModel(Application application) {
        super(application);
        repository = UserRepository.getInstance(application);
    }

    public LiveData<FirebaseUser> getCurrentFirebaseUser() {
        return repository.getCurrentFirebaseUser();
    }

    public LiveData<String> getErrorMessage() {
        return repository.getErrorMessage();
    }

    public void attemptLogin(String email, String password) {
        repository.logIn(email, password);
    }
}
