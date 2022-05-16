package com.group2.foodie.view.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private FloatingActionButton editBtn;

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
        editBtn = view.findViewById(R.id.editPersonalProfile);
    }

    private void setupViews() {
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            username.setText(user.getUsername());
            password.setText(user.getPassword());
            email.setText(user.getEmail());

            if (profilePicture.getDrawable() == null) {
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/users/" + user.getEmail());
                GlideApp.with(this)
                        .load(storageRef)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(profilePicture);
            }
        });

        viewModel.getMyFollowing().observe(getViewLifecycleOwner(), following -> {
            followingTextLabel.setText(new StringBuilder().append("Following: ").append(following.size()));
        });

        viewModel.getMyFollowers().observe(getViewLifecycleOwner(), followers -> {
            followersTextLabel.setText(new StringBuilder().append("Followers: ").append(followers.size()));
        });

        viewModel.getFridge().observe(getViewLifecycleOwner(), fridge -> {
            fridgeTxt.setText(new StringBuilder().append("Fridge: ").append(fridge.size()));
        });

        viewModel.getRecipes().observe(getViewLifecycleOwner(), recipes -> {
            recipeTxt.setText(new StringBuilder().append("Recipes: ").append(recipes.size()));
        });

        recipeLayout.setOnClickListener(n -> {
            Bundle bundle = new Bundle();
            bundle.putString("recipeType", "personal");
            navController.navigate(R.id.fragment_recipes, bundle);
        });

        fridgeLayout.setOnClickListener(n -> {
            navController.navigate(R.id.fragment_fridge);
        });

        followersTextLabel.setOnClickListener(listener -> {
            Bundle bundle = new Bundle();
            bundle.putInt("followers", 0);
            navController.navigate(R.id.fragment_followingfollowers, bundle);
        });

        followingTextLabel.setOnClickListener(listener -> {
            Bundle bundle = new Bundle();
            bundle.putInt("followers", 1);
            navController.navigate(R.id.fragment_followingfollowers, bundle);
        });

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getData() != null) {
                Uri image = result.getData().getData();
                profilePicture.setImageURI(image);
            }
        });

        profilePicture.setDrawingCacheEnabled(true);
        profilePicture.buildDrawingCache();

        profilePicture.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            intent.setType("image/*");
            activityResultLauncher.launch(intent);
        });

        editBtn.setOnClickListener(v -> {
            viewModel.editUser(username.getText().toString(), email.getText().toString(), password.getText().toString());
            if (profilePicture.getDrawable() != null) {
                Bitmap bitmap = ((BitmapDrawable) profilePicture.getDrawable()).getBitmap();
                viewModel.uploadUserImage(bitmap, viewModel.getUser().getValue().getEmail()).addOnCompleteListener(listener -> {
                    Toast.makeText(getActivity(), "Details Saved successfully", Toast.LENGTH_SHORT).show();
                });
            } else
                Toast.makeText(getActivity(), "Details Saved successfully without picture", Toast.LENGTH_SHORT).show();
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
