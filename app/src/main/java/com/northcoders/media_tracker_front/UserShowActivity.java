package com.northcoders.media_tracker_front;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.northcoders.media_tracker_front.adapter.TabAdapter;
import com.northcoders.media_tracker_front.databinding.ActivityUserShowBinding;
import com.northcoders.media_tracker_front.fragments.EpisodeListFragment;
import com.northcoders.media_tracker_front.fragments.SavedShowDetailsFragment;

public class UserShowActivity extends AppCompatActivity {


    EpisodeListFragment episodeListFragment;

    private TabAdapter tabAdapter;
    private ViewPager2 viewPager2;
    private static String[] tabNames = {"Details", "Episodes"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Long id = intent.getLongExtra("SHOW_ID", 0);
        setContentView(R.layout.activity_user_show);

        viewPager2 = findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(getSupportFragmentManager(), getLifecycle());
        tabAdapter.addFragment(SavedShowDetailsFragment.newInstance(id));
        tabAdapter.addFragment(EpisodeListFragment.newInstance(id));
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(tabAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_menu);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(tabNames[position])
        ).attach();
    }

}