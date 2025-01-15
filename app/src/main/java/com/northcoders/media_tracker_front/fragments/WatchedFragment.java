package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.northcoders.media_tracker_front.R;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.northcoders.media_tracker_front.adapter.RecyclerViewInterface;
import com.northcoders.media_tracker_front.databinding.FragmentWatchedBinding;
import com.northcoders.media_tracker_front.model.WatchHistory;
import com.northcoders.media_tracker_front.viewmodel.WatchHistoryViewModel;
import com.northcoders.media_tracker_front.adapter.WatchHistoryAdapter;
import java.util.ArrayList;
import java.util.List;

public class WatchedFragment extends Fragment implements RecyclerViewInterface {
    RecyclerView recyclerView;
    ArrayList<WatchHistory> watchHistoryArrayList;
    WatchHistoryAdapter watchHistoryAdapter;
    FragmentWatchedBinding binding;
    WatchHistoryViewModel viewModel;

    public WatchedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WatchHistoryViewModel.class);
        getAllAlbums();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_watched, container, false);
        return binding.getRoot();
    }

    private  void getAllAlbums() {
        viewModel.getWatchHistory().observe(this, new Observer<List<WatchHistory>>() {
            @Override
            public void onChanged(List<WatchHistory> albums) {
                watchHistoryArrayList = (ArrayList<WatchHistory>) albums;
                displayWatchHistoryInRecyclerView();
            }
        });
    }

    private void displayWatchHistoryInRecyclerView() {
        recyclerView = binding.recyclerview;
        watchHistoryAdapter = new WatchHistoryAdapter(watchHistoryArrayList, this.getContext(), this);
        recyclerView.setAdapter(watchHistoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        watchHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {

    }
}