package com.group2.foodie.viewmodel;

import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.group2.foodie.livedata.UserLiveData;
import com.group2.foodie.model.Follower;
import com.group2.foodie.model.User;
import com.group2.foodie.repository.MyFollowersRepository;
import com.group2.foodie.repository.UserRepository;

import java.util.List;

public class PersonalProfileViewModel extends ViewModel {
    private UserRepository userRepository;
    private MyFollowersRepository followersRepository;

    public PersonalProfileViewModel() {
        userRepository = UserRepository.getInstance();
        followersRepository = MyFollowersRepository.getInstance();

    }

    public void init(){
        userRepository.init();
        followersRepository.init();
    }

    public LiveData<User> getUser(){
        Log.e("repository", String.valueOf(userRepository.getUser()==null));
        return userRepository.getUser();
    }
    public LiveData<List<Follower>> getMyFollowing() {
        return followersRepository.getMyFollowing();
    }

    public LiveData<List<Follower>> getMyFollowers() {
        return followersRepository.getMyFollowers();
    }
}
