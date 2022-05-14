package com.group2.foodie.viewmodel;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.storage.UploadTask;
import com.group2.foodie.model.Follower;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.model.User;
import com.group2.foodie.repository.FridgeRepository;
import com.group2.foodie.repository.MyFollowersRepository;
import com.group2.foodie.repository.RecipeRepository;
import com.group2.foodie.repository.UserRepository;

import java.util.List;

public class PersonalProfileViewModel extends ViewModel {
    private UserRepository userRepository;
    private MyFollowersRepository followersRepository;
    private RecipeRepository recipeRepository;
    private FridgeRepository fridgeRepository;
    private MutableLiveData<String> errorMessage;

    public PersonalProfileViewModel() {
        userRepository = UserRepository.getInstance();
        followersRepository = MyFollowersRepository.getInstance();
        recipeRepository = RecipeRepository.getInstance();
        fridgeRepository = FridgeRepository.getInstance();
        errorMessage = new MutableLiveData<>();

    }

    public void init(){
        userRepository.initCurrentUser();
        followersRepository.init();
        recipeRepository.init();
        fridgeRepository.init("-1");
    }

    public LiveData<User> getUser(){
        return userRepository.getCurrentUser();
    }

    public LiveData<List<Follower>> getMyFollowing() {
        return followersRepository.getMyFollowing();
    }

    public LiveData<List<Follower>> getMyFollowers() {
        return followersRepository.getMyFollowers();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return recipeRepository.getPersonalRecipes();
    }

    public LiveData<List<Ingredient>> getFridge(){
        return fridgeRepository.getFridgeIngredients();
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }


    public boolean editUser(String username,String email, String password ){
        if(!isValid(username, email, password))
            return false;
        userRepository.updateUser(new User(username, email, password));
        return true;
    }

    public UploadTask uploadUserImage(Bitmap bitmap, String path){
        Log.e("profile pic", "view model");
        return userRepository.uploadUserImage(bitmap, path);
    }

    public boolean isValid(String username, String email, String password){
        if (username == null || username.isEmpty()) {
            return false;
        }
        if(email == null || email.isEmpty()) {
            return false;
        }
        if(password == null || password.isEmpty()) {
            return false;
        }
        return true;
    }
}
