package com.northcoders.media_tracker_front.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.UserShowActivity;
import com.northcoders.media_tracker_front.adapter.RecyclerViewInterface;
import com.northcoders.media_tracker_front.adapter.WatchingAdapter;
import com.northcoders.media_tracker_front.databinding.FragmentWatchingBinding;
import com.northcoders.media_tracker_front.model.UserEpisode;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.viewmodel.UserEpisodeViewModel;
import com.northcoders.media_tracker_front.viewmodel.WatchingViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WatchingFragment extends Fragment implements RecyclerViewInterface {
    RecyclerView recyclerView;
    List<UserShow> watchingList  = new ArrayList<>();
    WatchingAdapter adapter;
    FragmentWatchingBinding binding;
    WatchingViewModel viewModel;
    UserEpisodeViewModel userEpisodeViewModel;
    Map<Long, UserEpisode> epWatchedByShow = new HashMap<>();



    public WatchingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WatchingViewModel.class);
        userEpisodeViewModel = new ViewModelProvider(this).get(UserEpisodeViewModel.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWatching();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_watching, container, false);
        return binding.getRoot();
    }

    private void getWatching() {
        viewModel.getWatching().observe(this.getViewLifecycleOwner(), new Observer<List<UserShow>>() {
            @Override
            public void onChanged(List<UserShow> watching) {
                watchingList = watching;
                Log.i("EP", epWatchedByShow.toString());
                displayWatchingInRecyclerView();
            }
        });
    }

    private void displayWatchingInRecyclerView() {
        if(watchingList != null && epWatchedByShow != null) {
            recyclerView = binding.watchingRecyclerview;
            adapter = new WatchingAdapter(watchingList, this.getContext(), this, epWatchedByShow);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setHasFixedSize(true);
            watchingItemNextClickListener();
            adapter.notifyDataSetChanged();
        }
    }


    private void watchingItemNextClickListener() {
        adapter.setOnClickListener(new WatchingAdapter.OnClickListener() {
            @Override
            public void onNextClick(View view, int position) {
                UserShow show = watchingList.get(position);
                getLatestEpisode(show.getUserShowId().getShow().getId());
                show.setEpisodesWatched(show.getEpisodesWatched() + 1);
                viewModel.updateShow(show.getUserShowId().getShow().getId(), show);
            }
        });
    }

    public void getLatestEpisode(Long showId) {
            userEpisodeViewModel.getLatestEpisode(showId).observe(getViewLifecycleOwner(), new Observer<UserEpisode>() {
                @Override
                public void onChanged(UserEpisode episode) {
                    epWatchedByShow.put(showId, episode);
                    Log.i("EP", showId + ": " + episode.getUserEpisodeId().getEpisode().getEpisodeNumber());
                    updateLatestEpisode(episode);
                }
            });
        }


        public void latestEpisodes() {
            displayWatchingInRecyclerView();
        }

        @Override
    public void onItemClick(int position) {

        Long id = watchingList.get(position).getUserShowId().getShow().getId();

        Intent intent = new Intent(getActivity().getBaseContext(), UserShowActivity.class);
        intent.putExtra("SHOW_ID", id);
        startActivity(intent);
    }

    public void updateLatestEpisode(UserEpisode episode) {
        episode.setWatched(true);
        userEpisodeViewModel.updateUserEpisode(episode.getUserEpisodeId().getEpisode().getId(), episode);
        displayWatchingInRecyclerView();
    }
}