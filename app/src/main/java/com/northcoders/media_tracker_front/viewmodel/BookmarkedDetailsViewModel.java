package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.repository.BookmarkedRepository;

public class BookmarkedDetailsViewModel extends AndroidViewModel {
    BookmarkedRepository bookmarkedRepository;

    public BookmarkedDetailsViewModel(@NonNull Application application) {
        super(application);
        this.bookmarkedRepository = new BookmarkedRepository(application);
    }

    public LiveData<UserFilm> getUserFilm(Long filmId) {
        return bookmarkedRepository.getFilmMutableLiveData(filmId);
    }

    public void deleteUserFilm(long id){
        bookmarkedRepository.deleteUserFilm(id);
    }

    public void updateUserFilm(Long id, UserFilm film){
        bookmarkedRepository.updateUserFilm(id, film);
    }

}
