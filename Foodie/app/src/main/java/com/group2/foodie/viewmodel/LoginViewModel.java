package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.group2.foodie.repository.UserRepository;

public class LoginViewModel extends ViewModel {
    private UserRepository repository;

    public LoginViewModel() {
        repository = UserRepository.getInstance();
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
