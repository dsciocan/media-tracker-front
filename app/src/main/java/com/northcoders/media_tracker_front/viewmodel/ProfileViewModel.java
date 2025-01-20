package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.northcoders.media_tracker_front.model.AppUserRepository;

public class ProfileViewModel extends AndroidViewModel {

    private AppUserRepository appUserRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application); this.appUserRepository = new AppUserRepository(application);
    }

    public boolean deleteUser(){
       return appUserRepository.deleteUser();
    }
}
