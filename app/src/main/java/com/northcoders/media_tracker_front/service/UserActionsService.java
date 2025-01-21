package com.northcoders.media_tracker_front.service;

import com.northcoders.media_tracker_front.model.WatchHistory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserActionsService {

    @GET("users/auth")
    Call<Void> auth();

    @GET("users/films/search")
    Call<List<WatchHistory>> getHistory(@Query("status") String status);
    @DELETE("users")
    Call<Void> deleteUser();

    @GET("users/films/search?status=WATCHED")
    Call<List<WatchHistory>> getHistory();
    @GET("users/films/{filmDbId}")
    Call<WatchHistory> getUserFilmDetails(@Path("filmDbId") Long movieId );

}
