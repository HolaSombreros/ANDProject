package com.group2.foodie.view.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.group2.foodie.R;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.util.Util;
import com.group2.foodie.viewmodel.AddEditIngredientViewModel;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class AddEditIngredientFragment extends Fragment {

    private AddEditIngredientViewModel viewModel;
    private NavController navController;
    private EditText name;
    private EditText quantity;
    private Spinner measurement;
    private CalendarView expirationDate;
    private Button save;
    private Button remove;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addedit_ingredient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(AddEditIngredientViewModel.class);
        if (getArguments() != null) {
            viewModel.init(getArguments().getString("ingredient"));
        } else {
            viewModel.init("");
        }
        initializeViews(view);
        setupViews();
    }

    private void initializeViews(View view) {
        navController = Navigation.findNavController(view);
        name = view.findViewById(R.id.addedit_ingredient_name);
        quantity = view.findViewById(R.id.addedit_ingredient_quantity);
        measurement = view.findViewById(R.id.addedit_ingredient_measurement);
        expirationDate = view.findViewById(R.id.addedit_ingredient_date);
        save = view.findViewById(R.id.addedit_ingredient_save);
        remove = view.findViewById(R.id.addedit_ingredient_remove);
    }

    private void setupViews() {
        expirationDate.setOnDateChangeListener((calendarView, year, month, day) -> {
            viewModel.setDate(String.format("%d-%02d-%02d", year, (month + 1), day));
        });

        viewModel.setDate(LocalDate.now().toString());

        ArrayAdapter<String> ingredientMeasurementAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                Util.getMeasurements());
        ingredientMeasurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measurement.setAdapter(ingredientMeasurementAdapter);

        remove.setVisibility(View.INVISIBLE);

        if (getArguments() != null) {
            viewModel.getIngredient().observe(getViewLifecycleOwner(), ingredient -> {
                name.setText(ingredient.getName());
                quantity.setText(String.valueOf(ingredient.getQuantity()));
                measurement.setSelection(ingredient.getMeasurement().ordinal());
                LocalDate localDate = Util.getLocalDateFromString(ingredient.getExpirationDate());
                Calendar calendarDate = Calendar.getInstance();
                calendarDate.set(localDate.getYear(), localDate.getMonth().getValue() - 1, localDate.getDayOfMonth());
                expirationDate.setDate(calendarDate.getTimeInMillis());
                viewModel.setDate(localDate.toString());
                remove.setVisibility(View.VISIBLE);
            });
        }

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(v -> {
            if (getArguments() != null) {
                if (viewModel.saveIngredient(name.getText().toString(), quantity.getText().toString(),
                        Measurement.fromString(measurement.getSelectedItem().toString()), viewModel.getDate())) {
                    Toast.makeText(getActivity(), "Ingredient \"" + name.getText().toString() +
                            "\" saved!", Toast.LENGTH_SHORT).show();
                    navController.popBackStack();
                }
            }
            else if (viewModel.addIngredient(name.getText().toString(), quantity.getText().toString(),
                    Measurement.fromString(measurement.getSelectedItem().toString()), viewModel.getDate())) {
                Toast.makeText(getActivity(), "Ingredient \"" + name.getText().toString() +
                        "\" added!", Toast.LENGTH_SHORT).show();
                navController.popBackStack();
            }
        });

        AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(getActivity());
        deleteDialogBuilder.setMessage("Are you sure you want to delete this ingredient?");
        deleteDialogBuilder.setPositiveButton("Yes", (dialogInterface, i) -> {
            viewModel.removeIngredient();
            navController.popBackStack();
        });
        deleteDialogBuilder.setNegativeButton("No", ((dialogInterface, i) -> {
        }));
        AlertDialog deleteDialog = deleteDialogBuilder.create();

        remove.setOnClickListener(v -> {
            deleteDialog.show();
        });
    }
}
