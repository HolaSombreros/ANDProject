package com.group2.foodie.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.group2.foodie.model.User;
import com.group2.foodie.repository.UserRepository;

public class RegisterViewModel extends AndroidViewModel {
    private UserRepository repository;

    public RegisterViewModel(Application app) {
        super(app);
        this.repository = UserRepository.getInstance();
    }

    public void register(String username, String email, String password) throws Exception {
        repository.addUser(new User(username, email, password));
    }

    //TODO: validate all fields
    public boolean validatePassword(String password, String repeatedPassword){
        return password.trim().equals(repeatedPassword.trim());
    }

    public void reset(){

    }
}
