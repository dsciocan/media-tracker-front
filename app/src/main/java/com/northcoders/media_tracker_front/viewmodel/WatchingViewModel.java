package com.northcoders.media_tracker_front.viewmodel;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.model.repository.UserShowRepository;
import java.util.List;

public class WatchingViewModel extends AndroidViewModel {
    UserShowRepository userShowRepository;

    public WatchingViewModel(@NonNull Application application) {
        super(application);
        this.userShowRepository = new UserShowRepository(application);
    }

    public LiveData<List<UserShow>> getWatching() {
        return userShowRepository.getWatchingMutableLiveData();
    }

    public void updateShow(Long showId, UserShow newShow) {
        userShowRepository.updateUserShow(showId, newShow);
    }
}
