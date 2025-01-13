package com.northcoders.media_tracker_front;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.navigation.NavigationBarView;
import com.northcoders.media_tracker_front.fragments.CurrentlyWatchingFragment;
import com.northcoders.media_tracker_front.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    NavigationBarView navigationBarView;
    HomeFragment homeFragment = new HomeFragment();
    CurrentlyWatchingFragment currentlyWatchingFragment = new CurrentlyWatchingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBarView = findViewById(R.id.BottomNavView);
        navigationBarView.setOnItemSelectedListener(this);
        navigationBarView.setSelectedItemId(R.id.home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.home){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayoutFragment, homeFragment)
                    .commit();
            return true;
        }
        if(item.getItemId() == R.id.current){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayoutFragment, currentlyWatchingFragment)
                    .commit();
            return true;
        }


        return false;
    }
}