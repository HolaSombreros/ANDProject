package com.group2.foodie.viewmodel;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.group2.foodie.model.User;
import com.group2.foodie.repository.UserRepository;

public class PersonalProfileViewModel extends ViewModel {
    private UserRepository userRepository;

    public PersonalProfileViewModel() {
        userRepository = UserRepository.getInstance();
    }

    public User getUser(){
       return userRepository.getCurrentUser();
    }
}
