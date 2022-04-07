package com.group2.foodie.view.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.group2.foodie.R;
import com.group2.foodie.list.RecipeAdapter;
import com.group2.foodie.list.ViewIngredientsAdapter;
import com.group2.foodie.viewmodel.FridgeViewModel;

public class FridgeFragment extends Fragment {

    private RecyclerView ingredientsRecycler;
    private ViewIngredientsAdapter ingredientsAdapter;
    private FloatingActionButton fab;
    private FridgeViewModel viewModel;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fridge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(FridgeViewModel.class);
        viewModel.init();
        initializeViews(view);
        setupViews();
    }

    private void initializeViews(View view) {
        navController = Navigation.findNavController(view);
        ingredientsRecycler = view.findViewById(R.id.fridge_recyclerView);
        fab = view.findViewById(R.id.fridge_fab);
    }

    private void setupViews() {
        ingredientsRecycler.hasFixedSize();
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        ingredientsAdapter = new ViewIngredientsAdapter();

        viewModel.getFridgeIngredients().observe(getViewLifecycleOwner(), ingredients -> {
            ingredientsAdapter.setIngredients(ingredients);
        });
        ingredientsRecycler.setAdapter(ingredientsAdapter);

        ingredientsAdapter.setOnClickListener(ingredient -> {
            Bundle bundle = new Bundle();
            bundle.putString("ingredient", ingredient.getName());
            // navigate to view
        });

        fab.setOnClickListener(v -> {
           //navigate to add TODO
        });
    }
}
