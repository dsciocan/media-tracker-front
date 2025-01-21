package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.model.FilmSearchResult;
import com.northcoders.media_tracker_front.model.FilmSearchResultRepository;

import java.util.List;

public class FilmSearchResultViewModel extends AndroidViewModel {
FilmSearchResultRepository filmSearchResultRepository;


    public FilmSearchResultViewModel(@NonNull Application application) {
        super(application);
        this.filmSearchResultRepository = new FilmSearchResultRepository(application);
    }

    public MutableLiveData<List<FilmSearchResult>> getFilmSearchResults(String searchQuery){
        return filmSearchResultRepository.getMutableLiveData(searchQuery);
    }
}
