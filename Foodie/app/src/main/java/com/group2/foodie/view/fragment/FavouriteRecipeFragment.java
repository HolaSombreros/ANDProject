package com.group2.foodie.view.fragment;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.foodie.R;
import com.group2.foodie.list.RecipeAdapter;
import com.group2.foodie.viewmodel.FavouriteRecipeViewModel;

public class FavouriteRecipeFragment extends Fragment {
    private RecyclerView recyclerView;
    private NavController navController;
    private FavouriteRecipeViewModel viewModel;
    private RecipeAdapter recipeAdapter;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        navController = Navigation.findNavController(view);
        recyclerView = view.findViewById(R.id.favourite_recipe_recycler);
        viewModel = new ViewModelProvider(getActivity()).get(FavouriteRecipeViewModel.class);
        viewModel.init();
        recipeAdapter = new RecipeAdapter();

        recyclerView.hasFixedSize();
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }
        recyclerView.setAdapter(recipeAdapter);
        viewModel.getFavouriteRecipes().observe(getViewLifecycleOwner(), recipes -> {
            recipeAdapter.setRecipes(recipes);
        });

        recipeAdapter.setOnClickListener(recipe -> {
            Bundle bundle = new Bundle();
            bundle.putString("recipeId", recipe.getId());
            bundle.putString("publisherId", recipe.getPublisherId());
            navController.navigate(R.id.fragment_view_recipe, bundle);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite_recipe, container, false);
    }
}