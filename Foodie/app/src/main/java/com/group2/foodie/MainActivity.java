package com.group2.foodie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.group2.foodie.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationDrawer;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initializeLayout();
        setupNavigation();
        setupAuthentication();
    }

    private void initializeLayout() {
        navigationDrawer = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
    }

    private void setupNavigation() {
        navController = Navigation.findNavController(this, R.id.main_activity_fragment);
        setSupportActionBar(toolbar);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fragment_personal_recipes,
                R.id.fragment_fridge,
                R.id.fragment_personal_profile,
                R.id.fragment_daily_recipe,
                R.id.fragment_shopping_list,
                R.id.fragment_followingfollowers
        ).setOpenableLayout(drawerLayout).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationDrawer, navController);
    }

    private void setupAuthentication() {
        viewModel.getCurrentFirebaseUser().observe(this, user -> {
            if (user == null) {
                navController.navigate(R.id.fragment_login);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}