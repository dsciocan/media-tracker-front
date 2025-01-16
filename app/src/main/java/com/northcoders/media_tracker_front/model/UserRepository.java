package com.northcoders.media_tracker_front.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.service.RetroFitInstance;
import com.northcoders.media_tracker_front.service.UserActionsService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private MutableLiveData<User> currentUser= new MutableLiveData<User>();

    private Application application;

    public UserRepository(Application application) {
        this.application = application;
    }

    // The getCurrentUser() is currently just a placeholder method to debug sending tokens to the back
    // The backend method doesn't return anything besides an OK httpstatus
    public MutableLiveData<User> getCurrentUser() {
        UserActionsService actionsService = RetroFitInstance.getUserService();
        Call<User> call = actionsService.authUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                currentUser.setValue(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("GET all request", t.getMessage());
            }
        });
        return currentUser;
    }


}
