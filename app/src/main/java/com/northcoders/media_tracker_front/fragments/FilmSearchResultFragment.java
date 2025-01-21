package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.adapter.FilmSearchResultAdapter;
import com.northcoders.media_tracker_front.adapter.RecyclerViewInterface;
import com.northcoders.media_tracker_front.databinding.FragmentFilmSearchResultsBinding;
import com.northcoders.media_tracker_front.model.FilmSearchResult;
import com.northcoders.media_tracker_front.viewmodel.FilmSearchResultViewModel;

import java.util.ArrayList;
import java.util.List;

public class FilmSearchResultFragment extends Fragment implements RecyclerViewInterface {
    private FragmentFilmSearchResultsBinding binding;
    private RecyclerView recyclerView;
    ArrayList<FilmSearchResult> searchResults = new ArrayList<>();
    FilmSearchResultAdapter filmSearchResultAdapter;
    FilmSearchResultViewModel viewModel;
    private String previousQuery;

    public FilmSearchResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(FilmSearchResultViewModel.class);
        super.onCreate(savedInstanceState);
        previousQuery = "";
        if(getArguments() != null){
            previousQuery = previousQuery.replace("",getArguments().getString("SearchQuery"));
           getArguments().clear();
        }

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        if(previousQuery != null && !previousQuery.isEmpty()){
////            binding.SearchViewSearch.setQuery(previousQuery,false);
//
           getFilmResults(previousQuery);
//        }

        binding.SearchViewSearch.clearFocus();

        binding.SearchViewSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getFilmResults(query);
                binding.SearchViewSearch.setQuery(query,false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_film_search_results,container,false);
        // Inflate the layout for this fragment

           return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {
    }

    private void getFilmResults(String query){
        viewModel.getFilmSearchResults(query).observe(getViewLifecycleOwner(), new Observer<List<FilmSearchResult>>() {
            @Override
            public void onChanged(List<FilmSearchResult> filmSearchResults) {
                if(!searchResults.isEmpty()){
                searchResults.clear();}
                searchResults = (ArrayList<FilmSearchResult>) filmSearchResults;
                displayInRecyclerView();
            }
        });
    }

    private void displayInRecyclerView(){
        recyclerView = binding.recyclerview;
        filmSearchResultAdapter = new FilmSearchResultAdapter(searchResults,this.getContext(),this);
        recyclerView.setAdapter(filmSearchResultAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        filmSearchResultAdapter.notifyDataSetChanged();
    }


}