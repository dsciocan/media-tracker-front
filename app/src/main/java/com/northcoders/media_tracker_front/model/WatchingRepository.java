package com.northcoders.media_tracker_front.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.service.MovieApiService;
import com.northcoders.media_tracker_front.service.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchingRepository {

    private MutableLiveData<List<Watching>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public WatchingRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Watching>> getMutableLiveData() {
        MovieApiService movieApiService = RetrofitInstance.getService();

        Call<List<Watching>> call = movieApiService.getWatching();
        call.enqueue(new Callback<List<Watching>>() {
            @Override
            public void onResponse(Call<List<Watching>> call, Response<List<Watching>> response) {
                List<Watching> watchingList = response.body();
                mutableLiveData.setValue(watchingList);
            }

            @Override
            public void onFailure(Call<List<Watching>> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
