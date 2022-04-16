package com.group2.foodie.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.repository.UserRepository;

public class LogoutViewModel extends AndroidViewModel {
    private UserRepository repository;

    public LogoutViewModel(Application application) {
        super(application);
        repository = UserRepository.getInstance(application);
    }

    public void logout(){
        repository.logOut();
    }
}
