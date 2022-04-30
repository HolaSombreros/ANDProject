package com.group2.foodie.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.group2.foodie.R;
import com.group2.foodie.viewmodel.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private RegisterViewModel viewModel;

    private TextInputEditText usernameInput;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText repeatPasswordInput;
    private Button registerButton;
    private Button signIn;
    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(RegisterViewModel.class);
        initializeViews(view);
        setUpViews();
    }


    private void initializeViews(View view) {
        usernameInput = view.findViewById(R.id.inputUsernameRegister);
        emailInput = view.findViewById(R.id.inputEmailRegister);
        passwordInput = view.findViewById(R.id.inputPasswordRegister);
        repeatPasswordInput = view.findViewById(R.id.inputRepeatPasswordRegister);
        registerButton = view.findViewById(R.id.registerButton);
        signIn = view.findViewById(R.id.signIn);
        navController = Navigation.findNavController(view);
    }

    private void setUpViews() {
        registerButton.setOnClickListener(view -> {
            if (viewModel.validatePassword(passwordInput.getText().toString(), repeatPasswordInput.getText().toString())) {
                try {
                    viewModel.register(usernameInput.getText().toString(), emailInput.getText().toString(), passwordInput.getText().toString());
                    navController.navigate(R.id.fragment_login);
                }
                catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else
                Toast.makeText(getContext(), R.string.passwords_not_match, Toast.LENGTH_SHORT).show();
        });
        signIn.setOnClickListener(v -> {
            navController.navigate(R.id.fragment_login);
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.reset();
    }
}
