package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.media_tracker_front.model.Show;
import com.northcoders.media_tracker_front.model.UserEpisode;
import com.northcoders.media_tracker_front.model.repository.ShowDetailsRepository;
import com.northcoders.media_tracker_front.model.repository.UserEpisodeRepository;

import java.util.List;

public class UserEpisodeViewModel extends AndroidViewModel {

    UserEpisodeRepository userEpisodeRepository;
    ShowDetailsRepository showDetailsRepository;

    public UserEpisodeViewModel(@NonNull Application application) {
        super(application);
        userEpisodeRepository = new UserEpisodeRepository(application);
        showDetailsRepository = new ShowDetailsRepository(application);
    }

    public LiveData<List<UserEpisode>> getAllUserEpisodesByShow(Long showId) {
        return userEpisodeRepository.getEpisodeListMutableData(showId);
    }

    public LiveData<UserEpisode> getUserEpisode(Long episodeId) {
        return userEpisodeRepository.getUserEpisodeMutableData(episodeId);
    }

    public void updateUserEpisode(Long episodeId, UserEpisode newEpisode) {
        userEpisodeRepository.updateUserEpisodeMutableData(episodeId, newEpisode);
    }

    public LiveData<Show> getShow(Long showId) {
        return showDetailsRepository.getShowById(showId);
    }

    public LiveData<UserEpisode> getLatestEpisode(Long showId) {
        return userEpisodeRepository.getLatestUserEpisode(showId);
    }

}
