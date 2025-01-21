package com.northcoders.media_tracker_front.service;

import com.northcoders.media_tracker_front.model.WatchHistory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserActionsService {


    @GET("users/auth")
    Call<Void> auth();

    @GET("users/films/search")
    Call<List<WatchHistory>> getHistory(@Query("status") String status);
}
