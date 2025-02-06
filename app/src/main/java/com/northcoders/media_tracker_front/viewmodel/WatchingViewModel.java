package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.model.Watching;
import com.northcoders.media_tracker_front.model.repository.WatchingRepository;

import java.util.List;

public class WatchingViewModel extends AndroidViewModel {
    WatchingRepository watchingRepository;
    public WatchingViewModel(@NonNull Application application) {
        super(application);
        this.watchingRepository = new WatchingRepository(application);
    }

    public LiveData<List<UserShow>> getWatching() {
        return watchingRepository.getMutableLiveData();
    }
}
