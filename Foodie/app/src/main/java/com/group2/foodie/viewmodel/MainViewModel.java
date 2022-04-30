package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.repository.FridgeRepository;
import com.group2.foodie.repository.UserRepository;

import java.util.List;

public class MainViewModel extends ViewModel {
    private UserRepository repository;

    public MainViewModel() {
        super();
        repository = UserRepository.getInstance();
    }

    public LiveData<FirebaseUser> getCurrentFirebaseUser() {
        return repository.getCurrentFirebaseUser();
    }

    public LiveData<List<Ingredient>> getIngredients() {
        return FridgeRepository.getInstance().getExpiredIngredients();
    }
}
