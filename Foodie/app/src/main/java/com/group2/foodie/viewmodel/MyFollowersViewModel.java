package com.group2.foodie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.group2.foodie.model.Follower;
import com.group2.foodie.repository.MyFollowersRepository;

import java.util.List;

public class MyFollowersViewModel extends ViewModel {
    private MyFollowersRepository repository;

    public MyFollowersViewModel() {
        repository = MyFollowersRepository.getInstance();
    }

    public void init() {
        repository.init();
    }

    public LiveData<List<Follower>> getMyFollowers() {
        return repository.getMyFollowers();
    }

    public void toggleFollowUser(Follower user) {
        if (user.isFollowed()) {
            repository.unfollowUser(user.getId());
        } else {
            repository.followUser(user.getId());
        }
    }
}
