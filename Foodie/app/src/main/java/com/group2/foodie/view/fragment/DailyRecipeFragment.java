package com.group2.foodie.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.group2.foodie.R;
import com.group2.foodie.adapter.ViewPagerAdapter;
import com.group2.foodie.dailyrecipe.DailyRecipe;
import com.group2.foodie.dailyrecipe.ExtendedIngredientsAdapter;
import com.group2.foodie.list.ViewIngredientsAdapter;
import com.group2.foodie.model.Recipe;
import com.group2.foodie.viewmodel.DailyRecipeViewModel;

public class DailyRecipeFragment extends Fragment {

    private DailyRecipeViewModel dailyRecipeViewModel;
    private TextView instructions;
    private EditText recipeName;
    private ImageView imageView;
    private RecyclerView ingredientsList;
    private TextView preparationTime;
    private TextView servings;
    private ExtendedIngredientsAdapter ingredientsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_daily_recipe, container, false);
    }
    private void initViews(View view) {
        instructions = view.findViewById(R.id.daily_instructions);
        recipeName = view.findViewById(R.id.daily_recipe_name);
        imageView = view.findViewById(R.id.daily_image);
        ingredientsList = view.findViewById(R.id.daily_recipe_ingredients);
        preparationTime = view.findViewById(R.id.daily_preparation_time);
        servings = view.findViewById(R.id.daily_servings);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        dailyRecipeViewModel = new ViewModelProvider(getActivity()).get(DailyRecipeViewModel.class);
        dailyRecipeViewModel.init();
        initViews(view);
        ingredientsList.hasFixedSize();
        ingredientsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        dailyRecipeViewModel.searchDailyRecipe();
        dailyRecipeViewModel.getDailyRecipe().observe(getViewLifecycleOwner(), recipe-> {
                instructions.setText(recipe.getInstructions());
                recipeName.setText(recipe.getTitle());
                ingredientsAdapter = new ExtendedIngredientsAdapter(recipe.getExtendedIngredients());
                ingredientsList.setAdapter(ingredientsAdapter);
                servings.setText(new StringBuilder().append("Servings ").append((recipe.getServings())));
                preparationTime.setText(new StringBuilder().append("Ready in ").append((recipe.getReadyInMinutes())).append(" minutes"));
                Glide.with(getActivity()).load(recipe.getImage()).into(imageView);
        });
    }
}