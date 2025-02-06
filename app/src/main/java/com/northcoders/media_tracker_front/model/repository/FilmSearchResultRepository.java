package com.northcoders.media_tracker_front.model.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.model.FilmSearchResult;
import com.northcoders.media_tracker_front.service.MediaApiService;
import com.northcoders.media_tracker_front.service.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmSearchResultRepository {
    private MutableLiveData<List<FilmSearchResult>> mutableLiveData = new MutableLiveData<>();
    private Application application;


    public FilmSearchResultRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<FilmSearchResult>> getMutableLiveData(String searchQuery){
        MediaApiService mediaApiService = RetrofitInstance.getMovieService();
        Call<List<FilmSearchResult>> call = mediaApiService.getFilmSearchResults(searchQuery);
        call.enqueue(new Callback<List<FilmSearchResult>>() {
            @Override
            public void onResponse(Call<List<FilmSearchResult>> call, Response<List<FilmSearchResult>> response) {
                if(response.code() == 200){
                    Log.i("Film Search Repository", response.toString());
                    List<FilmSearchResult> resultList = response.body();
                    mutableLiveData.setValue(resultList);
                }else {
                    Log.e("Film Search Repository", String.valueOf(response.code()));
                    Log.e("Film Search Repository", response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<FilmSearchResult>> call, Throwable t) {
                Log.e("Film Search Repository Failed ", t.getMessage());
            }
        });
        return mutableLiveData;
    }



}
