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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group2.foodie.R;
import com.group2.foodie.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {
    private LoginViewModel viewModel;
    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private Button loginButton;
    private TextView registerRedirect;
    private FirebaseAuth auth;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        auth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(view);

        initializeViews(view);
        setupViews();
    }

    private void initializeViews(View view) {
       usernameInput = view.findViewById(R.id.inputUsername);
       passwordInput = view.findViewById(R.id.inputPassword);
       loginButton = view.findViewById(R.id.login);
       registerRedirect = view.findViewById(R.id.registerRedirectTxt);
    }

    private void setupViews() {
        loginButton.setOnClickListener(v -> {
            login();
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        });

        registerRedirect.setOnClickListener(v ->{
            navController.navigate(R.id.fragment_register);
        });
    }

    //TODO: this should be done in the VIEW MODEL
    private void login() {
        if (viewModel.login(usernameInput.getText().toString(), passwordInput.getText().toString())) {
            auth.signInWithEmailAndPassword(usernameInput.getText().toString(),
                    passwordInput.getText().toString())
                    .addOnCompleteListener(getActivity(), task -> {
                        if (task.isSuccessful()) {
                            navController.navigate(R.id.fragment_app_intro);
                        } else {
                            Toast.makeText(getActivity(),
                                    "Invalid email/password combination",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser current = auth.getCurrentUser();
        if (current != null) {
            navController.navigate(R.id.fragment_app_intro);
        }
    }
}
