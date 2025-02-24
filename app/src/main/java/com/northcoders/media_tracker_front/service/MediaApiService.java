package com.northcoders.media_tracker_front.service;

import com.northcoders.media_tracker_front.model.Film;
import com.northcoders.media_tracker_front.model.FilmDetails;
import com.northcoders.media_tracker_front.model.FilmSearchResult;
import com.northcoders.media_tracker_front.model.Show;
import com.northcoders.media_tracker_front.model.ShowDetails;
import com.northcoders.media_tracker_front.model.ShowSearchResult;
import com.northcoders.media_tracker_front.model.UserShow;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MediaApiService {

    @GET("shows/search/{searchQuery}")
    Call<List<ShowSearchResult>> getShowSearchResults(@Path("searchQuery") String searchQuery);

    @GET("shows/details/{showId}")
    Call<ShowDetails> getShowDetails(@Path("showId") Long showId);

    @GET("shows/saved/tmdb={tmdbId}")
    Call<Show> getShowByTmdbId(@Path("tmdbId") Long tmdbId);

    @GET("shows/saved/{id}")
    Call<Show> getShowById(@Path("id") Long id);



    @GET("watching")
    Call<List<UserShow>> getWatching();


    @GET("films/search/{query}")
    Call<List<FilmSearchResult>> getFilmSearchResults(@Path("query") String searchQuery);

    @GET("films/details/{movieId}")
    Call<FilmDetails> getFilmDetails(@Path("movieId") Long movieId);

    @GET("films/saved/tmdb={tmdbId}")
    Call<Film> getFilmByTmdbId(@Path("tmdbId") Long tmdbId);


}
