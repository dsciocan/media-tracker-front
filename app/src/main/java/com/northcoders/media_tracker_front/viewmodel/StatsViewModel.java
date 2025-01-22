package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.media_tracker_front.model.StatsRepository;

import java.util.Map;

public class StatsViewModel extends AndroidViewModel {

StatsRepository statsRepository;

    public StatsViewModel(Application application) {
        super(application);
        this.statsRepository = new StatsRepository(application);
    }

    public LiveData<Map<String,Integer>> getGenreStats(){
        return statsRepository.getMutableLiveData();
    }
    public LiveData<Integer> getTotalRuntimeStat(){
        return statsRepository.getTotalRuntimeStat();
    }
}
