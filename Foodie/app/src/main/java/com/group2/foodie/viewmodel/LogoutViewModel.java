package com.group2.foodie.viewmodel;

import androidx.lifecycle.ViewModel;

import com.group2.foodie.repository.UserRepository;

public class LogoutViewModel extends ViewModel {
    private UserRepository repository;

    public LogoutViewModel() {
        repository = UserRepository.getInstance();
    }

    public void logout(){
        repository.logOut();
    }
}
