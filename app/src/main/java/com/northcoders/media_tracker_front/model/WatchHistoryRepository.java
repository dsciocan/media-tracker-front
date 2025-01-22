package com.northcoders.media_tracker_front.model;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

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
    private MutableLiveData<WatchHistory> userFilm = new MutableLiveData<>();
    private Application application;

    public WatchHistoryRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<WatchHistory>> getMutableLiveData(){
        UserActionsService userActionsService = RetrofitInstance.getUserService();
        // getHistory() fetches the data, see 'service'
        Call<List<WatchHistory>> call = userActionsService.getHistory();
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

    public MutableLiveData<WatchHistory> getUserFilmDetails(Long id){
        UserActionsService service = RetrofitInstance.getUserService();
        Call<WatchHistory> call = service.getUserFilmDetails(id);
        call.enqueue(new Callback<WatchHistory>() {
            @Override
            public void onResponse(Call<WatchHistory> call, Response<WatchHistory> response) {
                Log.i("WatchHistoryRepo Response", String.valueOf(response.code()));
               Log.i("WatchHistoryRepo Response", response.body().toString());
                if(response.code() == 200){
                    WatchHistory userCall = response.body();
                    userFilm.setValue(userCall);
                }

            }

            @Override
            public void onFailure(Call<WatchHistory> call, Throwable t) {
                Log.e("WatchHistoryRepo Response", t.getMessage());
            }
        });


        return userFilm;
    }


    public void deleteUserFilm(Long id){
        UserActionsService service = RetrofitInstance.getUserService();
        Call<Void> call = service.deleteUserFilm(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Watched History Repo", String.valueOf(response.code()));
                if(response.code() == 200){
                    Toast.makeText(application.getApplicationContext(), "The Movie Has Been Removed", Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Watched History Repo", t.getMessage());
            }
        });
    }

    public void updateUserFilm(Long id,WatchHistory film){
        UserActionsService service = RetrofitInstance.getUserService();
        Call<Void> call = service.updateUserFilm(id,film);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("WatchHistoryRepo Response", String.valueOf(response.code()));
                Toast.makeText(application.getApplicationContext(), "The Movie Has Been Updated", Toast.LENGTH_SHORT);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

}
