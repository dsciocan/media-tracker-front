package com.northcoders.media_tracker_front;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.northcoders.media_tracker_front.model.User;
import com.northcoders.media_tracker_front.model.UserRepository;

public class LoginActivityViewModel extends AndroidViewModel {
    private UserRepository userRepository;


    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository(application);
    }

    // The getAuth() is currently a placeholder method to debug sending tokens to the back
    public void getAuth(){
        userRepository.auth();
    }

}
