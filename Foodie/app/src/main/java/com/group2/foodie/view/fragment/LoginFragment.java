package com.group2.foodie.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.google.android.material.textfield.TextInputEditText;
import com.group2.foodie.R;
import com.group2.foodie.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {
    private LoginViewModel viewModel;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private Button loginButton;
    private TextView registerButton;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        navController = Navigation.findNavController(view);

        viewModel.getCurrentFirebaseUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                Bundle bundle = new Bundle();
                bundle.putString("recipeType", "personal");
                navController.navigate(R.id.fragment_recipes, bundle);
            }
        });

        initializeViews(view);
        setupViews();
    }

    private void initializeViews(View view) {
       emailInput = view.findViewById(R.id.login_emailInput);
       passwordInput = view.findViewById(R.id.login_passwordInput);
       loginButton = view.findViewById(R.id.login_loginBtn);
       registerButton = view.findViewById(R.id.login_registerBtn);
    }

    private void setupViews() {
        loginButton.setOnClickListener(v -> {
            viewModel.attemptLogin(emailInput.getText().toString(),
                    passwordInput.getText().toString());
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        });

        registerButton.setOnClickListener(v ->{
            navController.navigate(R.id.fragment_register);
        });
    }
}
