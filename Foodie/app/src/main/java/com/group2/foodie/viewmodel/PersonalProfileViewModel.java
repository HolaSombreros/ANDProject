package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

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

    public PersonalProfileViewModel() {
        userRepository = UserRepository.getInstance();
        followersRepository = MyFollowersRepository.getInstance();
        recipeRepository = RecipeRepository.getInstance();
        fridgeRepository = FridgeRepository.getInstance();
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
}
