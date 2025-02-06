package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.model.repository.WatchHistoryRepository;
import java.util.List;

public class WatchHistoryViewModel extends AndroidViewModel{
        WatchHistoryRepository watchHistoryRepository;

        public WatchHistoryViewModel(@NonNull Application application) {
            super(application);
            this.watchHistoryRepository = new WatchHistoryRepository(application);
        }
        public LiveData<List<UserFilm>> getWatchHistory() {
            return watchHistoryRepository.getMutableLiveData();
        }
        public LiveData<UserFilm> getWatchedFilmDetails(Long id){
            return watchHistoryRepository.getUserFilmDetails(id);
        }
        public void deleteUserFilm(long id){
            watchHistoryRepository.deleteUserFilm(id);
        }

        public void updateUserFilm(Long id,UserFilm film){
            watchHistoryRepository.updateUserFilm( id , film);
        }

        public LiveData<List<UserShow>> getWatchedShows() {
            return watchHistoryRepository.getShowListLiveData();
        }

    }
