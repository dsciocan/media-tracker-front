package com.northcoders.media_tracker_front;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.northcoders.media_tracker_front.model.AppUserRepository;

public class LoginActivityViewModel extends AndroidViewModel {
    private AppUserRepository appUserRepository;


    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        this.appUserRepository = new AppUserRepository(application);
    }

    // The auth() will be a method to create a user on the backend when they sign up for the app
    public void getAuth(){
        appUserRepository.auth();
    }

}
