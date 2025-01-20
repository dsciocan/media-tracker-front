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

public class BookmarkedRepository {

    private MutableLiveData<List<Bookmarked>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public BookmarkedRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Bookmarked>> getMutableLiveData() {
        MovieApiService movieApiService = RetrofitInstance.getService();

        Call<List<Bookmarked>> call = movieApiService.getBookmarked();
        call.enqueue(new Callback<List<Bookmarked>>() {
            @Override
            public void onResponse(Call<List<Bookmarked>> call, Response<List<Bookmarked>> response) {
                List<Bookmarked> bookmarkedList = response.body();
                mutableLiveData.setValue(bookmarkedList);
            }

            @Override
            public void onFailure(Call<List<Bookmarked>> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
