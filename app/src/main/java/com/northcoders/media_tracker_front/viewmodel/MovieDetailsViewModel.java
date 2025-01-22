package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.media_tracker_front.model.FilmDetails;
import com.northcoders.media_tracker_front.model.MovieDetailsRepository;

public class MovieDetailsViewModel extends AndroidViewModel {

    MovieDetailsRepository movieDetailsRepository;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        this.movieDetailsRepository = new MovieDetailsRepository(application);
    }

    public LiveData<FilmDetails> getFilmDetails(Long id) {
        return movieDetailsRepository.getMutableLiveData(id);
    }
}
