package com.northcoders.media_tracker_front.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/*
    The data from the fetch request is going to be stored in this class.
    Need empty constructor and one with all the fields.
    Each field needs a getter (add @Bindable) and setter.
 */

public class UserFilm extends BaseObservable  {


    private UserFilmId userFilmId;
    private int rating;
    private String notes;
    private String status;
    private String watchedDate;

    public UserFilm(UserFilmId userFilmId, int rating, String notes, String status, String watchedDate) {
        this.userFilmId = userFilmId;
        this.rating = rating;
        this.notes = notes;
        this.status = status;
        this.watchedDate = watchedDate;
    }

    public UserFilm() {
    }

//    public Bookmarked(String title, String posterUrl, String synopsis) {
//        this.title = title;
//        this.posterUrl = posterUrl;
//        this.synopsis = synopsis;
//    }

//    @Bindable
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    @Bindable
//    public String getPosterUrl() {
//        return posterUrl;
//    }
//
//    public void setPosterUrl(String posterUrl) {
//        this.posterUrl = posterUrl;
//    }
//
//    @Bindable
//    public String getSynopsis() {
//        return synopsis;
//    }
//
//    public void setSynopsis(String synopsis) {
//        this.synopsis = synopsis;
//    }


    @Bindable
    public UserFilmId getUserFilmId() {
        return userFilmId;
    }

    public void setUserFilmId(UserFilmId userFilmId) {
        this.userFilmId = userFilmId;
    }

    @Bindable
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Bindable
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Bindable
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(String watchedDate) {
        this.watchedDate = watchedDate;
    }
}
