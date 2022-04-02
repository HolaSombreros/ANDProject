package com.group2.foodie.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.group2.foodie.R;
import com.group2.foodie.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {
    private LoginViewModel viewModel;
    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);

        initializeViews(view);

    }

    private void initializeViews(View view) {
       usernameInput = view.findViewById(R.id.inputUsername);
       passwordInput = view.findViewById(R.id.txtPassword);
       loginButton = view.findViewById(R.id.login);

    }

}
