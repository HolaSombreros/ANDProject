package com.group2.foodie.view.fragment;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.group2.foodie.R;
import com.group2.foodie.list.RecipeAdapter;
import com.group2.foodie.model.Follower;
import com.group2.foodie.util.GlideApp;
import com.group2.foodie.viewmodel.UserProfileViewModel;

public class UserProfileFragment extends Fragment {
    private ImageView profilePicture;
    private TextView username;
    private Button follow;
    private EditText search;
    private RecyclerView publicRecipes;
    private UserProfileViewModel viewModel;
    private RecipeAdapter adapter;
    private NavController navController;
    private Follower follower;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(UserProfileViewModel.class);
        viewModel.init(getArguments().getString("profileId"));
        initializeViews(view);
        setupViews(view);
    }

    private void initializeViews(View view) {
        profilePicture = view.findViewById(R.id.userProfilePicture);
        username = view.findViewById(R.id.usernameUser);
        follow = view.findViewById(R.id.followUserButton);
        search = view.findViewById(R.id.recipes_searchText_user);
        publicRecipes = view.findViewById(R.id.recipes_recycleView_user);
        navController = NavHostFragment.findNavController(this);
        follower = new Follower();
    }

    private void setupViews(View view) {
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            username.setText(user.getUsername());
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/users/" + user.getEmail());
            GlideApp.with(view).load(storageRef).into(profilePicture);
            isFollowing();
            if(!follower.isFollowed()){
                follower.setEmail(user.getEmail());
                follower.setId(viewModel.getUid());
                follower.setUsername(user.getUsername());
            }
        });
        viewModel.following().observe(getViewLifecycleOwner(), v->{
            if(follower.isFollowed()) {
                follow.setText("Following");
                follow.setBackgroundColor(Color.parseColor("#00BB00"));
            }
            else {
                follow.setText("Follow");
                follow.setBackgroundColor(Color.parseColor("#03A9F4"));
            }
        });

       follow.setOnClickListener( v ->{
           viewModel.follow(follower);
       });

        publicRecipes.hasFixedSize();
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            publicRecipes.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            publicRecipes.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }
        adapter = new RecipeAdapter();
        viewModel.getRecipes().observe(getViewLifecycleOwner(), recipes -> {
            adapter.setRecipes(recipes);
        });
        publicRecipes.setAdapter(adapter);

        adapter.setOnClickListener(recipe -> {
            Bundle bundle = new Bundle();
            bundle.putString("recipeId", recipe.getId());
            bundle.putString("publisherId", recipe.getPublisherId());
            navController.navigate(R.id.fragment_view_recipe, bundle);
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.setRecipes( search.getText().toString());
            }
        });

    }

    private void isFollowing(){
        viewModel.following().observe(getViewLifecycleOwner(), followers -> {
            for(Follower f: followers)
                if (f.getId().equals(viewModel.getUid())) {
                    follower = f;
                    return;
                }
            follower.setFollows(false);
        });
    }


}
