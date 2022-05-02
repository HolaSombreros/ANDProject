package com.group2.foodie.view.fragment;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.group2.foodie.R;

import com.group2.foodie.util.GlideApp;
import com.group2.foodie.viewmodel.PersonalProfileViewModel;


public class PersonalProfileFragment extends Fragment {
    private NavController navController;
    private PersonalProfileViewModel viewModel;
    private TextInputEditText username;
    private ImageView profilePicture;
    private TextView followersTextLabel;
    private TextView followingTextLabel;
    private LinearLayout recipeLayout;
    private LinearLayout fridgeLayout;
    private TextView recipeTxt;
    private TextView fridgeTxt;
    private TextInputEditText password;
    private TextInputEditText email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(PersonalProfileViewModel.class);
        viewModel.init();
        initializeViews(view);
        setupViews(view);
    }

    private void initializeViews(View view) {
        navController = Navigation.findNavController(view);
        username = view.findViewById(R.id.inputUsernamePersonal);
        profilePicture = view.findViewById(R.id.userProfilePicture);
        followersTextLabel = view.findViewById(R.id.followersTxt);
        followingTextLabel = view.findViewById(R.id.followingTxt);
        fridgeLayout = view.findViewById(R.id.fridge_personal_details);
        recipeLayout = view.findViewById(R.id.recipes_personal_display);
        fridgeTxt = view.findViewById(R.id.fridgeTxt);
        recipeTxt = view.findViewById(R.id.recipeTxt);
        password = view.findViewById(R.id.inputPasswordPersonal);
        email = view.findViewById(R.id.inputEmailPersonal);
    }

    //TODO: edit profile
    private void setupViews(View view) {
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            username.setText(user.getUsername());
            password.setText(user.getPassword());
            email.setText(user.getEmail());

            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/users/" + user.getId() + ".jpg");
            GlideApp.with(view).load(storageRef).into(profilePicture);
        });

        viewModel.getMyFollowing().observe(getViewLifecycleOwner(), following -> {
            followingTextLabel.setText(new StringBuilder().append("Following: ").append(following.size()));
        });

        viewModel.getMyFollowers().observe(getViewLifecycleOwner(), followers -> {
            followersTextLabel.setText(new StringBuilder().append("Followers: ").append(followers.size()));
        });

        viewModel.getFridge().observe(getViewLifecycleOwner(), fridge ->{
            fridgeTxt.setText(new StringBuilder().append("Fridge: ").append(fridge.size()));
        });

        viewModel.getRecipes().observe(getViewLifecycleOwner(), recipes -> {
            recipeTxt.setText(new StringBuilder().append("Recipes: ").append(recipes.size()));
        });

        recipeLayout.setOnClickListener(n->{
            navController.navigate(R.id.fragment_recipes);
        });

        fridgeLayout.setOnClickListener(n->{
            navController.navigate(R.id.fragment_fridge);
        });

        followersTextLabel.setOnClickListener(listener -> {
            navController.navigate(R.id.fragment_followingfollowers);
        });

        followersTextLabel.setOnClickListener(listener -> {
            navController.navigate(R.id.fragment_followingfollowers);
        });

        followingTextLabel.setOnClickListener(listener -> {
            navController.navigate(R.id.fragment_followingfollowers);
        });

        followingTextLabel.setOnClickListener(listener -> {
            navController.navigate(R.id.fragment_followingfollowers);
        });
    }
}
