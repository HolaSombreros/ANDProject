package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Follower;
import com.group2.foodie.repository.MyFollowersRepository;

import java.util.List;

public class MyFollowingViewModel extends ViewModel {
    private MyFollowersRepository repository;

    public MyFollowingViewModel() {
        repository = MyFollowersRepository.getInstance();
    }

    public void init() {
        repository.init();
    }

    public LiveData<List<Follower>> getMyFollowing() {
        return repository.getMyFollowing();
    }

    public void unfollowUser(Follower user) {
        repository.unfollowUser(user.getId());
    }
}
