package com.northcoders.media_tracker_front.model.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.model.ShowSearchResult;
import com.northcoders.media_tracker_front.service.MovieApiService;
import com.northcoders.media_tracker_front.service.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowSearchResultRepository {
        private MutableLiveData<List<ShowSearchResult>> mutableLiveData = new MutableLiveData<>();
        private Application application;

        public ShowSearchResultRepository(Application application) {
            this.application = application;
        }

        public MutableLiveData<List<ShowSearchResult>> getMutableLiveData(String searchQuery){
            MovieApiService movieApiService = RetrofitInstance.getMovieService();
            Call<List<ShowSearchResult>> call = movieApiService.getShowSearchResults(searchQuery);
            call.enqueue(new Callback<List<ShowSearchResult>>(){
                @Override
                public void onResponse(Call<List<ShowSearchResult>> call, Response<List<ShowSearchResult>> response) {
                    List<ShowSearchResult> showSearchResultList = response.body();
                    mutableLiveData.setValue(showSearchResultList);
                }

                @Override
                public void onFailure(Call<List<ShowSearchResult>> call, Throwable t) {
                    Log.i("GET request", t.getMessage());
                }
            });
            return mutableLiveData;
        }
    }
