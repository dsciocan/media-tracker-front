package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.FragmentWatchedBinding;
import com.northcoders.media_tracker_front.model.FilmDetails;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WatchedMovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WatchedMovieFragment extends Fragment {

    FilmDetails currentFilmDetails;

    MovieDetailsViewModel;

    FragmentWatchedMovie binding;

    public WatchedMovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WatchedMovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WatchedMovieFragment newInstance(Long movieId) {
        WatchedMovieFragment fragment = new WatchedMovieFragment();
        Bundle args = new Bundle();
        args.putLong();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_watched_movie, container, false);
    }
}