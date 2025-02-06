package com.northcoders.media_tracker_front;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.navigation.NavigationBarView;
import com.northcoders.media_tracker_front.fragments.BookmarkedFragment;
import com.northcoders.media_tracker_front.fragments.WatchingFragment;
import com.northcoders.media_tracker_front.fragments.EpisodeFragment;
import com.northcoders.media_tracker_front.fragments.HomeFragment;
import com.northcoders.media_tracker_front.fragments.MovieFragment;
import com.northcoders.media_tracker_front.fragments.ProfileFragment;
import com.northcoders.media_tracker_front.fragments.StatsFragment;
import com.northcoders.media_tracker_front.fragments.WatchedFragment;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    NavigationBarView navigationBarView;
    HomeFragment homeFragment = new HomeFragment();
    BookmarkedFragment bookmarkedFragment = new BookmarkedFragment();
    StatsFragment statsFragment = new StatsFragment();
    WatchedFragment watchedFragment = new WatchedFragment();
    MovieFragment movieFragment = new MovieFragment();
    EpisodeFragment episodeFragment = new EpisodeFragment();

    ProfileFragment profileFragment = new ProfileFragment();

    WatchingFragment watchingFragment = new WatchingFragment();

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
                    .replace(R.id.frameLayoutFragment, watchingFragment)
                    .commit();
            return true;
        }
        if(item.getItemId() == R.id.bookmarks){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayoutFragment, bookmarkedFragment)
                    .commit();
            return true;
        }
        if(item.getItemId() == R.id.stats){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayoutFragment, statsFragment)
                    .commit();
            return true;
        }
        if(item.getItemId() == R.id.watched){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayoutFragment,watchedFragment)
                    .commit();
            return true;
        }
        return false;
    }
}