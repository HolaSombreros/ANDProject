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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.group2.foodie.util.GlideApp;
import com.google.firebase.auth.FirebaseAuth;
import com.group2.foodie.model.Ingredient;
import com.group2.foodie.util.Util;
import com.group2.foodie.viewmodel.MainViewModel;

import java.time.LocalDate;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private TextView username;
    private TextView email;

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
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            scheduleNotification();
    }

    private void initializeLayout() {
        navigationDrawer = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);

        View header = navigationDrawer.getHeaderView(0);
        image = header.findViewById(R.id.drawer_header_image);
        username = header.findViewById(R.id.drawer_header_username);
        email = header.findViewById(R.id.drawer_header_email);
    }

    private void setupNavigation() {
        navController = Navigation.findNavController(this, R.id.main_activity_fragment);
        setSupportActionBar(toolbar);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fragment_recipes,
                R.id.fragment_public_recipes,
                R.id.fragment_fridge,
                R.id.fragment_personal_profile,
                R.id.fragment_daily_recipe,
                R.id.fragment_shopping_list,
                R.id.fragment_followingfollowers
        ).setOpenableLayout(drawerLayout).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationDrawer, navController);
        navigationDrawer.setNavigationItemSelectedListener(item -> {
            Bundle bundle = new Bundle();
            if (item.getItemId() == R.id.fragment_recipes) {
                bundle.putString("recipeType", "personal");
            }
            if (item.getItemId() == R.id.fragment_public_recipes) {
                bundle.putString("recipeType", "public");
            }
            if (item.getItemId() == R.id.fragment_personal_profile) {
                bundle.putString("profileId", null);
            }

            navController.navigate(item.getItemId(), bundle);
            drawerLayout.closeDrawers();
            return true;
        });
    }

    private void setupAuthentication() {
        viewModel.getCurrentFirebaseUser().observe(this, user -> {
            if (user == null) {
                navController.navigate(R.id.fragment_login);
            } else {
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/users/" + user.getUid() + ".jpg");
                GlideApp.with(this).load(storageRef).into(image);
                username.setText(user.getDisplayName());
                email.setText(user.getEmail());
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

    public void scheduleNotification() {
        Intent intent = new Intent(this, NotificationPublisher.class);
        intent.putExtra("hasExpired", "false");

        viewModel.getIngredients().observe(this, ingredients -> {
            for (Ingredient ingredient : ingredients) {
                if (Util.getLocalDateFromString(ingredient.getExpirationDate()).equals(LocalDate.now().plusDays(1)))
                    intent.putExtra("hasExpired", "true");
            }
        });

        PendingIntent pending = PendingIntent.getBroadcast(this, 42, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pending);
    }
}