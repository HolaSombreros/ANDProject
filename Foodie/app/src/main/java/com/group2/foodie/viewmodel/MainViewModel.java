package com.group2.foodie.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.group2.foodie.repository.UserRepository;

public class MainViewModel extends AndroidViewModel {
    private UserRepository repository;

    public MainViewModel(Application application) {
        super(application);
        repository = UserRepository.getInstance(application);
    }

    public LiveData<FirebaseUser> getCurrentFirebaseUser() {
        return repository.getCurrentFirebaseUser();
    }
}