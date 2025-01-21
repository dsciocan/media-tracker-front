package com.northcoders.media_tracker_front.model;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.northcoders.media_tracker_front.service.MovieApiService;
import com.northcoders.media_tracker_front.service.RetrofitInstance;
import com.northcoders.media_tracker_front.service.UserActionsService;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchHistoryRepository {
    private MutableLiveData<List<WatchHistory>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public WatchHistoryRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<WatchHistory>> getMutableLiveData(){
        //UserActionsService userActionsService = RetrofitInstance.getUserService();
        MovieApiService movieApiService = RetrofitInstance.getService();

        // getHistory() fetches the data, see 'service'
        Call<List<WatchHistory>> call = movieApiService.getHistory();
        call.enqueue(new Callback<List<WatchHistory>>(){
            @Override
            public void onResponse(Call<List<WatchHistory>> call, Response<List<WatchHistory>> response) {
                List<WatchHistory> historyList = response.body();
                mutableLiveData.setValue(historyList);
            }

            @Override
            public void onFailure(Call<List<WatchHistory>> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
