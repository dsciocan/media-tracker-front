package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.northcoders.media_tracker_front.model.ShowSearchResult;
import com.northcoders.media_tracker_front.model.ShowSearchResultRepository;
import java.util.List;

public class ShowSearchResultViewModel extends AndroidViewModel{
    ShowSearchResultRepository showSearchResultRepository;

    public ShowSearchResultViewModel(@NonNull Application application) {
        super(application);
        this.showSearchResultRepository = new ShowSearchResultRepository(application);
    }

    public LiveData<List<ShowSearchResult>> getShowSearchResult(String searchQuery) {
        return showSearchResultRepository.getMutableLiveData(searchQuery);
    }
}
