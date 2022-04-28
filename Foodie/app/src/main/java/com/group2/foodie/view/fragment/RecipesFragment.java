package com.group2.foodie.view.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.group2.foodie.R;
import com.group2.foodie.list.RecipeAdapter;
import com.group2.foodie.viewmodel.RecipesViewModel;

public class RecipesFragment extends Fragment {

    private RecyclerView recipesRecycler;
    private RecipeAdapter recipeAdapter;
    private FloatingActionButton fab;
    private RecipesViewModel viewModel;
    private NavController navController;
    private EditText searchBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(RecipesViewModel.class);
        viewModel.init();
        initializeViews(view);
        setupViews();
    }

    private void initializeViews(View view) {
        navController = Navigation.findNavController(view);
        recipesRecycler = view.findViewById(R.id.recipes_recycleView);
        searchBar = view.findViewById(R.id.recipes_searchText);
        fab = view.findViewById(R.id.personalRecipes_fab);
    }

    private void setupViews() {
        boolean isPublic = !getArguments().getString("recipeType").equals("personal");
        if (isPublic) {
            viewModel.getPublicRecipes().observe(getViewLifecycleOwner(), recipes -> {
                viewModel.setRecipes(true,"");
            });
        }
        else {
            viewModel.getPersonalRecipes().observe(getViewLifecycleOwner(), recipes -> {
                viewModel.setRecipes(false,"");
            });
        }

        recipesRecycler.hasFixedSize();
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recipesRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recipesRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }

        recipeAdapter = new RecipeAdapter();
        viewModel.getRecipes().observe(getViewLifecycleOwner(), recipes -> {
            recipeAdapter.setRecipes(recipes);
        });
        recipesRecycler.setAdapter(recipeAdapter);

        recipeAdapter.setOnClickListener(recipe -> {
            Bundle bundle = new Bundle();
            bundle.putString("recipeId", recipe.getId());
            bundle.putString("publisherId", recipe.getPublisherId());
            navController.navigate(R.id.fragment_view_recipe, bundle);
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
                viewModel.setRecipes(isPublic, searchBar.getText().toString());
            }
        });

        fab.setOnClickListener(v -> {
            navController.navigate(R.id.fragment_addedit_recipe);
        });
    }
}