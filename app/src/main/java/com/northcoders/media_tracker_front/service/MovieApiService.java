package com.northcoders.media_tracker_front.service;

import com.northcoders.media_tracker_front.model.FilmSearchResult;
import com.northcoders.media_tracker_front.model.ShowSearchResult;
import com.northcoders.media_tracker_front.model.Bookmarked;
import com.northcoders.media_tracker_front.model.WatchHistory;
import com.northcoders.media_tracker_front.model.Watching;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApiService {
    @GET("history")
    Call<List<WatchHistory>> getHistory();

    @GET("shows/search/{searchQuery}")
    Call<List<ShowSearchResult>> getShowSearchResults(@Path("searchQuery") String searchQuery);

    @GET("bookmarked")
    Call<List<Bookmarked>> getBookmarked();

    //call to user?
    @GET("watching")
    Call<List<Watching>> getWatching();

    @GET("films/search/{query}")
    Call<List<FilmSearchResult>> getFilmSearchResults(@Path("{query}") String searchQuery);
}
