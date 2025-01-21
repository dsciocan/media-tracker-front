package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.adapter.BookmarkedAdapter;
import com.northcoders.media_tracker_front.adapter.RecyclerViewInterface;
import com.northcoders.media_tracker_front.databinding.FragmentBookmarkedBinding;
import com.northcoders.media_tracker_front.model.Bookmarked;
import com.northcoders.media_tracker_front.viewmodel.BookmarkedViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookmarkedFragment extends Fragment implements RecyclerViewInterface {
    RecyclerView recyclerView;
    List<Bookmarked> bookmarkedList;
    BookmarkedAdapter adapter;

    FragmentBookmarkedBinding binding;
    BookmarkedViewModel viewModel;


    public BookmarkedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BookmarkedViewModel.class);
        getBookmarked();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmarked, container, false);
        return binding.getRoot();
    }

    private void getBookmarked() {
        viewModel.getBookmarked().observe(this, new Observer<List<Bookmarked>>() {
            @Override
            public void onChanged(List<Bookmarked> list) {
                bookmarkedList = list;
                displayBookmarkedInRecyclerView();
            }
        });
    }

    private void displayBookmarkedInRecyclerView() {
        recyclerView = binding.bookmarkedRecyclerview;
        adapter = new BookmarkedAdapter(bookmarkedList, this.getContext(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {

    }
}