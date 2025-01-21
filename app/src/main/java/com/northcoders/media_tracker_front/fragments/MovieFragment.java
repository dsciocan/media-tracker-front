package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.FragmentMovieBinding;
import com.northcoders.media_tracker_front.model.FilmDetails;
import com.northcoders.media_tracker_front.viewmodel.MovieDetailsViewModel;

public class MovieFragment extends Fragment {
    private static final String MOVIE_ID_KEY = "MovieKey"  ;
    FilmDetails currentFilmDetails;
    MovieDetailsViewModel viewModel;

    FragmentMovieBinding binding;

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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Long key = getArguments().getLong("MovieKey");
            currentFilmDetails = viewModel.getFilmDetails(key).getValue();
            binding.movieFragmentTitle.setText(currentFilmDetails.getTitle());
            binding.movieFragmentOverview.setText(currentFilmDetails.getOverview());
            binding.movieLanguage.setText(currentFilmDetails.getTitle());
        }



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie,container, false);
        return binding.getRoot();
    }





}