package com.example.sportsapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

import fragments.HomeFragment;
import fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private Map<Integer, Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.nav_home, new HomeFragment());
        fragmentMap.put(R.id.nav_events, new com.example.sportsapp.EventsFragment());
        fragmentMap.put(R.id.nav_profile, new ProfileFragment());


    }

    // BottomNavigationView item selection listener
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Retrieve the fragment corresponding to the selected menu item
                    Fragment selectedFragment = fragmentMap.get(item.getItemId());

                    // Replace the current fragment with the selected one
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                    }
                    return true;
                }
            };
}
