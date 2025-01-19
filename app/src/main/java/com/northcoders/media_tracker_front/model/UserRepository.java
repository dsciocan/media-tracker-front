package com.northcoders.media_tracker_front.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.service.RetrofitInstance;
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

    // The auth() is currently just a placeholder method to debug sending tokens to the back
    // The backend method doesn't return anything besides an OK httpstatus
    public void auth(){
        UserActionsService userActionsService = RetrofitInstance.getUserService();

        Call<Void> authUserCall = userActionsService.auth();
        authUserCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("RESPONSE FROM THE BACK", String.valueOf(response.code()));

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("FROM THE BACK",call.toString());
                Log.e("FROM THE BACK","WE FAILED: " + t.getMessage());
            }
        });
    }

}
