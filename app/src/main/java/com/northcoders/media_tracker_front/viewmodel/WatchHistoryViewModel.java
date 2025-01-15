package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.northcoders.media_tracker_front.model.WatchHistory;
import com.northcoders.media_tracker_front.model.WatchHistoryRepository;
import java.util.List;

public class WatchHistoryViewModel extends AndroidViewModel{
        WatchHistoryRepository watchHistoryRepository;

        public WatchHistoryViewModel(@NonNull Application application) {
            super(application);
            this.watchHistoryRepository = new WatchHistoryRepository(application);
        }

        public LiveData<List<WatchHistory>> getWatchHistory() {
            return watchHistoryRepository.getMutableLiveData();
        }
    }
