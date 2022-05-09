package com.group2.foodie.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.group2.foodie.R;
import com.group2.foodie.list.ViewIngredientsAdapter;
import com.group2.foodie.viewmodel.FridgeViewModel;

public class FridgeFragment extends Fragment {

    private RecyclerView ingredientsRecycler;
    private ViewIngredientsAdapter ingredientsAdapter;
    private FloatingActionButton fab;
    private FridgeViewModel viewModel;
    private NavController navController;
    private EditText searchBar;

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
        searchBar = view.findViewById(R.id.fridge_searchBar);
    }

    private void setupViews() {
        ingredientsRecycler.hasFixedSize();
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        // TODO - List looks empty on first load; have to access the searchbar first
        ingredientsAdapter = new ViewIngredientsAdapter();
        viewModel.getFridgeIngredients().observe(getViewLifecycleOwner(), ingredients -> {
            viewModel.filterByIngredientName("");
        });
        ingredientsRecycler.setAdapter(ingredientsAdapter);

        ingredientsAdapter.setOnClickListener(ingredient -> {
            Bundle bundle = new Bundle();
            bundle.putString("ingredient", ingredient.getId());
            navController.navigate(R.id.fragment_addedit_ingredient, bundle);
        });

        fab.setOnClickListener(v -> {
            navController.navigate(R.id.fragment_addedit_ingredient);
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                viewModel.setFilter(searchBar.getText().toString());
            }
        });

        viewModel.getFilteredFridgeIngredients().observe(getViewLifecycleOwner(), ingredients -> {
            ingredientsAdapter.setIngredients(ingredients);
        });
    }
}
