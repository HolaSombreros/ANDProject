package com.group2.foodie.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.group2.foodie.R;
import com.group2.foodie.list.IngredientsAdapter;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.model.Measurement;
import com.group2.foodie.viewmodel.AddRecipeViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(AddRecipeViewModel.class);

        initializeViews(view);
        setupViews();

        ingredients.hasFixedSize();
        ingredients.setLayoutManager(new LinearLayoutManager(getActivity()));

        ingredientsAdapter = new IngredientsAdapter();
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
        addIngredientBtn.setOnClickListener((v) -> {
            ingredientsAdapter.addNewListItem();
        });

        fab.setOnClickListener((v) -> {

        });
    }
}