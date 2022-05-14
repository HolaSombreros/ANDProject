package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Follower;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.model.User;
import com.group2.foodie.repository.MyFollowersRepository;
import com.group2.foodie.repository.RecipeRepository;
import com.group2.foodie.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserProfileViewModel extends ViewModel {
    private UserRepository userRepository;
    private RecipeRepository recipeRepository;
    private String uid;
    private MutableLiveData<List<Recipe>> filteredList;
    private MyFollowersRepository followersRepository;

    public UserProfileViewModel() {
        userRepository = UserRepository.getInstance();
        recipeRepository = RecipeRepository.getInstance();
        filteredList = new MutableLiveData<>();
        followersRepository = MyFollowersRepository.getInstance();
    }

    public void init(String uid){
        this.uid = uid;
        userRepository.initVisitUser(uid);
        recipeRepository.init(uid);
        followersRepository.init();
    }
    public LiveData<List<Recipe>> getRecipes(){
        return recipeRepository.getPublicPersonalRecipes();
    }

    public LiveData<User> getUser(){
        return userRepository.getVisitingUser(uid);
    }

    public String getUid() {
        return uid;
    }

    public void setRecipes(String text) {
        filteredList.setValue(filterByName(text).getValue());
    }

    public LiveData<List<Recipe>> filterByName(String text) {
        List<Recipe> filteredRecipes = new ArrayList<>();
        if (text.isEmpty()) {
            return getRecipes();
        }
        else {
            for (Recipe recipe1 : getRecipes().getValue())
                if (recipe1.getName().toLowerCase().contains(text.toLowerCase()))
                    filteredRecipes.add(recipe1);
        }
        return new MutableLiveData<>(filteredRecipes);
    }

    public LiveData<List<Follower>> following(){
        return followersRepository.getMyFollowing();
    }

    public void follow(Follower follower){
        if (follower.isFollowed()) {
            followersRepository.unfollowUser(uid);
        } else {
            followersRepository.followUser(uid);
        }
    }
}
