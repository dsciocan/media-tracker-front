package com.northcoders.media_tracker_front.model.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


import com.northcoders.media_tracker_front.model.Show;
import com.northcoders.media_tracker_front.model.ShowDetails;
import com.northcoders.media_tracker_front.model.UserShow;
import com.northcoders.media_tracker_front.service.MediaApiService;
import com.northcoders.media_tracker_front.service.RetrofitInstance;
import com.northcoders.media_tracker_front.service.UserActionsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailsRepository {

        MutableLiveData<ShowDetails> mutableLiveData = new MutableLiveData<>();
        MutableLiveData<Show> savedShowLiveData = new MutableLiveData<>();
        MutableLiveData<UserShow> userShowLiveData = new MutableLiveData<>();
        MutableLiveData<Boolean> userShowSavedStatus = new MutableLiveData<>();

        private final Application application;

         public ShowDetailsRepository(Application application) {
            this.application = application;
        }

    public MutableLiveData<ShowDetails> getMutableLiveData(Long id) {
             MediaApiService mediaApiService = RetrofitInstance.getMovieService();

            Call<ShowDetails> call = mediaApiService.getShowDetails(id);
            call.enqueue(new Callback<ShowDetails>() {
                @Override
                public void onResponse(Call<ShowDetails> call, Response<ShowDetails> response) {
                    ShowDetails showDetails = response.body();
                    mutableLiveData.postValue(showDetails);
                    mutableLiveData.setValue(showDetails);
                }

                @Override
                public void onFailure(Call<ShowDetails> call, Throwable t) {
                    Log.e("GET request", t.getMessage());
                }
            });

            return mutableLiveData;
        }

        public MutableLiveData<Show> getShowById(Long id) {
             MediaApiService mediaApiService = RetrofitInstance.getMovieService();

             Call<Show> showCall = mediaApiService.getShowById(id);
            showCall.enqueue(new Callback<Show>() {
                @Override
                public void onResponse(Call<Show> call, Response<Show> response) {
                    Show show = response.body();
                    savedShowLiveData.postValue(show);
                    savedShowLiveData.setValue(show);
                }

                @Override
                public void onFailure(Call<Show> call, Throwable t) {
                    Log.i("Show_GET_request", t.getMessage());
                }
            });
            return savedShowLiveData;
        }

        public MutableLiveData<Show> getMutableLiveDataByTmdbId(Long tmdbId) {
            MediaApiService mediaApiService = RetrofitInstance.getMovieService();

            Call<Show> call = mediaApiService.getShowByTmdbId(tmdbId);
            call.enqueue(new Callback<Show>() {
                @Override
                public void onResponse(Call<Show> call, Response<Show> response) {
                    Show show = response.body();
                    Log.i("Show_GET_request", response.body().toString());
                    savedShowLiveData.postValue(show);
                    savedShowLiveData.setValue(show);
                }

                @Override
                public void onFailure(Call<Show> call, Throwable t) {
                    Log.i("Show_GET_request", t.getMessage());

                }
            });

            return savedShowLiveData;
        }

        public MutableLiveData<UserShow> saveUserShow(long id, UserShow userShow){
            UserActionsService service = RetrofitInstance.getUserService();
            Call<UserShow> call = service.saveUserShow(id, userShow);
            call.enqueue(new Callback<UserShow>() {
                @Override
                public void onResponse(Call<UserShow> call, Response<UserShow> response) {
                    Log.i("Response From Movie Details Repo", String.valueOf(response.code()));
                    UserShow userShow1 = response.body();
                    userShowLiveData.postValue(userShow1);
                    userShowLiveData.setValue(userShow1);
                }

                @Override
                public void onFailure(Call<UserShow> call, Throwable t) {

                }
            });
            return userShowLiveData;
        }

    }

