package com.group2.foodie.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.group2.foodie.R;
import com.group2.foodie.adapter.ViewPagerAdapter;
import com.group2.foodie.dailyrecipe.DailyRecipe;
import com.group2.foodie.viewmodel.DailyRecipeViewModel;

public class DailyRecipeFragment extends Fragment {

    private DailyRecipeViewModel dailyRecipeViewModel;
    private TextView instructions;
    private EditText recipeName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(
                R.layout.fragment_daily_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        dailyRecipeViewModel = new ViewModelProvider(getActivity()).get(DailyRecipeViewModel.class);
        //dailyRecipeViewModel.init();
        instructions = view.findViewById(R.id.daily_instructions);
        recipeName = view.findViewById(R.id.daily_recipe_name);
        dailyRecipeViewModel.searchDailyRecipe();
        dailyRecipeViewModel.getDailyRecipe().observe(getViewLifecycleOwner(), recipe-> {
             instructions.setText(recipe.getInstructions());
                recipeName.setText(recipe.getTitle());
                //Log.w("daily", recipe.getTitle());

        });
    }
}