package com.northcoders.media_tracker_front.model.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.model.Watching;
import com.northcoders.media_tracker_front.service.MediaApiService;
import com.northcoders.media_tracker_front.service.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchingRepository {

    private MutableLiveData<List<UserShow>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public WatchingRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<UserShow>> getMutableLiveData() {
        MediaApiService mediaApiService = RetrofitInstance.getMovieService();

        Call<List<UserShow>> call = mediaApiService.getWatching();
        call.enqueue(new Callback<List<UserShow>>() {
            @Override
            public void onResponse(Call<List<UserShow>> call, Response<List<UserShow>> response) {
                List<UserShow> watchingList = response.body();
                mutableLiveData.setValue(watchingList);
            }

            @Override
            public void onFailure(Call<List<UserShow>> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
