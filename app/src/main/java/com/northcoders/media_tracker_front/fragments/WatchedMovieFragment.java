package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.FragmentWatchedBinding;
import com.northcoders.media_tracker_front.model.FilmDetails;
import com.northcoders.media_tracker_front.model.WatchHistory;
import com.northcoders.media_tracker_front.viewmodel.WatchHistoryViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WatchedMovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WatchedMovieFragment extends Fragment {

    FilmDetails currentFilmDetails;

    WatchHistory currentFilm;

    WatchHistoryViewModel viewModel;
    FragmentWatchedBinding binding;

    private final static String MOVIE_ID_KEY = "moviekey";


    public WatchedMovieFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WatchedMovieFragment newInstance(Long movieId) {
        WatchedMovieFragment fragment = new WatchedMovieFragment();
        Bundle args = new Bundle();
        args.putLong(MOVIE_ID_KEY,movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance){
        super.onCreate(savedInstance);
        if (getArguments() != null) {
            getArguments().getLong(MOVIE_ID_KEY);
            viewModel.getWatchHistory();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_watched_movie, container, false);
    }

    public void getUserFilmDetails(Long id){
        viewModel.getWatchedFilmDetails(id).observe(this, new Observer<WatchHistory>() {
            @Override
            public void onChanged(WatchHistory watchHistory) {
                currentFilm = watchHistory;
                displayInPage();
            }
        });
    }

    public void displayInPage(){
        binding.watchHistoryTitle.setText(currentFilm.getUserFilmId().getFilm().getTitle());

    }



}