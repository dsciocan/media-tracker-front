package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.adapter.RecyclerViewInterface;
import com.northcoders.media_tracker_front.adapter.WatchingAdapter;
import com.northcoders.media_tracker_front.databinding.FragmentWatchingBinding;
import com.northcoders.media_tracker_front.model.Watching;
import com.northcoders.media_tracker_front.viewmodel.WatchingViewModel;

import java.util.List;

public class WatchingFragment extends Fragment implements RecyclerViewInterface {
    RecyclerView recyclerView;
    List<Watching> watchingList;
    WatchingAdapter adapter;
    FragmentWatchingBinding binding;
    WatchingViewModel viewModel;


    public WatchingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WatchingViewModel.class);
        //getWatching();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_watching, container, false);
        return binding.getRoot();
    }

    private void getWatching() {
        viewModel.getWatching().observe(this, new Observer<List<Watching>>() {
            @Override
            public void onChanged(List<Watching> watching) {
                watchingList = watching;
                displayWatchingInRecyclerView();
            }
        });
    }

    private void displayWatchingInRecyclerView() {
        recyclerView = binding.watchingRecyclerview;
        adapter = new WatchingAdapter(watchingList, this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {

    }
}