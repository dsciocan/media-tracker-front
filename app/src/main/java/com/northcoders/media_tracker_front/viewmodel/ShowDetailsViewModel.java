package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.northcoders.media_tracker_front.model.Film;
import com.northcoders.media_tracker_front.model.FilmDetails;
import com.northcoders.media_tracker_front.model.Show;
import com.northcoders.media_tracker_front.model.ShowDetails;
import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.model.repository.BookmarkedRepository;
import com.northcoders.media_tracker_front.model.repository.MovieDetailsRepository;
import com.northcoders.media_tracker_front.model.repository.ShowDetailsRepository;

public class ShowDetailsViewModel extends AndroidViewModel {

    ShowDetailsRepository showDetailsRepository;
    BookmarkedRepository bookmarkedRepository;


    public ShowDetailsViewModel(@NonNull Application application) {
        super(application);
        this.showDetailsRepository = new ShowDetailsRepository(application);
        this.bookmarkedRepository = new BookmarkedRepository(application);

    }

    public LiveData<ShowDetails> getShowDetails(Long id) {
        return showDetailsRepository.getMutableLiveData(id);
    }

    public LiveData<Show> getShowDetailsByTmdbId(Long tmdbId) {
        return showDetailsRepository.getMutableLiveDataByTmdbId(tmdbId);
    }


    public LiveData<UserShow> saveUserShow(long id, UserShow userShow){
        return showDetailsRepository.saveUserShow(id, userShow);
    }

    public LiveData<Boolean> isUserShowSaved(Long tmdbId) {
        return showDetailsRepository.isUserShowSaved(tmdbId);
    }

    public LiveData<UserShow> getUserShow(Long showId) {
        return bookmarkedRepository.getSingleShowMutableLiveData(showId);
    }

//    public void deleteUserShow(long id){
//        bookmarkedRepository.deleteUserShow(id);
//    }

    public void updateUserShow(Long id, UserShow show){
        bookmarkedRepository.updateUserShow(id, show);
    }
}
