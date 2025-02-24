package com.northcoders.media_tracker_front.viewmodel;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.northcoders.media_tracker_front.model.Show;
import com.northcoders.media_tracker_front.model.ShowDetails;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.model.repository.UserShowRepository;
import com.northcoders.media_tracker_front.model.repository.ShowDetailsRepository;

public class ShowDetailsViewModel extends AndroidViewModel {

    ShowDetailsRepository showDetailsRepository;
    UserShowRepository userShowRepository;


    public ShowDetailsViewModel(@NonNull Application application) {
        super(application);
        this.showDetailsRepository = new ShowDetailsRepository(application);
        this.userShowRepository = new UserShowRepository(application);

    }

    public LiveData<ShowDetails> getShowDetails(Long id) {
        return showDetailsRepository.getMutableLiveData(id);
    }

    public LiveData<Show> getShowDetailsByTmdbId(Long tmdbId) {
        return showDetailsRepository.getMutableLiveDataByTmdbId(tmdbId);
    }


    public LiveData<UserShow> saveUserShow(long id, UserShow userShow){
        return showDetailsRepository.saveUserShow(id, userShow);
    }


    public LiveData<UserShow> getUserShow(Long showId) {
        return userShowRepository.getSingleShowMutableLiveData(showId);
    }

//    public void deleteUserShow(long id){
//        userShowRepository.deleteUserShow(id);
//    }

    public void updateUserShow(Long id, UserShow show){
        userShowRepository.updateUserShow(id, show);
    }
}
