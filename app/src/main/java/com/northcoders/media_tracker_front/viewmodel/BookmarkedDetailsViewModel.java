package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.model.repository.UserShowRepository;
import com.northcoders.media_tracker_front.model.repository.UserFilmRepository;

public class BookmarkedDetailsViewModel extends AndroidViewModel {
    UserShowRepository userShowRepository;
    UserFilmRepository userFilmRepository;

    public BookmarkedDetailsViewModel(@NonNull Application application) {
        super(application);
        this.userShowRepository = new UserShowRepository(application);
        this.userFilmRepository = new UserFilmRepository(application);
    }

    //UserFilm

    public LiveData<UserFilm> getUserFilm(Long filmId) {
        return userFilmRepository.getUserFilmDetails(filmId);
    }

    public void deleteUserFilm(long id){
        userFilmRepository.deleteUserFilm(id);
    }

    public void updateUserFilm(Long id, UserFilm film){
        userFilmRepository.updateUserFilm(id, film);
    }


    //UserShow

    public LiveData<UserShow> getUserShow(Long showId) {
        return userShowRepository.getSingleShowMutableLiveData(showId);
    }

    public void updateUserShow(Long id, UserShow show){
        userShowRepository.updateUserShow(id, show);
    }

}
