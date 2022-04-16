package com.group2.foodie.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.foodie.R;
import com.group2.foodie.list.MyFollowersAdapter;
import com.group2.foodie.viewmodel.MyFollowingViewModel;

public class MyFollowingFragment extends Fragment {
    private MyFollowingViewModel viewModel;
    private RecyclerView recyclerView;
    private MyFollowersAdapter adapter;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_followers, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(MyFollowingViewModel.class);
        viewModel.init();

        findViews(view);
        setupViews();
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.myFollowers_recyclerView);
        navController = NavHostFragment.findNavController(this);
    }

    private void setupViews() {
        adapter = new MyFollowersAdapter();
        viewModel.getMyFollowing().observe(getViewLifecycleOwner(), followers -> adapter.setFollowers(followers));

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(user -> {
//            Bundle bundle = new Bundle();
//                        bundle.putString("user", user.getEmail());
//             TODO - Navigate to user's profile?
//                        navController.navigate(R.id.fragment_personal_profile);
        });

        adapter.setOnRemoveListener(user -> {
//             TODO - Unfollow user
        });
    }
}
