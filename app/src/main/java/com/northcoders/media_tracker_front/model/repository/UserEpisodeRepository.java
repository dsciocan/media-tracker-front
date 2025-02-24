package com.northcoders.media_tracker_front.model.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;


import androidx.lifecycle.MutableLiveData;

import com.firebase.ui.auth.data.model.User;
import com.northcoders.media_tracker_front.model.UserEpisode;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.service.RetrofitInstance;
import com.northcoders.media_tracker_front.service.UserActionsService;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserEpisodeRepository {



    MutableLiveData<List<UserEpisode>> episodeListMutableData = new MutableLiveData<>();
    MutableLiveData<UserEpisode> episodeMutableLiveData = new MutableLiveData<>();

    private Application application;

    public UserEpisodeRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<UserEpisode>> getEpisodeListMutableData(Long showId) {
        UserActionsService userActionsService = RetrofitInstance.getUserService();
        Call<List<UserEpisode>> call = userActionsService.getUserEpisodeListByShowId(showId);
        call.enqueue(new Callback<List<UserEpisode>>() {
            @Override
            public void onResponse(Call<List<UserEpisode>> call, Response<List<UserEpisode>> response) {
                List<UserEpisode> results = response.body();
                Log.i("EPREPO", response.body().toString());
                episodeListMutableData.postValue(results);
                episodeListMutableData.setValue(results);
            }

            @Override
            public void onFailure(Call<List<UserEpisode>> call, Throwable t) {
                Log.i("EPISODEREPO", t.getMessage());
            }
        });
        return episodeListMutableData;
    }

    public MutableLiveData<UserEpisode> getUserEpisodeMutableData(Long episodeId) {
        UserActionsService userActionsService = RetrofitInstance.getUserService();
        Call<UserEpisode> call = userActionsService.getUserEpisode(episodeId);
        call.enqueue(new Callback<UserEpisode>() {
            @Override
            public void onResponse(Call<UserEpisode> call, Response<UserEpisode> response) {
                UserEpisode userEpisode = response.body();
                episodeMutableLiveData.postValue(userEpisode);
                episodeMutableLiveData.setValue(userEpisode);
            }

            @Override
            public void onFailure(Call<UserEpisode> call, Throwable t) {
                Log.i("EPISODEREPO", t.getMessage());
            }
        });
        return episodeMutableLiveData;
    }


    public MutableLiveData<UserEpisode> getLatestUserEpisode(Long showId) {
        UserActionsService userActionsService = RetrofitInstance.getUserService();
        Call<UserEpisode> call = userActionsService.getLatestUserEpisode(showId);
        call.enqueue(new Callback<UserEpisode>() {
            @Override
            public void onResponse(Call<UserEpisode> call, Response<UserEpisode> response) {
                UserEpisode userEpisode = response.body();
                episodeMutableLiveData.postValue(userEpisode);
                episodeMutableLiveData.setValue(userEpisode);
            }

            @Override
            public void onFailure(Call<UserEpisode> call, Throwable t) {
                Log.i("EPISODEREPO", t.getMessage());
            }
        });
        return episodeMutableLiveData;
    }


    public void updateUserEpisodeMutableData(Long id, UserEpisode episode){
        UserActionsService service = RetrofitInstance.getUserService();
        Call<UserEpisode> call = service.updateUserEpisode(id, episode);
        call.enqueue(new Callback<UserEpisode>() {
            @Override
            public void onResponse(Call<UserEpisode> call, Response<UserEpisode> response) {
                Log.i("EPREPO", String.valueOf(response.code()));
                if(response.code() == 200){
                    Toast.makeText(application.getApplicationContext(), "The show was updated", Toast.LENGTH_SHORT).show();
                    Log.i("EPREPO", response.body().getUserEpisodeId().getEpisode().getId().toString());
                    Log.i("EPREPO", String.valueOf(response.body().isWatched()));
                }
            }

            @Override
            public void onFailure(Call<UserEpisode> call, Throwable t) {
                Log.e("EPREPO", t.getMessage());
            }
        });
    }
}
