package com.northcoders.media_tracker_front.service;

import com.northcoders.media_tracker_front.model.User;

import retrofit2.Call;
import retrofit2.http.POST;
public interface UserActionsService {

    // Example request -> This one won't be included in the final product
    @POST("users/auth")
    Call<User> authUser();

}
