package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.model.repository.UserShowRepository;
import com.northcoders.media_tracker_front.model.repository.UserFilmRepository;

import java.util.List;

public class BookmarkedViewModel extends AndroidViewModel {

    UserShowRepository userShowRepository;
    UserFilmRepository filmRepository;

    public BookmarkedViewModel(@NonNull Application application) {
        super(application);
        this.userShowRepository = new UserShowRepository(application);
        this.filmRepository = new UserFilmRepository(application);
    }

    //UserFilm
    public LiveData<List<UserFilm>> getBookmarkedFilms() {
        return filmRepository.getBookmarkedFilmMutableLiveData();
    }

    //UserShow
    public LiveData<List<UserShow>> getBookmarkedShows() {
        return userShowRepository.getBookmarkedShowListLiveData();
    }
}
