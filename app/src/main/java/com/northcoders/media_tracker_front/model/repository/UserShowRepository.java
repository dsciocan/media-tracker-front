package com.northcoders.media_tracker_front.model.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.service.RetrofitInstance;
import com.northcoders.media_tracker_front.service.UserActionsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkedRepository {

    private MutableLiveData<List<UserFilm>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<UserFilm> singleFilmData = new MutableLiveData<>();
    private MutableLiveData<UserShow> singleShowData = new MutableLiveData<>();
    private MutableLiveData<List<UserShow>> showLiveData = new MutableLiveData<>();
    private MutableLiveData<List<UserShow>> watchingShowLiveData = new MutableLiveData<>();
    private MutableLiveData<List<UserShow>> watcehdShowLiveData = new MutableLiveData<>();
    private Application application;

    public BookmarkedRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<UserFilm>> getMutableLiveData() {
        UserActionsService userActionsService = RetrofitInstance.getUserService();

        Call<List<UserFilm>> call = userActionsService.getBookmarked();
        call.enqueue(new Callback<List<UserFilm>>() {
            @Override
            public void onResponse(Call<List<UserFilm>> call, Response<List<UserFilm>> response) {
                List<UserFilm> userFilmList = response.body();
                mutableLiveData.setValue(userFilmList);
            }

            @Override
            public void onFailure(Call<List<UserFilm>> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }
        });
        return mutableLiveData;
    }

    public MutableLiveData<UserFilm> getFilmMutableLiveData(Long filmId) {
        UserActionsService userActionsService = RetrofitInstance.getUserService();

        Call<UserFilm> call = userActionsService.getBookmarkedFilm(filmId);
        call.enqueue(new Callback<UserFilm>() {
            @Override
            public void onResponse(Call<UserFilm> call, Response<UserFilm> response) {
                Log.i("BOOKMARKED REPO", String.valueOf(response.code()));
//                Log.i("BOOKMARKED REPO", response.body().toString());
                UserFilm userFilm = response.body();
                singleFilmData.postValue(userFilm);
                singleFilmData.setValue(userFilm);
            }

            @Override
            public void onFailure(Call<UserFilm> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }

        });
        return singleFilmData;
    }

    public void deleteUserFilm(Long id){
        UserActionsService service = RetrofitInstance.getUserService();
        Call<Void> call = service.deleteUserFilm(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Bookmarked Repo", String.valueOf(response.code()));
                if(response.code() == 200){
                    Toast.makeText(application.getApplicationContext(), "Movie Has Been Removed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Bookmarked Repo", t.getMessage());
            }
        });
    }

    public void updateUserFilm(Long id, UserFilm film){
        UserActionsService service = RetrofitInstance.getUserService();
        Call<Void> call = service.updateUserBookFilm(id, film);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Bookmarked Repo", String.valueOf(response.code()));
                if(response.code() == 200){
                    Toast.makeText(application.getApplicationContext(), "The movie was updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Bookmarked Repo", t.getMessage());
            }
        });
    }



    //USERSHOW

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
                watchingShowLiveData.setValue(watchingList);
            }

            @Override
            public void onFailure(Call<List<UserShow>> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }
        });
        return watchingShowLiveData;
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
