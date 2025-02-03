package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.model.FilmSearchResult;
import com.northcoders.media_tracker_front.model.repository.FilmSearchResultRepository;
import com.northcoders.media_tracker_front.model.ShowSearchResult;
import com.northcoders.media_tracker_front.model.repository.ShowSearchResultRepository;

import java.util.List;

public class FilmSearchResultViewModel extends AndroidViewModel {
FilmSearchResultRepository filmSearchResultRepository;
ShowSearchResultRepository showSearchResultRepository;


    public FilmSearchResultViewModel(@NonNull Application application) {
        super(application);
        this.filmSearchResultRepository = new FilmSearchResultRepository(application);
        this.showSearchResultRepository = new ShowSearchResultRepository(application);
    }

    public MutableLiveData<List<FilmSearchResult>> getFilmSearchResults(String searchQuery){
        return filmSearchResultRepository.getMutableLiveData(searchQuery);
    }

    public MutableLiveData<List<ShowSearchResult>> getShowSearchResults(String searchQuery) {
        return showSearchResultRepository.getMutableLiveData(searchQuery);
    }

}
