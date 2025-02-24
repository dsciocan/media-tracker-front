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

public class WatchHistoryViewModel extends AndroidViewModel{
        UserFilmRepository userFilmRepository;
        UserShowRepository userShowRepository;

        public WatchHistoryViewModel(@NonNull Application application) {
            super(application);
            this.userFilmRepository = new UserFilmRepository(application);
            this.userShowRepository = new UserShowRepository(application);
        }

        //UserFilm
        public LiveData<List<UserFilm>> getWatchHistory() {
            return userFilmRepository.getWatchedFilmMutableLiveData();
        }
        public LiveData<UserFilm> getWatchedFilmDetails(Long id){
            return userFilmRepository.getUserFilmDetails(id);
        }
        public void deleteUserFilm(long id){
            userFilmRepository.deleteUserFilm(id);
        }

        public void updateUserFilm(Long id,UserFilm film){
            userFilmRepository.updateUserFilm( id , film);
        }

        //UserShow
        public LiveData<List<UserShow>> getWatchedShows() {
            return userShowRepository.getWatchedShowLiveData();
        }

    }
