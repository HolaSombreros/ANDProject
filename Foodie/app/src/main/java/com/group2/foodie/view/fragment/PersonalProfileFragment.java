package com.group2.foodie.view.fragment;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.group2.foodie.R;

import com.group2.foodie.viewmodel.PersonalProfileViewModel;


public class PersonalProfileFragment extends Fragment {
    private NavController navController;
    private PersonalProfileViewModel viewModel;
    private TextView username;
    private TextView followersTxt;
    private TextView followingTxt;
    private LinearLayout recipeLayout;
    private LinearLayout fridgeLayout;
    private TextView recipeTxt;
    private TextView fridgeTxt;
    private TextView passwordTxt;
    private TextView emailTxt;

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
        fridgeLayout = view.findViewById(R.id.fridge_personal_details);
        recipeLayout = view.findViewById(R.id.recipes_personal_display);
        fridgeTxt = view.findViewById(R.id.fridgeDisplay);
        recipeTxt = view.findViewById(R.id.recipeDisplay);
        passwordTxt = view.findViewById(R.id.passwordDisplay);
        emailTxt = view.findViewById(R.id.emailDisplay);
    }

    //TODO: find a way to deserialize recipes
    private void setupViews(){
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            username.setText(user.getUsername());
            passwordTxt.setText(user.getPassword());
            emailTxt.setText(user.getEmail());
        });

        viewModel.getMyFollowing().observe(getViewLifecycleOwner(), following -> {
                followingTxt.setText(String.valueOf(following.size()));
        });

        viewModel.getMyFollowers().observe(getViewLifecycleOwner(), followers -> {
            followersTxt.setText(String.valueOf(followers.size()));
        });

        viewModel.getFridge().observe(getViewLifecycleOwner(), fridge ->{
            fridgeTxt.setText(String.valueOf(fridge.size()));
        });

        viewModel.getRecipes().observe(getViewLifecycleOwner(), recipes -> {
            recipeTxt.setText(String.valueOf(recipes.size()));
        });

        recipeLayout.setOnClickListener(n->{
            navController.navigate(R.id.fragment_personal_recipes);
        });

        fridgeLayout.setOnClickListener(n->{
            navController.navigate(R.id.fragment_fridge);
        });

    }
}
