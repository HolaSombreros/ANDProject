package com.group2.foodie.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.group2.foodie.list.IngredientsAdapter;
import com.group2.foodie.viewmodel.AddRecipeViewModel;

public class AddRecipeFragment extends Fragment {
    private AddRecipeViewModel viewModel;

    private EditText name;
    private Spinner categories;
    private Button addIngredientBtn;
    private RecyclerView ingredients;
    private EditText instructions;
    private FloatingActionButton fab;

    private IngredientsAdapter ingredientsAdapter;

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

        ingredients.hasFixedSize();
        ingredients.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientsAdapter = new IngredientsAdapter(viewModel.getIngredients().getValue());
        ingredients.setAdapter(ingredientsAdapter);
    }

    private void initializeViews(View view) {
        name = view.findViewById(R.id.add_recipe_name);
        categories = view.findViewById(R.id.add_recipe_category);
        addIngredientBtn = view.findViewById(R.id.add_recipe_ingredients_addbutton);
        ingredients = view.findViewById(R.id.add_recipe_ingredients);
        instructions = view.findViewById(R.id.add_recipe_instructions);
        fab = view.findViewById(R.id.add_recipe_fab);
    }

    private void setupViews() {
        viewModel.getName().observe(getViewLifecycleOwner(), name -> {
            this.name.setText(name);
        });

        ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, viewModel.getCategories());
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(nameAdapter);

        viewModel.getInstructions().observe(getViewLifecycleOwner(), instructions -> {
            this.instructions.setText(instructions);
        });

        addIngredientBtn.setOnClickListener(v -> {
            viewModel.addNewIngredient();
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (!errorMessage.equals("")) {
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(v -> {
            if (viewModel.createRecipe(name.getText().toString(), categories.getSelectedItem().toString(), instructions.getText().toString())) {
                Toast.makeText(getActivity(), "Recipe successfully created!", Toast.LENGTH_SHORT).show();
                viewModel.reset();
            }
        });

        viewModel.getIngredients().observe(getViewLifecycleOwner(), ingredients -> {
            ingredientsAdapter.setList(ingredients);
        });
    }
}