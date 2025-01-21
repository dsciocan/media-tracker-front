package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.model.FilmDetails;
import com.northcoders.media_tracker_front.viewmodel.MovieDetailsViewModel;

public class MovieFragment extends Fragment {
    private static final String MOVIE_ID_KEY = "MovieKey"  ;
    FilmDetails currentFilmDetails;
    MovieDetailsViewModel viewModel;

    public MovieFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance(long id){
        MovieFragment movieFragment = new MovieFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(MOVIE_ID_KEY,id);
        movieFragment.setArguments(bundle);
        return movieFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Long key = getArguments().getLong("MovieKey");
            viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
            // use getArguements().getLong("MovieKey") to get the film id
            // Now the viewmodel method to get the FilmResults object
            // Set the FilmResults object to currentFilmDetails
            // use that object for the textviews etc
            currentFilmDetails = viewModel.getFilmDetails(key).getValue();

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }
}