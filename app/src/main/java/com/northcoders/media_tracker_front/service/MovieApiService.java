package com.northcoders.media_tracker_front.service;

import com.northcoders.media_tracker_front.model.Bookmarked;
import com.northcoders.media_tracker_front.model.WatchHistory;
import com.northcoders.media_tracker_front.model.Watching;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApiService {
    @GET("history")
    Call<List<WatchHistory>> getHistory();

    @GET("bookmarked")
    Call<List<Bookmarked>> getBookmarked();

    //call to user?
    @GET("bookmarked")
    Call<List<Watching>> getWatching();
}
