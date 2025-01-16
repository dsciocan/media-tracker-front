package com.northcoders.media_tracker_front.service;

import com.northcoders.media_tracker_front.model.ShowSearchResult;
import com.northcoders.media_tracker_front.model.WatchHistory;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApiService {
    @GET("history")
    Call<List<WatchHistory>> getHistory();

    @GET("shows/search/{searchQuery}")
    Call<List<ShowSearchResult>> getShowSearchResults(@Path("searchQuery") String searchQuery);
}
