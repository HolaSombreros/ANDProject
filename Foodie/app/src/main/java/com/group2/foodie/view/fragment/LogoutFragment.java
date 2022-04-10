package com.group2.foodie.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.group2.foodie.R;
import com.group2.foodie.viewmodel.LogoutViewModel;

public class LogoutFragment extends Fragment {
    private LogoutViewModel viewModel;
    private NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logout, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(LogoutViewModel.class);
        initializeViews(view);
        logout();
    }
    private void initializeViews(View view) {
        navController = Navigation.findNavController(view);
        navController.navigate(R.id.fragment_login);
    }

    private void logout(){
        viewModel.logout();
    }
}
