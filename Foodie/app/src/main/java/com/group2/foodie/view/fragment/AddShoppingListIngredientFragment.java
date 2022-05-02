package com.group2.foodie.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.group2.foodie.R;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.util.Util;
import com.group2.foodie.viewmodel.AddEditIngredientViewModel;
import com.group2.foodie.viewmodel.AddShoppingListIngredientViewModel;

public class AddShoppingListIngredientFragment extends Fragment {
    private EditText ingredientName;
    private EditText ingredientQuantity;
    private Spinner spinner;
    private Button saveIngredient;
    private AddShoppingListIngredientViewModel viewModel;
    private NavController navController;


    private void initViews(View view) {
        navController = Navigation.findNavController(view);
        ingredientName = view.findViewById(R.id.shopping_add_ingredient);
        ingredientQuantity =view.findViewById(R.id.shopping_ingredient_quantity);
        spinner = view.findViewById(R.id.shopping_ingredient_measurement);
        saveIngredient = view.findViewById(R.id.shopping_ingredient_save);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_shopping_list_ingredient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(AddShoppingListIngredientViewModel.class);
        initViews(view);
        ArrayAdapter<String> ingredientMeasurementAdapter= new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                Util.getMeasurements());
        ingredientMeasurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ingredientMeasurementAdapter);
        saveIngredient.setOnClickListener(v -> {
            if(viewModel.addIngredient(ingredientName.getText().toString(),
                    ingredientQuantity.getText().toString(),
                    Measurement.fromString(spinner.getSelectedItem().toString()))) {
                navController.navigate(R.id.fragment_shopping_list);
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }
}