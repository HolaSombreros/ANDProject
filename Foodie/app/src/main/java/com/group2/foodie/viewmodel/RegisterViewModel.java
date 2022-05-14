package com.group2.foodie.viewmodel;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.firebase.storage.UploadTask;
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
    public UploadTask uploadUserImage(Bitmap bitmap, String path){
        return repository.uploadUserImage(bitmap, path);
    }

    public void reset(){

    }
}
