package com.northcoders.media_tracker_front.model.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.service.RetrofitInstance;
import com.northcoders.media_tracker_front.service.UserActionsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserShowRepository {

    private MutableLiveData<UserShow> singleShowData = new MutableLiveData<>();
    private MutableLiveData<List<UserShow>> showLiveData = new MutableLiveData<>();
    private MutableLiveData<List<UserShow>> watchingShowLiveData = new MutableLiveData<>();
    private MutableLiveData<List<UserShow>> watcehdShowLiveData = new MutableLiveData<>();
    private Application application;

    public UserShowRepository(Application application) {
        this.application = application;
    }


    public MutableLiveData<List<UserShow>> getBookmarkedShowListLiveData() {
        UserActionsService userActionsService = RetrofitInstance.getUserService();

        Call<List<UserShow>> call = userActionsService.getBookmarkedShows();
        call.enqueue(new Callback<List<UserShow>>() {
            @Override
            public void onResponse(Call<List<UserShow>> call, Response<List<UserShow>> response) {
                List<UserShow> userShowList = response.body();
                showLiveData.postValue(userShowList);
                showLiveData.setValue(userShowList);

            }

            @Override
            public void onFailure(Call<List<UserShow>> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }
        });
        return showLiveData;
    }

    public MutableLiveData<List<UserShow>> getWatchingMutableLiveData() {
        UserActionsService userActionsService = RetrofitInstance.getUserService();

        Call<List<UserShow>> call = userActionsService.getShowsWatching();
        call.enqueue(new Callback<List<UserShow>>() {
            @Override
            public void onResponse(Call<List<UserShow>> call, Response<List<UserShow>> response) {
                List<UserShow> watchingList = response.body();
                watchingShowLiveData.postValue(watchingList);
                watchingShowLiveData.setValue(watchingList);
            }

            @Override
            public void onFailure(Call<List<UserShow>> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }
        });
        return watchingShowLiveData;
    }


    public MutableLiveData<List<UserShow>> getWatchedShowLiveData() {
        UserActionsService userActionsService = RetrofitInstance.getUserService();

        Call<List<UserShow>> call = userActionsService.getShowsByStatus("WATCHED");
        call.enqueue(new Callback<List<UserShow>>() {
            @Override
            public void onResponse(Call<List<UserShow>> call, Response<List<UserShow>> response) {
                List<UserShow> userShowList = response.body();
                watcehdShowLiveData.postValue(userShowList);
                watcehdShowLiveData.setValue(userShowList);

            }

            @Override
            public void onFailure(Call<List<UserShow>> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }
        });
        return watcehdShowLiveData;
    }

    public MutableLiveData<UserShow> getSingleShowMutableLiveData(Long showId) {
        UserActionsService userActionsService = RetrofitInstance.getUserService();

        Call<UserShow> call = userActionsService.getUserShowDetails(showId);
        call.enqueue(new Callback<UserShow>() {
            @Override
            public void onResponse(Call<UserShow> call, Response<UserShow> response) {
                Log.i("BOOKMARKED REPO", String.valueOf(response.code()));
//                Log.i("BOOKMARKED REPO", response.body().toString());
                UserShow userShow = response.body();
                singleShowData.postValue(userShow);
                singleShowData.setValue(userShow);
            }

            @Override
            public void onFailure(Call<UserShow> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }

        });
        return singleShowData;
    }

    public void updateUserShow(Long id, UserShow show){
        UserActionsService service = RetrofitInstance.getUserService();
        Call<Void> call = service.updateUserShow(id, show);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Bookmarked Repo", String.valueOf(response.code()));
                if(response.code() == 200){
                    Toast.makeText(application.getApplicationContext(), "The show was updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Bookmarked Repo", t.getMessage());
            }
        });
    }


}
