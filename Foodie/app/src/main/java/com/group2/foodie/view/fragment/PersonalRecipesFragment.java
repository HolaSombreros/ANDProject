package com.group2.foodie.view.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.foodie.R;
import com.group2.foodie.list.RecipeAdapter;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.viewmodel.PersonalRecipesViewModel;

import java.util.ArrayList;
import java.util.List;

public class PersonalRecipesFragment extends Fragment {

    private RecyclerView recipesRecycler;
    private RecipeAdapter recipeAdapter;
    private PersonalRecipesViewModel viewModel;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recipesRecycler = view.findViewById(R.id.personalRecipes_recycleView);
        recipesRecycler.hasFixedSize();
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recipesRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recipesRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }

        ArrayList<Recipe> recipeList = new ArrayList<>();
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));
        recipeList.add(new Recipe("Burger", R.drawable.ic_fridge, new ArrayList<>(), "1. Kill the pig", true, "Food"));


        recipeAdapter = new RecipeAdapter(recipeList);
        //recipeAdapter = new RecipeAdapter(viewModel.getPersonalRecipes());
        recipesRecycler.setAdapter(recipeAdapter);

        // TODO
        //navController = Navigation.findNavController(getActivity(), R.id.main_activity_fragment);

        recipeAdapter.setOnClickListener(v -> {
            //  TODO
            // navController.navigate(R.id.);
        });
    }
}