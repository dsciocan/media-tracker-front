package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.media_tracker_front.model.repository.UserShowRepository;
import com.northcoders.media_tracker_front.model.Film;
import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.FilmDetails;
import com.northcoders.media_tracker_front.model.repository.MovieDetailsRepository;
import com.northcoders.media_tracker_front.model.repository.UserFilmRepository;

public class MovieDetailsViewModel extends AndroidViewModel {

    MovieDetailsRepository movieDetailsRepository;
    UserFilmRepository userFilmRepository;


    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        this.movieDetailsRepository = new MovieDetailsRepository(application);
        this.userFilmRepository = new UserFilmRepository(application);

    }

    public LiveData<FilmDetails> getFilmDetails(Long id) {
        return movieDetailsRepository.getMutableLiveData(id);
    }

    public LiveData<Film> getFilmDetailsByTmdbId(Long tmdbId) {
        return movieDetailsRepository.getMutableLiveDataByTmdbId(tmdbId);
    }

    public LiveData<UserFilm> saveUserFilm(long id, UserFilm userFilm){
         return movieDetailsRepository.saveUserFilm(id, userFilm);
    }

    public LiveData<Boolean> isUserFilmSaved(Long tmdbId) {
       return movieDetailsRepository.isUserFilmSaved(tmdbId);
    }

    public LiveData<UserFilm> getUserFilm(Long filmId) {
        return userFilmRepository.getUserFilmDetails(filmId);
    }

    public void deleteUserFilm(long id){
        userFilmRepository.deleteUserFilm(id);
    }

    public void updateUserFilm(Long id, UserFilm film){
        userFilmRepository.updateUserFilm(id, film);
    }

}
