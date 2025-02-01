package com.northcoders.media_tracker_front.service;

import com.northcoders.media_tracker_front.model.Film;
import com.northcoders.media_tracker_front.model.FilmDetails;
import com.northcoders.media_tracker_front.model.FilmSearchResult;
import com.northcoders.media_tracker_front.model.ShowSearchResult;
import com.northcoders.media_tracker_front.model.Watching;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApiService {

    @GET("shows/search/{searchQuery}")
    Call<List<ShowSearchResult>> getShowSearchResults(@Path("searchQuery") String searchQuery);




    @GET("watching")
    Call<List<Watching>> getWatching();


    @GET("films/search/{query}")
    Call<List<FilmSearchResult>> getFilmSearchResults(@Path("query") String searchQuery);

    @GET("films/details/{movieId}")
    Call<FilmDetails> getFilmDetails(@Path("movieId") Long movieId);

    @GET("films/saved/tmdb={tmdbId}")
    Call<Film> getFilmByTmdbId(@Path("tmdbId") Long tmdbId);


}
