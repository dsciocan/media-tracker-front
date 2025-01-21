package com.northcoders.media_tracker_front.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.service.RetrofitInstance;
import com.northcoders.media_tracker_front.service.UserActionsService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsRepository {
    private MutableLiveData<Map<String,Integer>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public StatsRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<Map<String,Integer>> getMutableLiveData(){
        UserActionsService userActionsService = RetrofitInstance.getUserService();
//        Call<Map<String,Integer>> call = userActionsService.getGenreStats();
//        call.enqueue(new Callback<Map<String, Integer>>() {
//            @Override
//            public void onResponse(Call<Map<String, Integer>> call, Response<Map<String, Integer>> response) {
//                if(response.code() == 200){
//                    Log.i("Stats Repository", response.body().toString());
//                    Map<String, Integer> genreStatsMap = response.body();
//                    mutableLiveData.setValue(genreStatsMap);
//                }
//                else{
//                    Log.e("Stats Repository", String.valueOf(response.code()));
//                    Log.e("Stats Repository", response.message());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Map<String, Integer>> call, Throwable t) {
//                Log.e("Stats Repository", t.getMessage());
//            }
//        });

        return mutableLiveData;
    }
}
