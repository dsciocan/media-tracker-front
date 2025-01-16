package com.northcoders.media_tracker_front.service;

import com.northcoders.media_tracker_front.model.Bookmarked;
import com.northcoders.media_tracker_front.model.WatchHistory;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApiService {
    @GET("history")
    Call<List<WatchHistory>> getHistory();

    @GET("bookmarked")
    Call<List<Bookmarked>> getBookmarked();
}
