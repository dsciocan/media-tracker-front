package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.model.repository.UserShowRepository;

public class SavedShowDetailsViewModel extends AndroidViewModel {
   UserShowRepository userShowRepository;

    public SavedShowDetailsViewModel(@NonNull Application application) {
        super(application);
        userShowRepository = new UserShowRepository(application);
    }

    public LiveData<UserShow> getSingleUserShow(Long showId) {
        return userShowRepository.getSingleShowMutableLiveData(showId);
    }

    public void updateUserShow(Long showId, UserShow newShow) {
        userShowRepository.updateUserShow(showId, newShow);
    }

}
