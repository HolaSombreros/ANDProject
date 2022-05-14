package com.group2.foodie.viewmodel;

import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.User;
import com.group2.foodie.repository.UserRepository;

public class RegisterViewModel extends ViewModel {
    private UserRepository repository;

    public RegisterViewModel() {
        this.repository = UserRepository.getInstance();
    }

    public void register(String username, String email, String password) throws Exception {
        repository.addUser(new User(username, email, password));
    }

    public boolean validatePassword(String password, String repeatedPassword){
        return password.trim().equals(repeatedPassword.trim());
    }

    public void reset(){

    }
}
