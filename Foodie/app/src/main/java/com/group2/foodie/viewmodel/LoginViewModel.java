package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> errorMessage;

    public LoginViewModel() {
        errorMessage = new MutableLiveData<>();
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
