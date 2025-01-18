package com.northcoders.media_tracker_front.service;

import retrofit2.Call;
import retrofit2.http.GET;
public interface UserActionsService {


    @GET("users/auth")
    Call<Void> auth();
}
