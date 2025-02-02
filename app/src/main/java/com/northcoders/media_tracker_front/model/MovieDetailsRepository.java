package com.northcoders.media_tracker_front.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.service.MovieApiService;
import com.northcoders.media_tracker_front.service.RetrofitInstance;
import com.northcoders.media_tracker_front.service.UserActionsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsRepository {

    MutableLiveData<FilmDetails> mutableLiveData = new MutableLiveData<>();
    MutableLiveData<Film> savedFilmLiveData = new MutableLiveData<>();
    MutableLiveData<UserFilm> userFilmLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> userFilmSavedStatus = new MutableLiveData<>();

    private final Application application;

    public MovieDetailsRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<FilmDetails> getMutableLiveData(Long id) {
        MovieApiService movieApiService = RetrofitInstance.getMovieService();

        Call<FilmDetails> call = movieApiService.getFilmDetails(id);
        call.enqueue(new Callback<FilmDetails>() {
            @Override
            public void onResponse(Call<FilmDetails> call, Response<FilmDetails> response) {
                Log.i("MovieDetailsRepo",String.valueOf(response.code()));
                Log.i("MovieDetailsRepo", response.body().toString());
                FilmDetails film = response.body();
                mutableLiveData.postValue(film);
                mutableLiveData.setValue(film);
            }

            @Override
            public void onFailure(Call<FilmDetails> call, Throwable t) {
                Log.i("GET request", t.getMessage());

            }
        });

        return mutableLiveData;
    }

    public MutableLiveData<Film> getMutableLiveDataByTmdbId(Long tmdbId) {
        MovieApiService movieApiService = RetrofitInstance.getMovieService();

        Call<Film> call = movieApiService.getFilmByTmdbId(tmdbId);
        call.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                Log.i("MovieDetailsRepo1",String.valueOf(response.code()));
                Log.i("MovieDetailsRepo1", response.body().toString());
                Film film = response.body();
                savedFilmLiveData.postValue(film);
                savedFilmLiveData.setValue(film);
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Log.i("film_GET_request", t.getMessage());

            }
        });

        return savedFilmLiveData;
    }

    public MutableLiveData<UserFilm> saveUserFilm(long id, UserFilm userFilm){
        UserActionsService service = RetrofitInstance.getUserService();
        Call<UserFilm> call = service.saveUserFilm(id, userFilm);
        call.enqueue(new Callback<UserFilm>() {
            @Override
            public void onResponse(Call<UserFilm> call, Response<UserFilm> response) {
                Log.i("Response From Movie Details Repo", String.valueOf(response.code()));
                UserFilm userFilm1 = response.body();
                userFilmLiveData.postValue(userFilm1);
                userFilmLiveData.setValue(userFilm1);
            }

            @Override
            public void onFailure(Call<UserFilm> call, Throwable t) {

            }
        });
        return userFilmLiveData;
    }



    public MutableLiveData<Boolean> isUserFilmSaved(Long tmdbId) {
        UserActionsService userActionsService = RetrofitInstance.getUserService();
        Call<Boolean> call = userActionsService.isUserFilmSaved(tmdbId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                userFilmSavedStatus.postValue(response.body());
                userFilmSavedStatus.setValue(response.body());
//                Log.i("GET boolean", response.body().toString());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("GET boolean", t.getMessage());
            }
        });
        return userFilmSavedStatus;
    }



}
