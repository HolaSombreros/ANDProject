package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.User;
import com.group2.foodie.repository.UserRepository;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> errorMessage;
    private UserRepository repository;

    public LoginViewModel() {
        errorMessage = new MutableLiveData<>();
        repository = UserRepository.getInstance();
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public boolean login(String email, String password) {
        if (email == null || email.isEmpty()) {
            errorMessage.setValue("Please enter an email address");
            return false;
        }

        if (password == null || password.isEmpty()) {
            errorMessage.setValue("Please enter a password");
            return false;
        }

        return true;
    }
}
