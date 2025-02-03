package com.northcoders.media_tracker_front.model.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.model.AppUser;
import com.northcoders.media_tracker_front.service.RetrofitInstance;
import com.northcoders.media_tracker_front.service.UserActionsService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppUserRepository {
    private MutableLiveData<AppUser> currentUser= new MutableLiveData<AppUser>();
    private boolean status;

    private Application application;

    public AppUserRepository(Application application) {
        this.application = application;
    }

    // The auth() will be a method to create a user on the backend when they sign up for the app
    // The backend method doesn't return anything besides an OK httpstatus (hopefully)
    public void auth(){
        UserActionsService userActionsService = RetrofitInstance.getUserService();

        Call<Void> authUserCall = userActionsService.auth();
        authUserCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Auth Response From Back", String.valueOf(response.code()));

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Auth Response From Back",call.toString());
                Log.e("Auth Response From Back","WE FAILED: " + t.getMessage());
            }
        });
    }

    public boolean deleteUser(){
        UserActionsService userActionsService = RetrofitInstance.getUserService();

        Call<Void> deleteUserCall = userActionsService.deleteUser();
        deleteUserCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Log.i("DeleteUser Response From Back", String.valueOf(response.code()));
                    status = true;
                }
                else{
                    status = false;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("DeleteUser Response From Back",call.toString());
                Log.e("DeleteUser Response From Back","WE FAILED: " + t.getMessage());
            }
        });

        return status;
    }



}
