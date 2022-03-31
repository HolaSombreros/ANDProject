package com.group2.foodie.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.group2.foodie.R;
import com.group2.foodie.list.EditableIngredientsAdapter;
import com.group2.foodie.viewmodel.AddRecipeViewModel;

public class AddRecipeFragment extends Fragment {
    private AddRecipeViewModel viewModel;

    private EditText recipeNameInput;
    private Spinner recipeCategoryInput;
    private Spinner ingredientNameInput;
    private EditText ingredientQuantityInput;
    private Spinner ingredientMeasurementInput;
    private Button addIngredientBtn;
    private RecyclerView ingredientsRecyclerView;
    private EditText recipeInstructionsInput;
    private FloatingActionButton addRecipeFab;

    private EditableIngredientsAdapter ingredientsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(AddRecipeViewModel.class);

        initializeViews(view);
        setupViews();

        ingredientsRecyclerView.hasFixedSize();
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientsAdapter = new EditableIngredientsAdapter(viewModel.getIngredients().getValue());
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        ingredientsAdapter.setOnRemoveListener(ingredient -> {
            viewModel.removeIngredient(ingredient);
        });
    }

    private void initializeViews(View view) {
        recipeNameInput = view.findViewById(R.id.add_recipeNameInput);
        recipeCategoryInput = view.findViewById(R.id.add_recipe_categoryInput);
        ingredientNameInput = view.findViewById(R.id.add_recipe_ingredientNameInput);
        ingredientQuantityInput = view.findViewById(R.id.add_recipe_ingredientQuantityInput);
        ingredientMeasurementInput = view.findViewById(R.id.add_recipe_ingredientMeasurementInput);
        addIngredientBtn = view.findViewById(R.id.add_recipe_addIngredientButton);
        ingredientsRecyclerView = view.findViewById(R.id.add_recipe_ingredientsRecyclerView);
        recipeInstructionsInput = view.findViewById(R.id.add_recipe_instructionsInput);
        addRecipeFab = view.findViewById(R.id.add_recipe_addRecipeFab);
    }

    private void setupViews() {
        ArrayAdapter<String> recipeCategoryAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                viewModel.getRecipeCategories());
        recipeCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipeCategoryInput.setAdapter(recipeCategoryAdapter);

        ArrayAdapter<String> ingredientNameAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                viewModel.getIngredientNames());
        ingredientNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingredientNameInput.setAdapter(ingredientNameAdapter);

        ArrayAdapter<String> ingredientMeasurementAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                viewModel.getIngredientMeasurements());
        ingredientMeasurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ingredientMeasurementInput.setAdapter(ingredientMeasurementAdapter);

        addIngredientBtn.setOnClickListener(v -> {
            if (viewModel.addNewIngredient(ingredientNameInput.getSelectedItem().toString(),
                    ingredientQuantityInput.getText().toString(),
                    ingredientMeasurementInput.getSelectedItem().toString()))
            {
                ingredientNameInput.setSelection(0);
                ingredientQuantityInput.setText("");
                ingredientMeasurementInput.setSelection(0);
            }
        });

        viewModel.getIngredients().observe(getViewLifecycleOwner(), ingredients -> {
            ingredientsAdapter.setIngredients(ingredients);
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        addRecipeFab.setOnClickListener(v -> {
            if (viewModel.addRecipe(recipeNameInput.getText().toString(),
                    recipeCategoryInput.getSelectedItem().toString(),
                    recipeInstructionsInput.getText().toString()))
            {
                Toast.makeText(getActivity(), "Recipe \"" + recipeNameInput.getText().toString() +
                        "\" created!", Toast.LENGTH_SHORT).show();

                recipeNameInput.setText("");
                recipeCategoryInput.setSelection(0);
                recipeInstructionsInput.setText("");
                ingredientNameInput.setSelection(0);
                ingredientQuantityInput.setText("");
                ingredientMeasurementInput.setSelection(0);
                viewModel.reset();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        viewModel.reset();
    }
}