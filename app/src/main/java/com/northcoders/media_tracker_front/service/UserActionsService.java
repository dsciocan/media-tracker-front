package com.northcoders.media_tracker_front.service;

import android.text.BoringLayout;

import com.northcoders.media_tracker_front.model.FilmDetails;
import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.UserShow;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserActionsService {

    //USER ENDPOINTS
    @GET("users/auth")
    Call<Void> auth();

    @DELETE("users")
    Call<Void> deleteUser();


    //USERFILM ENDPOINTS
    @GET("users/films/search?")
    Call<List<UserFilm>> getFilmsByStatus(@Query("status") String status);

    @GET("users/films/search?status=BOOKMARKED")
    Call<List<UserFilm>> getBookmarked();

    @GET("users/films/{filmId}")
    Call<UserFilm> getBookmarkedFilm(@Path("filmId") Long filmId);

    @GET("users/films/search?status=WATCHED")
    Call<List<UserFilm>> getHistory();

    @GET("users/films/{filmDbId}")
    Call<UserFilm> getUserFilmDetails(@Path("filmDbId") Long movieId );

    @PATCH("users/films/{filmDbId}")
    Call<Void> updateUserFilm(@Path("filmDbId") Long id, @Body UserFilm film);

    @PATCH("users/films/{filmDbId}")
    Call<Void> updateUserBookFilm(@Path("filmDbId") Long id, @Body UserFilm film);

    @DELETE("users/films/{filmDbId}")
    Call<Void> deleteUserFilm(@Path("filmDbId") Long movieId);

    @POST("users/films/{movieid}")
    Call<UserFilm> saveUserFilm(@Path ("movieid") Long id, @Body UserFilm userFilm);

    @GET("users/film/{tmdbId}/isSaved")
    Call<Boolean> isUserFilmSaved(@Path("tmdbId") Long tmdbId);



    //USERSHOW ENDPOINTS
    @GET("users/shows?status=WATCHING")
    Call<List<UserShow>> getShowsWatching(@Query("status") String status);

    @GET("users/shows?status=BOOKMARKED")
    Call<List<UserShow>> getBookmarkedShows();

    @GET("users/shows?")
    Call<List<UserShow>> getShowsByStatus(@Query("status") String status);

    @GET("users/shows?status=WATCHED")
    Call<List<UserShow>> getShowHistory();

    @GET("users/shows/{showDbId}")
    Call<UserShow> getUserShowDetails(@Path("showDbId") Long showId );

    @PUT("users/shows/{showDbId}")
    Call<Void> updateUserShow(@Path("showDbId") Long id, @Body UserShow film);

//    @DELETE("users/films/{filmDbId}")
//    Call<Void> deleteUserShow(@Path("filmDbId") Long showId);

    @POST("users/shows/save/{showTmdbId}")
    Call<UserShow> saveUserShow(@Path ("showTmdbId") Long id, @Body UserShow UserShow);

    @GET("users/show/{tmdbId}/isSaved")
    Call<Boolean> isUserShowSaved(@Path("tmdbId") Long tmdbId);




    //STATS ENDPOINTS
    @GET("users/genreStats")
    Call<Map<String,Integer>> getUserGenreStats();

    @GET("users/totalWatchedRuntime")
    Call<Integer> getTotalWatchedRuntime();







}
