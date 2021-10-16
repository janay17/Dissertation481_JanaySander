package com.sander.fantasticfood.UserInterface.MainComponents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sander.fantasticfood.R;
import com.sander.fantasticfood.databinding.ActivityMyProfileBinding;

public class MyProfileActivity extends AppCompatActivity {

    private ActivityMyProfileBinding binding;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.profileToolbar;
        setSupportActionBar(toolbar);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_profile_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.profileMyDetails:
            case R.id.profileMealPlans:
            case R.id.profileMyRecipes:
            case R.id.profileAllergies:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}