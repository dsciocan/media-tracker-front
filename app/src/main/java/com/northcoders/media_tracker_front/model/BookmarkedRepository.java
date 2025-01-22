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

public class BookmarkedRepository {

    private MutableLiveData<List<Bookmarked>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Bookmarked> singleFilmData = new MutableLiveData<>();
    private Application application;

    public BookmarkedRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Bookmarked>> getMutableLiveData() {
        UserActionsService userActionsService = RetrofitInstance.getUserService();

        Call<List<Bookmarked>> call = userActionsService.getBookmarked();
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

    public MutableLiveData<Bookmarked> getFilmMutableLiveData(Long filmId) {
        UserActionsService userActionsService = RetrofitInstance.getUserService();

        Call<Bookmarked> call = userActionsService.getBookmarkedFilm(filmId);
        call.enqueue(new Callback<Bookmarked>() {
            @Override
            public void onResponse(Call<Bookmarked> call, Response<Bookmarked> response) {
                Log.i("BOOKMARKED REPO", String.valueOf(response.code()));
                Log.i("BOOKMARKED REPO", response.body().toString());
                Bookmarked userFilm = response.body();
                singleFilmData.setValue(userFilm);
            }

            @Override
            public void onFailure(Call<Bookmarked> call, Throwable t) {
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

    public void updateUserFilm(Long id, Bookmarked film){
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



}
