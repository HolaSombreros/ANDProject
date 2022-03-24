package com.group2.foodie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.group2.foodie.R;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeLayout();
        setupNavigation();
    }

    private void initializeLayout() {
        bottomNavigationView = findViewById(R.id.main_activity_bottom_navigation);
    }

    private void setupNavigation() {
        navController = Navigation.findNavController(this, R.id.main_activity_fragment);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navbar_recipes,
                R.id.navbar_fridge,
                R.id.navbar_profile
        ).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}