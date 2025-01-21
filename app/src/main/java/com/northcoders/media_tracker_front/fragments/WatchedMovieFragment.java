package com.northcoders.media_tracker_front.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.FragmentWatchedBinding;
import com.northcoders.media_tracker_front.databinding.FragmentWatchedMovieBinding;
import com.northcoders.media_tracker_front.model.FilmDetails;
import com.northcoders.media_tracker_front.model.WatchHistory;
import com.northcoders.media_tracker_front.viewmodel.WatchHistoryViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WatchedMovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WatchedMovieFragment extends Fragment {

    FilmDetails currentFilmDetails;

    WatchHistory currentFilm = new WatchHistory();

    WatchHistoryViewModel viewModel;
    FragmentWatchedMovieBinding binding;

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
        viewModel = new ViewModelProvider(this).get(WatchHistoryViewModel.class) ;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance){
        super.onCreate(savedInstance);
        if (getArguments() != null) {
            getUserFilmDetails(getArguments().getLong(MOVIE_ID_KEY));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_watched_movie, container, false );
        return binding.getRoot();
    }

    public void getUserFilmDetails(Long id){
        viewModel.getWatchedFilmDetails(id).observe(this, new Observer<WatchHistory>() {
            @Override
            public void onChanged(WatchHistory watchHistory) {
                currentFilm = watchHistory;
                displayInPage(currentFilm);
            }
        });
    }

    public void displayInPage(WatchHistory watchHistory){
        binding.movieFragmentTitle.setText(watchHistory.getUserFilmId().getFilm().getTitle());
        binding.movieYear.setText(watchHistory.getWatchedDate());
        binding.movieRuntime.setText(watchHistory.getUserFilmId().getFilm().getDuration() + " mins");
        binding.movieLanguage.setText(watchHistory.getUserFilmId().getFilm().getLanguage());
        binding.movieCountry.setText(watchHistory.getUserFilmId().getFilm().getCountry());

        Glide.with(binding.movieFragmentImage.getContext())
                .load(watchHistory.getUserFilmId().getFilm().getPoster_url())
                .into(binding.movieFragmentImage);
        List<String> genres = watchHistory.getUserFilmId().getFilm().getGenres();
        binding.movieGenres.setText(String.join(", ", genres));
        TextView detailsTextView = binding.movieFragmentDetailsCard.findViewById(R.id.details_text_view);
        detailsTextView.setText(watchHistory.getUserFilmId().getFilm().getSynopsis());
        binding.movieFragmentNotesInput.setText(watchHistory.getNotes());
        int rating = watchHistory.getRating();
        binding.movieFragmentRatingBar.setRating(rating);

    }



}