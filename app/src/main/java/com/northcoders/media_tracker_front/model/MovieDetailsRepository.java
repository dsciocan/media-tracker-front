package com.northcoders.media_tracker_front.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.service.MovieApiService;
import com.northcoders.media_tracker_front.service.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsRepository {

    MutableLiveData<FilmDetails> mutableLiveData = new MutableLiveData<>();

    private final Application application;

    public MovieDetailsRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<FilmDetails> getMutableLiveData(Long id) {
        MovieApiService movieApiService = RetrofitInstance.getService();

        Call<FilmDetails> call = movieApiService.getFilmDetails(id);
        call.enqueue(new Callback<FilmDetails>() {
            @Override
            public void onResponse(Call<FilmDetails> call, Response<FilmDetails> response) {
                FilmDetails film = response.body();
                mutableLiveData.setValue(film);
            }

            @Override
            public void onFailure(Call<FilmDetails> call, Throwable t) {
                Log.i("GET request", t.getMessage());

            }
        });

        return mutableLiveData;
    }
}
