package com.group2.foodie.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.group2.foodie.R;
import com.group2.foodie.model.User;
import com.group2.foodie.viewmodel.PersonalProfileViewModel;
import com.group2.foodie.viewmodel.PersonalRecipesViewModel;

public class PersonalProfileFragment extends Fragment {
    private NavController navController;
    private PersonalProfileViewModel viewModel;
    private TextView username;
    private TextView followersTxt;
    private TextView followingTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_profile, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(PersonalProfileViewModel.class);
        viewModel.init();
        initializeViews(view);
        setupViews();
    }
    private void initializeViews(View view) {
        navController = Navigation.findNavController(view);
        username = view.findViewById(R.id.usernameTextView);
        followersTxt = view.findViewById(R.id.followersDisplay);
        followingTxt = view.findViewById(R.id.followingDisplay);
    }

    private void setupViews(){
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            username.setText(user.getUsername());
        });

        viewModel.getMyFollowing().observe(getViewLifecycleOwner(), following -> {
                followingTxt.setText(String.valueOf(following.size()));
        });

        viewModel.getMyFollowers().observe(getViewLifecycleOwner(), followers -> {
            followersTxt.setText(String.valueOf(followers.size()));
        });

    }
}
